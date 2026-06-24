package com.kiriai.kiriorganization

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import android.content.ClipboardManager
import android.content.ClipData
import android.content.Context
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.foundation.layout.heightIn
import androidx.compose.ui.unit.dp
import com.kiriai.kiriorganization.ui.theme.KiriTypography
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.work.WorkManager
import androidx.work.OneTimeWorkRequestBuilder
import com.kiriai.kiriorganization.workers.InactivityWorker
import java.util.concurrent.TimeUnit
import androidx.work.ExistingWorkPolicy
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.kiriai.kiriorganization.ui.screens.*
import com.kiriai.kiriorganization.ui.theme.*
import com.kiriai.kiriorganization.ui.viewmodels.MainViewModel
import com.kiriai.kiriorganization.ui.viewmodels.SubscriptionViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.razorpay.PaymentData
import com.razorpay.PaymentResultWithDataListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity(), PaymentResultWithDataListener {

    private val subscriptionViewModel: SubscriptionViewModel by viewModels()

    private val requestPermissionLauncher = registerForActivityResult(
        androidx.activity.result.contract.ActivityResultContracts.RequestMultiplePermissions()
    ) { perms ->
        android.util.Log.d("Kiri_DEBUG", "Permissions result: $perms")
        startKiriService()
    }

    private fun startKiriService() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val hasNotify = checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS) == android.content.pm.PackageManager.PERMISSION_GRANTED
            if (!hasNotify) return
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
            val hasDataSync = checkSelfPermission(android.Manifest.permission.FOREGROUND_SERVICE_DATA_SYNC) == android.content.pm.PackageManager.PERMISSION_GRANTED
            if (!hasDataSync) {
                return
            }
        }

        try {
            startService(android.content.Intent(this, com.kiriai.kiriorganization.services.KiriBackgroundService::class.java))
        } catch (e: Exception) {
            android.util.Log.e("KiriService", "Start failed: ${e.message}")
        }
    }

    override fun onNewIntent(intent: android.content.Intent) {
        super.onNewIntent(intent)
        setIntent(intent) 
    }

    override fun onResume() {
        super.onResume()
        val request = OneTimeWorkRequestBuilder<InactivityWorker>()
            .setInitialDelay(6, TimeUnit.HOURS)
            .build()
        WorkManager.getInstance(this).enqueueUniqueWork(
            "inactivity_reminder",
            ExistingWorkPolicy.REPLACE,
            request
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        
        requestPermissions()
        WindowCompat.setDecorFitsSystemWindows(window, false)
        val lastCrash = com.kiriai.kiriorganization.utils.KiriCrashHandler.getAndClearLastCrash(this)

        setContent {
            val viewModel: MainViewModel = hiltViewModel()
            val themeMode by viewModel.isDarkMode.collectAsStateWithLifecycle()
            val startDestination by viewModel.startDestination.collectAsStateWithLifecycle()
            val navController = rememberNavController()
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            
            CompositionLocalProvider(LocalThemeMode provides themeMode) {
                KiriTheme(darkTheme = themeMode) {
                    if (lastCrash != null) {
                        CrashDialog(lastCrash)
                    }

                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        // REFINED_NAVIGATION_SYNC: We use a stable start destination and 
                        // only perform redirects when the auth state explicitly changes.
                        val initialRoute = remember(startDestination) { startDestination }
                        
                        if (initialRoute != null) {
                            LaunchedEffect(startDestination) {
                                val currentRoute = navController.currentBackStackEntry?.destination?.route?.split("?")?.first()
                                android.util.Log.d("Kiri_DEBUG", "MainActivity: AuthSync destination=$startDestination currentRoute=$currentRoute")
                                
                                when {
                                    startDestination == "landing" && currentRoute != "landing" && currentRoute != "login" && currentRoute != "register" -> {
                                        navController.navigate("landing") {
                                            popUpTo(0) { inclusive = true }
                                        }
                                    }
                                    startDestination == "chat" && (currentRoute == "landing" || currentRoute == "login" || currentRoute == "register") -> {
                                        navController.navigate("chat") {
                                            popUpTo(0) { inclusive = true }
                                        }
                                    }
                                }
                            }

                            NavHost(
                                navController = navController,
                                startDestination = if (initialRoute == "chat") "chat?id={id}" else initialRoute
                            ) {
                                composable("landing") { LandingScreen(navController) }
                                composable("login") { LoginScreen(navController) }
                                composable("register") { RegisterScreen(navController) }
                                composable(
                                    route = "chat?id={id}",
                                    arguments = listOf(
                                        androidx.navigation.navArgument("id") { 
                                            nullable = true
                                            defaultValue = null 
                                        }
                                    )
                                ) { backStackEntry -> 
                                    val id = backStackEntry.arguments?.getString("id")
                                    ChatScreen(navController, id = id) 
                                }
                                composable("profile") { ProfileScreen(navController) }
                                composable("pricing") { PricingScreen(navController, subscriptionViewModel) }
                                composable("imagelab") { ImageLabScreen(navController) }
                            }
                        } else {
                            // Initializing State: Prevent blank screen by showing a subtle loader
                            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                                CircularProgressIndicator(color = MaterialTheme.colorScheme.primary, strokeWidth = 2.dp)
                            }
                        }
                    }
                }
            }
        }
    }

    @Composable
    private fun CrashDialog(crashTrace: String) {
        val showDialog = remember { mutableStateOf(true) }
        
        if (showDialog.value) {
            androidx.compose.material3.AlertDialog(
                onDismissRequest = { showDialog.value = false },
                containerColor = VelvetBlack.copy(alpha = 0.9f), // Subtle glass effect
                titleContentColor = ShowroomWhite,
                textContentColor = ShowroomWhite,
                title = { Text("KIRI // SYSTEM_ERROR", style = KiriTypography.labelLarge) },
                text = { 
                    Text(
                        text = "The application encountered a temporary instability and has been restored. If this persists, please contact support.",
                        style = KiriTypography.bodySmall.copy(color = SilverMist)
                    )
                },
                confirmButton = {
                    androidx.compose.material3.TextButton(onClick = { showDialog.value = false }) {
                        Text("RESTART_SESSION", style = KiriTypography.labelMedium)
                    }
                }
            )
        }
    }

    private fun requestPermissions() {
        val permissions = mutableListOf<String>()
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            permissions.add(android.Manifest.permission.POST_NOTIFICATIONS)
            permissions.add(android.Manifest.permission.READ_MEDIA_IMAGES)
            permissions.add(android.Manifest.permission.READ_MEDIA_VIDEO)
        } else {
            permissions.add(android.Manifest.permission.READ_EXTERNAL_STORAGE)
        }
        
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
            permissions.add(android.Manifest.permission.FOREGROUND_SERVICE_DATA_SYNC)
        }
        
        if (permissions.isNotEmpty()) {
            requestPermissionLauncher.launch(permissions.toTypedArray())
        }
    }

    override fun onPaymentSuccess(razorpayPaymentId: String?, paymentData: PaymentData?) {
        paymentData?.let {
            subscriptionViewModel.onPaymentSuccess(
                it.orderId ?: "",
                it.paymentId ?: "",
                it.signature ?: ""
            )
        }
    }

    override fun onPaymentError(code: Int, response: String?, paymentData: PaymentData?) {
        Toast.makeText(this, "Payment Failed: $response", Toast.LENGTH_LONG).show()
    }
}
