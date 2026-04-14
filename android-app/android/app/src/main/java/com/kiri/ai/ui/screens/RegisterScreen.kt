package com.kiri.ai.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.kiri.ai.ui.components.KiriButton
import com.kiri.ai.ui.components.KiriTextField
import com.kiri.ai.ui.theme.*
import com.kiri.ai.ui.viewmodels.AuthViewModel

@Composable
fun RegisterScreen(
    navController: NavController,
    viewModel: AuthViewModel = hiltViewModel()
) {
    val state = viewModel.uiState

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Parchment)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Auth Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = Ivory),
            border = BorderStroke(1.dp, BorderCream)
        ) {
            Column(
                modifier = Modifier.padding(32.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "Create account",
                    style = KiriTypography.headlineMedium,
                    color = AnthropicNearBlack
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Text(
                    text = "Join Kiri AI to start thinking deeper and creating without limits.",
                    style = KiriTypography.bodyMedium,
                    color = OliveGray
                )
                
                Spacer(modifier = Modifier.height(32.dp))
                
                if (state.error != null) {
                    Text(
                        text = state.error!!,
                        color = ErrorCrimson,
                        modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                        style = KiriTypography.labelLarge
                    )
                }
                
                KiriTextField(
                    value = state.name,
                    onValueChange = { viewModel.onNameChange(it) },
                    label = "Full Name",
                    placeholder = "Your name",
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                KiriTextField(
                    value = state.email,
                    onValueChange = { viewModel.onEmailChange(it) },
                    label = "Email address",
                    placeholder = "name@example.com",
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                KiriTextField(
                    value = state.password,
                    onValueChange = { viewModel.onPasswordChange(it) },
                    label = "Password",
                    placeholder = "At least 6 characters",
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
                )
                
                Spacer(modifier = Modifier.height(32.dp))
                
                KiriButton(
                    text = "Create Account — It's Free",
                    onClick = {
                        viewModel.register {
                            navController.navigate("chat") {
                                popUpTo(0) { inclusive = true }
                            }
                        }
                    },
                    isLoading = state.isLoading
                )
                
                Spacer(modifier = Modifier.height(24.dp))
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start
                ) {
                    Text(text = "Already have an account? ", style = KiriTypography.bodySmall)
                    Text(
                        text = "Sign in",
                        style = KiriTypography.bodySmall.copy(fontWeight = FontWeight.Bold),
                        color = TerracottaBrand,
                        modifier = Modifier.clickable { navController.navigate("login") }
                    )
                }
            }
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        Text(
            text = "← Back to home",
            style = KiriTypography.bodyMedium,
            color = StoneGray,
            modifier = Modifier.clickable { navController.popBackStack() }
        )
    }
}
