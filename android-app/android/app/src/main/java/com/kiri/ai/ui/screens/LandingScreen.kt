package com.kiri.ai.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
                    .background(TerracottaBrand.copy(alpha = 0.1f), shape = androidx.compose.foundation.shape.CircleShape)
                    .padding(horizontal = 16.dp, vertical = 6.dp)
            ) {
                Text(
                    text = "✦ AI-POWERED INTELLIGENCE",
                    style = KiriTypography.labelLarge.copy(
                        color = TerracottaBrand,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                )
            }
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        FadeUpAnimation(visible = animate, delayMillis = 200) {
            Text(
                text = "Kiri AI",
                style = KiriTypography.displayLarge.copy(
                    brush = Brush.linearGradient(
                        colors = listOf(AnthropicNearBlack, TerracottaBrand)
                    )
                ),
                textAlign = TextAlign.Center
            )
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        FadeUpAnimation(visible = animate, delayMillis = 300) {
            Text(
                text = "Your intelligent assistant that thinks deeper, reasons sharper, and creates without limits. Powered by Google Gemini.",
                style = KiriTypography.bodyLarge.copy(
                    color = OliveGray,
                    textAlign = TextAlign.Center,
                    lineHeight = 28.sp
                ),
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
        
        Spacer(modifier = Modifier.height(32.dp))
        
        // Hero Illustration
        FadeUpAnimation(visible = animate, delayMillis = 400) {
            /* 
               Note: In a real project, hero_illustration would be in res/drawable.
               Using a placeholder that represents the generated organic illustration.
            */
            Box(modifier = Modifier.fillMaxWidth().height(240.dp), contentAlignment = Alignment.Center) {
                // Mocking the Image load since I can't actually compile/run android here
                // But I've placed the file in drawable/hero_illustration.png
                Text("Organic Hand-Drawn Illustration Placeholder", color = OliveGray)
            }
        }
        
        Spacer(modifier = Modifier.height(32.dp))
        
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
