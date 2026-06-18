package com.kiriai.kiriorganization.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.kiriai.kiriorganization.ui.components.KiriButton
import com.kiriai.kiriorganization.ui.components.KiriTextField
import com.kiriai.kiriorganization.ui.theme.*
import com.kiriai.kiriorganization.ui.viewmodels.AuthViewModel

/**
 * Modern Neo-Brutalist Registration Screen
 */
@Composable
fun RegisterScreen(
    navController: NavController,
    viewModel: AuthViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val isDark = isSystemInDarkTheme()
    val bgColor = if (isDark) BrutalistBlack else BrutalistWhite
    val cardBg = if (isDark) BrutalistDarkGray else BrutalistWhite
    val textColor = if (isDark) BrutalistWhite else BrutalistBlack
    val shadowColor = if (isDark) BrutalistLightGray else BrutalistBlack

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(bgColor)
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            // Offset shadow layer
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .offset(x = 6.dp, y = 6.dp)
                    .background(color = shadowColor, shape = RoundedCornerShape(12.dp))
                    .border(width = 3.dp, color = textColor, shape = RoundedCornerShape(12.dp))
            )

            // Foreground card content
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = cardBg, shape = RoundedCornerShape(12.dp))
                    .border(width = 3.dp, color = textColor, shape = RoundedCornerShape(12.dp))
                    .padding(32.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "ACCESS // REGISTER",
                    style = KiriTypography.headlineLarge,
                    color = textColor,
                    fontWeight = FontWeight.Black
                )
                
                Spacer(modifier = Modifier.height(12.dp))
                
                Text(
                    text = "INITIALIZE CLIENT PROFILE FOR HYPER-PERFORMANCE ACCESS.",
                    style = KiriTypography.labelMedium.copy(
                        color = if (isDark) BrutalistLightGray else BrutalistDarkGray,
                        lineHeight = 22.sp
                    )
                )
                
                Spacer(modifier = Modifier.height(24.dp))
                
                val errorText = state.error
                if (errorText != null) {
                    Text(
                        text = "ERROR // ${errorText.uppercase()}",
                        color = KiriError,
                        modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                        style = KiriTypography.labelMedium.copy(fontWeight = FontWeight.Bold)
                    )
                }
                
                KiriTextField(
                    value = state.name,
                    onValueChange = { viewModel.onNameChange(it) },
                    label = "PROFILE_NAME",
                    placeholder = "CLIENT_NAME"
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                KiriTextField(
                    value = state.email,
                    onValueChange = { viewModel.onEmailChange(it) },
                    label = "ID_EMAIL",
                    placeholder = "client@kiri.ai",
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                KiriTextField(
                    value = state.password,
                    onValueChange = { viewModel.onPasswordChange(it) },
                    label = "KEY_PASS",
                    placeholder = "SECURE_KEY",
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
                )
                
                Spacer(modifier = Modifier.height(36.dp))
                
                KiriButton(
                    text = "INIT_ACCOUNT",
                    onClick = {
                        if (state.name.isBlank() || state.email.isBlank() || state.password.isBlank()) {
                            viewModel.setError("All fields are required.")
                            return@KiriButton
                        }
                        if (state.password.length < 6) {
                            viewModel.setError("Password must be at least 6 characters.")
                            return@KiriButton
                        }
                        viewModel.register {
                            navController.navigate("chat") {
                                popUpTo(0) { inclusive = true }
                            }
                        }
                    },
                    isLoading = state.isLoading,
                    modifier = Modifier.fillMaxWidth()
                )
                
                Spacer(modifier = Modifier.height(32.dp))
                
                Column {
                    Text(
                        text = "ALREADY_REGISTERED? LOGIN_HERE",
                        style = KiriTypography.labelMedium.copy(
                            color = if (isDark) BrutalistYellow else BrutalistDarkGray,
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier.clickable { navController.navigate("login") }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "← RETURN_TO_SHOWROOM",
                        style = KiriTypography.labelMedium.copy(
                            color = if (isDark) BrutalistLightGray else BrutalistDarkGray,
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier.clickable { navController.popBackStack() }
                    )
                }
            }
        }
    }
}
