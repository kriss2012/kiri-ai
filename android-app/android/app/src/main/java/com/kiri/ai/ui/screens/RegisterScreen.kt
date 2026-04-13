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
        androidx.compose.material3.Card(
            modifier = Modifier.fillMaxWidth(),
            shape = androidx.compose.foundation.shape.RoundedCornerShape(20.dp),
            colors = androidx.compose.material3.CardDefaults.cardColors(containerColor = Ivory),
            border = androidx.compose.foundation.BorderStroke(1.dp, BorderCream)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Kiri AI",
                    style = KiriTypography.headlineLarge,
                    color = TerracottaBrand
                )
                Text(
                    text = "Think Deeper",
                    style = KiriTypography.labelLarge,
                    color = StoneGray
                )
                
                Spacer(modifier = Modifier.height(32.dp))
                
                Text(
                    text = "Create your account",
                    style = KiriTypography.headlineMedium,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Start
                )
                
                Spacer(modifier = Modifier.height(24.dp))
                
                if (state.error != null) {
                    Text(
                        text = state.error!!,
                        color = ErrorCrimson,
                        modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                        fontSize = 14.sp
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
                    label = "Email",
                    placeholder = "you@example.com",
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
                
                Spacer(modifier = Modifier.height(24.dp))
                
                KiriButton(
                    text = "Create Account — It's Free",
                    onClick = { viewModel.register { navController.navigate("chat") } },
                    isLoading = state.isLoading
                )
                
                Spacer(modifier = Modifier.height(24.dp))
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(text = "Already have an account? ", style = KiriTypography.bodyMedium, color = OliveGray)
                    Text(
                        text = "Sign in",
                        style = KiriTypography.bodyMedium.copy(fontWeight = FontWeight.Bold),
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
            modifier = Modifier.clickable { navController.navigate("landing") }
        )
    }
}
