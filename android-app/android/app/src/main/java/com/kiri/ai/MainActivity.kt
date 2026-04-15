package com.kiri.ai

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import android.content.ClipboardManager
import android.content.ClipData
import android.content.Context
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.foundation.layout.heightIn
import androidx.compose.ui.unit.dp
import com.kiri.ai.ui.theme.KiriTypography
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kiri.ai.ui.screens.*
import com.kiri.ai.ui.theme.*
import com.kiri.ai.ui.viewmodels.MainViewModel
import com.kiri.ai.ui.viewmodels.SubscriptionViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.razorpay.PaymentData
import com.razorpay.PaymentResultWithDataListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity(), PaymentResultWithDataListener {

    private val subscriptionViewModel: SubscriptionViewModel by viewModels()

    private val requestPermissionLauncher = registerForActivityResult(
        androidx.activity.result.contract.ActivityResultContracts.RequestMultiplePermissions()
    ) { _ ->
        startKiriService()
    }

    /**
     * ARCHITECTURAL_STABILITY_NOTICE
     * This application uses a flat, technical design system to prevent native rendering
     * recursion crashes (dispatchGetDisplayList). 
     * 
     * CORE_GUIDELINES:
     * 1. Avoid nesting NavHosts or multiple Scaffolds.
     * 2. Ensure all screens handle WindowInsets (IME, status, and navigation bars) at the root.
     * 3. chat-related components must use explicit drawing layers (graphicsLayer).
     */

    private fun startKiriService() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val hasNotify = checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS) == android.content.pm.PackageManager.PERMISSION_GRANTED
            if (!hasNotify) return
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
            val hasDataSync = checkSelfPermission(android.Manifest.permission.FOREGROUND_SERVICE_DATA_SYNC) == android.content.pm.PackageManager.PERMISSION_GRANTED
            if (!hasDataSync) {
                // We request it in requestPermissions() but here we guard the service start
                return
            }
        }

        try {
            startService(android.content.Intent(this, com.kiri.ai.services.KiriBackgroundService::class.java))
        } catch (e: Exception) {
            android.util.Log.e("KiriService", "Start failed: ${e.message}")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        
        requestPermissions()
        startKiriService()
        WindowCompat.setDecorFitsSystemWindows(window, false)
        val lastCrash = com.kiri.ai.utils.KiriCrashHandler.getAndClearLastCrash(this)

        setContent {
            val viewModel: MainViewModel = hiltViewModel()
            val themeMode by viewModel.isDarkMode.collectAsStateWithLifecycle()
            
            CompositionLocalProvider(LocalThemeMode provides themeMode) {
                KiriTheme(darkTheme = themeMode) {
                    if (lastCrash != null) {
                        CrashDialog(lastCrash)
                    }

                    val startDestination by viewModel.startDestination.collectAsStateWithLifecycle()

                    val startDest = startDestination
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        if (startDest != null) {
                            val navController = rememberNavController()
                            CompositionLocalProvider(
                                androidx.compose.ui.platform.LocalContext provides this
                            ) {
                                NavHost(
                                    navController = navController,
                                    startDestination = startDest
                                ) {
                                    composable("landing") { LandingScreen(navController) }
                                    composable("login") { LoginScreen(navController) }
                                    composable("register") { RegisterScreen(navController) }
                                    composable("chat?id={id}") { backStackEntry -> 
                                        val id = backStackEntry.arguments?.getString("id")
                                        ChatScreen(navController, id = id) 
                                    }
                                    composable("profile") { ProfileScreen(navController) }
                                    composable("pricing") { PricingScreen(navController, subscriptionViewModel) }
                                }
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
                containerColor = VelvetBlack,
                titleContentColor = ShowroomWhite,
                textContentColor = ShowroomWhite,
                title = { Text("KIRI // CRASH_DETECTED", style = KiriTypography.labelLarge) },
                text = { 
                    androidx.compose.foundation.lazy.LazyColumn(modifier = Modifier.heightIn(max = 400.dp)) {
                        item {
                            Text(
                                text = "TECHNICAL_TRACE:\n\n$crashTrace",
                                style = KiriTypography.bodySmall.copy(color = SilverMist)
                            )
                        }
                    }
                },
                confirmButton = {
                    androidx.compose.material3.TextButton(onClick = {
                        val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                        val clip = ClipData.newPlainText("Kiri AI Crash", crashTrace)
                        clipboard.setPrimaryClip(clip)
                        android.widget.Toast.makeText(this, "TRACE_COPIED", android.widget.Toast.LENGTH_SHORT).show()
                    }) {
                        Text("COPY_TRACE", style = KiriTypography.labelMedium)
                    }
                },
                dismissButton = {
                    androidx.compose.material3.TextButton(onClick = { showDialog.value = false }) {
                        Text("DISMISS", style = KiriTypography.labelMedium)
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
