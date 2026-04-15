package com.kiri.ai.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.kiri.ai.ui.components.KiriButton
import com.kiri.ai.ui.components.KiriTextField
import com.kiri.ai.ui.theme.*
import com.kiri.ai.ui.viewmodels.AuthViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

/**
 * Bugatti Showroom Login
 */

@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: AuthViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(VelvetBlack)
            .padding(32.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "ACCESS // LOGIN",
            style = KiriTypography.labelLarge,
            color = ShowroomWhite
        )
        
        Spacer(modifier = Modifier.height(12.dp))
        
        Text(
            text = "ENTER SECURE CREDENTIALS TO INITIALIZE SESSION.",
            style = KiriTypography.labelMedium.copy(
                color = SilverMist,
                lineHeight = 22.sp
            )
        )
        
        Spacer(modifier = Modifier.height(48.dp))
        
        if (state.error != null) {
            Text(
                text = "ERROR // ${state.error!!.uppercase()}",
                color = ShowroomWhite,
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
            isLoading = state.isLoading
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
