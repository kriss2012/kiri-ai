package com.kiri.ai.ui.screens

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.compose.ui.graphics.Brush
import com.kiri.ai.ui.components.KiriButton

import com.kiri.ai.ui.components.KiriTextField
import com.kiri.ai.ui.theme.*
import com.kiri.ai.ui.viewmodels.AuthViewModel
import com.kiri.ai.ui.viewmodels.ChatViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navController: NavController,
    chatViewModel: ChatViewModel = hiltViewModel(),
    authViewModel: AuthViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val state = chatViewModel.uiState
    val authUiState = authViewModel.uiState
    var name by remember { mutableStateOf(state.user?.name ?: "") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Account Settings", style = KiriTypography.titleLarge) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Parchment)
            )
        },
        containerColor = Parchment
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(24.dp)
        ) {
            Text(
                text = "Manage your profile and subscription",
                style = KiriTypography.bodyLarge,
                color = OliveGray
            )
            
            Spacer(modifier = Modifier.height(32.dp))
            
            // Profile Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Ivory),
                border = BorderStroke(1.dp, BorderCream)
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
                                style = KiriTypography.headlineLarge.copy(color = Ivory)
                            )
                        }

                        Spacer(modifier = Modifier.width(20.dp))
                        Column {
                            Text(
                                text = state.user?.name ?: "User",
                                style = KiriTypography.titleLarge.copy(fontFamily = SerifFont)
                            )
                            Text(
                                text = state.user?.email ?: "email@example.com",
                                style = KiriTypography.bodyMedium,
                                color = OliveGray
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Badge(
                                containerColor = if (state.user?.isPremium == true) Color(0xFFFFD700).copy(alpha = 0.2f) else WarmSand,
                                contentColor = if (state.user?.isPremium == true) Color(0xFFB8860B) else CharcoalWarm
                            ) {
                                Text(state.user?.plan ?: "Free Plan")
                            }
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(32.dp))
                    
                    KiriTextField(
                        value = name,
                        onValueChange = { name = it },
                        label = "Display Name",
                        placeholder = "Your name"
                    )
                    
                    Spacer(modifier = Modifier.height(24.dp))
                    
                    KiriButton(
                        text = "Save Changes",
                        onClick = { 
                            authViewModel.updateProfile(name) {
                                Toast.makeText(context, "Profile updated", Toast.LENGTH_SHORT).show()
                            }
                        },
                        modifier = Modifier.width(160.dp),
                        enabled = !authUiState.isLoading
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Subscription Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Ivory),
                border = BorderStroke(1.dp, BorderCream)
            ) {
                Column(modifier = Modifier.padding(24.dp)) {
                    Text(text = "Subscription", style = KiriTypography.titleLarge.copy(fontFamily = SerifFont))
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Your current plan is ${state.user?.plan ?: "Free"}. Upgrade to Premium for unlimted requests and faster responses.",
                        style = KiriTypography.bodyMedium,
                        color = OliveGray
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    KiriButton(
                        text = "⚡ Manage Subscription",
                        onClick = { navController.navigate("pricing") }
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Logout
            KiriButton(
                text = "Log Out",
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
