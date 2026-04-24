package com.kiriai.kiriorganization.ui.screens

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.kiriai.kiriorganization.ui.components.KiriButton
import com.kiriai.kiriorganization.ui.components.KiriTextField
import com.kiriai.kiriorganization.ui.theme.*
import com.kiriai.kiriorganization.ui.viewmodels.AuthViewModel
import com.kiriai.kiriorganization.ui.viewmodels.ChatViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navController: NavController,
    chatViewModel: ChatViewModel = hiltViewModel(),
    authViewModel: AuthViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val state by chatViewModel.uiState.collectAsStateWithLifecycle()
    val authUiState by authViewModel.uiState.collectAsStateWithLifecycle()
    var name by remember { mutableStateOf(state.user?.name ?: "") }
    
    LaunchedEffect(state.user?.name) {
        state.user?.name?.let { name = it }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("ACCOUNT_SETTINGS", style = KiriTypography.labelLarge) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = VelvetBlack,
                    titleContentColor = ShowroomWhite,
                    navigationIconContentColor = ShowroomWhite
                )
            )
        },
        containerColor = VelvetBlack
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(24.dp)
        ) {
            Text(
                text = "MANAGE_SYSTEM_PROFILE_AND_INTEL_TIERS",
                style = KiriTypography.labelSmall,
                color = SilverMist
            )
            
            Spacer(modifier = Modifier.height(32.dp))
            
            // Profile Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = DarkGray),
                border = BorderStroke(1.dp, SilverMist.copy(alpha = 0.1f))
            ) {
                Column(modifier = Modifier.padding(24.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(72.dp)
                                .background(Brush.linearGradient(listOf(VelvetBlack, DarkGray)), CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = state.user?.name?.take(1) ?: "U",
                                style = KiriTypography.headlineLarge.copy(color = ShowroomWhite)
                            )
                        }

                        Spacer(modifier = Modifier.width(20.dp))
                        Column {
                            Text(
                                text = state.user?.name ?: "USER_NULL",
                                style = KiriTypography.labelLarge,
                                color = ShowroomWhite
                            )
                            Text(
                                text = state.user?.email ?: "IDENTITY_HIDDEN",
                                style = KiriTypography.bodySmall,
                                color = SilverMist
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Surface(
                                color = if (state.user?.isPremium == true) ShowroomWhite.copy(alpha = 0.1f) else DarkGray,
                                shape = RoundedCornerShape(4.dp),
                                border = BorderStroke(1.dp, if (state.user?.isPremium == true) ShowroomWhite else SilverMist.copy(alpha = 0.3f))
                            ) {
                                Text(
                                    text = state.user?.plan?.uppercase() ?: "FREE_TIER",
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                                    style = KiriTypography.labelSmall.copy(color = if (state.user?.isPremium == true) ShowroomWhite else SilverMist)
                                )
                            }
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(32.dp))
                    
                    KiriTextField(
                        value = name,
                        onValueChange = { name = it },
                        label = "DISPLAY_NAME",
                        placeholder = "ENTER_NAME"
                    )
                    
                    Spacer(modifier = Modifier.height(24.dp))
                    
                    KiriButton(
                        text = "UPDATE_IDENTITY",
                        onClick = { 
                            authViewModel.updateProfile(name) {
                                Toast.makeText(context, "IDENTITY_SYNCED", Toast.LENGTH_SHORT).show()
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        enabled = !authUiState.isLoading
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // App Management Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = DarkGray),
                border = BorderStroke(1.dp, SilverMist.copy(alpha = 0.1f))
            ) {
                Column(modifier = Modifier.padding(24.dp)) {
                    Text(
                        text = "SYSTEM_CONTROL", 
                        style = KiriTypography.labelMedium.copy(color = SilverMist),
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    SettingsRow(
                        title = "CLEAR_HISTORY",
                        description = "Purge all conversation logs from secure storage.",
                        onClick = {
                            chatViewModel.clearAllHistory {
                                Toast.makeText(context, "LOGS_WIPED", Toast.LENGTH_SHORT).show()
                            }
                        },
                        icon = Icons.Default.Delete,
                        contentColor = ErrorCrimson
                    )

                    HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp), color = SilverMist.copy(alpha = 0.1f))

                    SettingsRow(
                        title = "SECURITY_MANAGEMENT",
                        description = "Rotate authentication keys and credentials.",
                        onClick = {
                            Toast.makeText(context, "SECURITY_PROTOCOL_ACTIVE", Toast.LENGTH_SHORT).show()
                        },
                        icon = Icons.Default.Lock
                    )

                    HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp), color = SilverMist.copy(alpha = 0.1f))

                    SettingsRow(
                        title = "HELP_AND_SUPPORT",
                        description = "Access documentation or contact development team.",
                        onClick = {
                            val intent = android.content.Intent(android.content.Intent.ACTION_VIEW, android.net.Uri.parse("https://github.com/kriss2012/kiri-ai/issues/18"))
                            context.startActivity(intent)
                        },
                        icon = Icons.Default.Help
                    )

                    HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp), color = SilverMist.copy(alpha = 0.1f))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text("BUILD_VERSION", style = KiriTypography.labelLarge)
                            Text("V1.2.3 // STABLE_PRODUCTION", style = KiriTypography.bodySmall)
                        }
                        Icon(Icons.Default.Info, contentDescription = null, tint = SilverMist, modifier = Modifier.size(20.dp))
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(24.dp))

            // App Details Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = DarkGray),
                border = BorderStroke(1.dp, SilverMist.copy(alpha = 0.1f))
            ) {
                Column(modifier = Modifier.padding(24.dp)) {
                    Text(
                        text = "TECHNICAL_SPECIFICATIONS", 
                        style = KiriTypography.labelMedium.copy(color = SilverMist),
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Text(
                        text = "Kiri AI implements a distributed multimodal reasoning architecture using the Bugatti design protocol. System utilizes low-latency fiber-optic routing for neural response generation.",
                        style = KiriTypography.bodySmall,
                        color = SilverMist
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "CORE_ENGINE: GEMINI_2.0_FLASH\nREGION: GLOBAL_EDGE\nENCRYPTION: AES_256_ACTIVE",
                        style = KiriTypography.labelSmall.copy(color = SilverMist, lineHeight = 18.sp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))
            
            KiriButton(
                text = "TERMINATE_SESSION // LOGOUT",
                onClick = { 
                    authViewModel.logout {
                        navController.navigate("landing") {
                            popUpTo(0) { inclusive = true }
                        }
                    }
                },
                containerColor = Color.Transparent,
                contentColor = ErrorCrimson,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun SettingsRow(
    title: String,
    description: String,
    onClick: () -> Unit,
    icon: ImageVector,
    contentColor: Color = ShowroomWhite
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(title, style = KiriTypography.labelLarge, color = contentColor)
            Text(description, style = KiriTypography.bodySmall, color = SilverMist)
        }
        Icon(icon, contentDescription = null, tint = contentColor.copy(alpha = 0.6f), modifier = Modifier.size(20.dp))
    }
}
