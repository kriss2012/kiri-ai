package com.kiriai.kiriorganization.ui.screens

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
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
 * Bugatti Showroom Login
 */

@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: AuthViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val isDark = isSystemInDarkTheme()

    // THEME_ANIMATION_ENGINE: Consistent with ChatScreen
    val animatedBgStart by animateColorAsState(
        targetValue = if (isDark) DeepSpaceBlue else Color(0xFFF0F2F5),
        animationSpec = tween(1200, easing = LinearOutSlowInEasing), label = "bgStart"
    )
    val animatedBgEnd by animateColorAsState(
        targetValue = if (isDark) VelvetBlack else Color(0xFFFFFFFF),
        animationSpec = tween(1200, easing = LinearOutSlowInEasing), label = "bgEnd"
    )

    val backgroundBrush = Brush.verticalGradient(
        colors = listOf(animatedBgStart, animatedBgEnd)
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundBrush)
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .graphicsLayer {
                    clip = true
                    shape = RoundedCornerShape(24.dp)
                }
                .background(
                    color = (if (isDark) GlassBlack else GlassWhite).copy(alpha = 0.15f),
                    shape = RoundedCornerShape(24.dp)
                )
                .border(
                    width = 0.5.dp,
                    brush = Brush.linearGradient(
                        listOf(
                            if (isDark) GlassBorderWhite else GlassBorderBlack.copy(alpha = 0.1f),
                            Color.Transparent
                        )
                    ),
                    shape = RoundedCornerShape(24.dp)
                )
                .padding(32.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "ACCESS // LOGIN",
                style = KiriTypography.labelLarge,
                color = if (isDark) ShowroomWhite else VelvetBlack
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Text(
                text = "ENTER SECURE CREDENTIALS TO INITIALIZE SESSION.",
                style = KiriTypography.labelMedium.copy(
                    color = SilverMist,
                    lineHeight = 22.sp
                )
            )
            
            Spacer(modifier = Modifier.height(40.dp))
            
            val errorText = state.error
            if (errorText != null) {
                Text(
                    text = "ERROR // ${errorText.uppercase()}",
                    color = if (isDark) ShowroomWhite else Color.Red,
                    modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp),
                    style = KiriTypography.labelMedium
                )
            }
            
            KiriTextField(
                value = state.email,
                onValueChange = { viewModel.onEmailChange(it) },
                label = "ID_EMAIL",
                placeholder = "client@kiri.ai",
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
            )
            
            Spacer(modifier = Modifier.height(24.dp))
            
            KiriTextField(
                value = state.password,
                onValueChange = { viewModel.onPasswordChange(it) },
                label = "KEY_PASS",
                placeholder = "SECURE_KEY",
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )
            
            Spacer(modifier = Modifier.height(48.dp))
            
            KiriButton(
                text = "INITIALIZE_SESSION",
                onClick = {
                    viewModel.login {
                        navController.navigate("chat") {
                            popUpTo(0) { inclusive = true }
                        }
                    }
                },
                isLoading = state.isLoading,
                modifier = Modifier.fillMaxWidth().height(56.dp)
            )
            
            Spacer(modifier = Modifier.height(32.dp))
            
            Column {
                Text(
                    text = "NEW_ACCOUNT? REGISTER_HERE",
                    style = KiriTypography.labelMedium,
                    color = SilverMist,
                    modifier = Modifier.clickable { navController.navigate("register") }
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "← RETURN_TO_SHOWROOM",
                    style = KiriTypography.labelMedium,
                    color = SilverMist,
                    modifier = Modifier.clickable { navController.popBackStack() }
                )
            }
        }
    }
}
