package com.kiri.ai.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.kiri.ai.ui.components.FadeUpAnimation
import com.kiri.ai.ui.components.KiriButton
import com.kiri.ai.ui.components.KiriOutlineButton
import com.kiri.ai.ui.theme.*

@Composable
fun LandingScreen(navController: NavController) {
    var animate by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { animate = true }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Parchment)
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(60.dp))
        
        FadeUpAnimation(visible = animate, delayMillis = 100) {
            Box(
                modifier = Modifier
                    .background(
                        brush = Brush.linearGradient(LogoGradient),
                        shape = CircleShape
                    )
                    .padding(horizontal = 16.dp, vertical = 6.dp)
            ) {
                Text(
                    text = "✦ AI-POWERED INTELLIGENCE",
                    style = KiriTypography.labelLarge.copy(
                        color = Color.White,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                )
            }
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        FadeUpAnimation(visible = animate, delayMillis = 200) {
            Image(
                painter = painterResource(id = com.kiri.ai.R.drawable.app_logo),
                contentDescription = "Kiri AI Logo",
                modifier = Modifier.size(120.dp)
            )
        }
        
        FadeUpAnimation(visible = animate, delayMillis = 250) {
            Text(
                text = "Kiri AI",
                style = KiriTypography.displayLarge.copy(
                    brush = Brush.linearGradient(LogoGradient)
                ),
                textAlign = TextAlign.Center
            )
        }



        
        Spacer(modifier = Modifier.height(24.dp))
        
        FadeUpAnimation(visible = animate, delayMillis = 300) {
            Text(
                text = "Your intelligent assistant that thinks deeper, reasons sharper, and creates without limits.",
                style = KiriTypography.bodyLarge.copy(
                    color = OliveGray,
                    textAlign = TextAlign.Center,
                    lineHeight = 32.sp // More relaxed for editorial feel
                ),
                modifier = Modifier.padding(horizontal = 24.dp)
            )
        }
        
        Spacer(modifier = Modifier.height(32.dp))
        
        Spacer(modifier = Modifier.height(8.dp))
        
        FadeUpAnimation(visible = animate, delayMillis = 500) {
            Column {
                KiriButton(
                    text = "Start for Free",
                    onClick = { navController.navigate("register") }
                )
                
                Spacer(modifier = Modifier.height(12.dp))
                
                KiriOutlineButton(
                    text = "Sign In",
                    onClick = { navController.navigate("login") }
                )
            }
        }
        
        Spacer(modifier = Modifier.height(64.dp))
        
        // Features Section
        FadeUpAnimation(visible = animate, delayMillis = 600) {
            Column {
                FeatureCard(
                    icon = "⚡",
                    title = "Lightning Fast",
                    desc = "Get answers in seconds with Gemini 2.0 Flash."
                )
                Spacer(modifier = Modifier.height(16.dp))
                FeatureCard(
                    icon = "🧠",
                    title = "Deep Reasoning",
                    desc = "Complex analysis and creative writing."
                )
            }
        }
        
        Spacer(modifier = Modifier.height(48.dp))
    }
}

@Composable
fun FeatureCard(
    icon: String,
    title: String,
    desc: String
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Ivory),
        border = BorderStroke(1.dp, BorderCream)
    ) {
        Row(
            modifier = Modifier.padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(Parchment, RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text(text = icon, fontSize = 24.sp)
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = title,
                    style = KiriTypography.titleMedium,
                    color = AnthropicNearBlack
                )
                Text(
                    text = desc,
                    style = KiriTypography.bodySmall,
                    color = OliveGray,
                    lineHeight = 20.sp
                )
            }
        }
    }
}
