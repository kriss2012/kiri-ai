package com.kiri.ai.ui.screens

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
    val colorScheme = MaterialTheme.colorScheme

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { 
                    Text(
                        "USER_ATELIER // PROFILE", 
                        style = KiriTypography.labelLarge,
                        letterSpacing = 2.sp
                    ) 
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = colorScheme.background,
                    titleContentColor = colorScheme.onBackground,
                    navigationIconContentColor = colorScheme.onBackground
                )
            )
        },
        containerColor = colorScheme.background
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp, vertical = 32.dp)
        ) {
            // HIGH_PERFORMANCE_AVATAR
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 40.dp),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .border(1.dp, colorScheme.primary.copy(alpha = 0.3f), CircleShape)
                        .padding(4.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(colorScheme.primary, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = state.user?.name?.take(1)?.uppercase() ?: "U",
                            style = KiriTypography.headlineLarge.copy(
                                color = colorScheme.onPrimary,
                                fontSize = 48.sp
                            )
                        )
                    }
                }
            }

            // TECHNICAL_DATA_SECTION
            Text(
                "IDENTITY_MODULE",
                style = KiriTypography.labelSmall,
                color = colorScheme.primary.copy(alpha = 0.5f),
                modifier = Modifier.padding(bottom = 16.dp)
            )
            
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(colorScheme.surfaceVariant.copy(alpha = 0.3f), RoundedCornerShape(4.dp))
                    .padding(20.dp)
            ) {
                Text(
                    text = state.user?.email ?: "NULL_AUTH_EMAIL",
                    style = KiriTypography.bodyLarge,
                    color = colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(24.dp))
                
                KiriTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = "RENAME_USER",
                    placeholder = "ENTER_NEW_ALIAS"
                )
                
                Spacer(modifier = Modifier.height(24.dp))
                
                KiriButton(
                    text = "COMMIT_CHANGES",
                    onClick = { 
                        authViewModel.updateProfile(name) {
                            Toast.makeText(context, "DATA_SYNC_COMPLETE", Toast.LENGTH_SHORT).show()
                        }
                    },
                    modifier = Modifier.fillMaxWidth().height(48.dp),
                    isLoading = authUiState.isLoading,
                    shape = RoundedCornerShape(2.dp)
                )
            }

            Spacer(modifier = Modifier.height(40.dp))

            // SUBSCRIPTION_DATA_SECTION
            Text(
                "SUBSCRIPTION_STATUS",
                style = KiriTypography.labelSmall,
                color = colorScheme.primary.copy(alpha = 0.5f),
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, colorScheme.outline.copy(alpha = 0.1f), RoundedCornerShape(4.dp))
                    .padding(20.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = (state.user?.plan ?: "FREE_TIER").uppercase(),
                        style = KiriTypography.labelLarge,
                        color = colorScheme.primary
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    if (state.user?.isPremium == true) {
                        Surface(
                            color = colorScheme.primary.copy(alpha = 0.1f),
                            shape = RoundedCornerShape(4.dp)
                        ) {
                            Text(
                                "ACTIVE",
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                                style = KiriTypography.labelSmall,
                                color = colorScheme.primary
                            )
                        }
                    }
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Text(
                    text = "ACCESS_LEVEL: Full multimodal support, unlimited queries, and priority reasoning cycles.",
                    style = KiriTypography.bodySmall,
                    color = colorScheme.onSurface.copy(alpha = 0.7f)
                )
                
                Spacer(modifier = Modifier.height(32.dp))
                
                KiriButton(
                    text = "MANAGE_SUBSCRIPTION",
                    onClick = { navController.navigate("pricing") },
                    modifier = Modifier.fillMaxWidth().height(44.dp),
                    containerColor = Color.Transparent,
                    contentColor = colorScheme.primary,
                    border = BorderStroke(1.dp, colorScheme.primary.copy(alpha = 0.2f)),
                    shape = RoundedCornerShape(2.dp)
                )
            }

            Spacer(modifier = Modifier.height(48.dp))

            // DESTRUCTIVE_ACTIONS
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
                contentColor = colorScheme.error,
                modifier = Modifier.fillMaxWidth()
            )
            
            Spacer(modifier = Modifier.height(64.dp))
        }
    }
}
