package com.kiri.ai.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.kiri.ai.ui.components.KiriButton
import com.kiri.ai.ui.components.KiriTextField
import com.kiri.ai.ui.theme.*
import com.kiri.ai.ui.viewmodels.ChatViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navController: NavController,
    viewModel: ChatViewModel = hiltViewModel()
) {
    val state = viewModel.uiState
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
            androidx.compose.material3.Card(
                modifier = Modifier.fillMaxWidth(),
                shape = androidx.compose.foundation.shape.RoundedCornerShape(16.dp),
                colors = androidx.compose.material3.CardDefaults.cardColors(containerColor = Ivory),
                border = androidx.compose.foundation.BorderStroke(1.dp, BorderCream)
            ) {
                Column(modifier = Modifier.padding(24.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(72.dp)
                                .background(TerracottaBrand, androidx.compose.foundation.shape.CircleShape),
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
                        onClick = { /* Implement profile update logic */ },
                        modifier = Modifier.width(160.dp)
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Subscription Card
            androidx.compose.material3.Card(
                modifier = Modifier.fillMaxWidth(),
                shape = androidx.compose.foundation.shape.RoundedCornerShape(16.dp),
                colors = androidx.compose.material3.CardDefaults.cardColors(containerColor = Ivory),
                border = androidx.compose.foundation.BorderStroke(1.dp, BorderCream)
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
                    /* Implement logout */ 
                    navController.navigate("landing") {
                        popUpTo("chat") { inclusive = true }
                    }
                },
                containerColor = Color.Transparent,
                contentColor = ErrorCrimson,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
