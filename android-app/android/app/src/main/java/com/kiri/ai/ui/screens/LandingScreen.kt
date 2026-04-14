package com.kiri.ai.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.kiri.ai.ui.components.KiriButton
import com.kiri.ai.ui.components.KiriSecondaryButton
import com.kiri.ai.ui.theme.*

/**
 * Bugatti-Inspired Landing Screen
 * 
 * Cinematic, monumental, and ultra-monochrome.
 * Focuses on high-performance scale and silent luxury.
 */

@Composable
fun LandingScreen(navController: NavController) {
    val scrollState = rememberScrollState()
    var startAnimation by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        startAnimation = true
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(VelvetBlack)
            .verticalScroll(scrollState)
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Top Spacer for Cinematic Air
        Spacer(modifier = Modifier.height(100.dp))

        // Monumental Branding
        AnimatedVisibility(
            visible = startAnimation,
            enter = fadeIn(animationSpec = tween(1500)) + 
                    expandVertically(animationSpec = tween(1000, easing = EaseOutExpo))
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "KIRI",
                    style = KiriTypography.displayLarge.copy(
                        color = ShowroomWhite,
                        fontSize = 120.sp, // Monumental scale
                        lineHeight = 110.sp
                    ),
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "INTELLIGENCE",
                    style = KiriTypography.labelLarge.copy(
                        color = ShowroomWhite,
                        letterSpacing = 8.sp // Ultra-wide tracking
                    ),
                    textAlign = TextAlign.Center
                )
            }
        }

        Spacer(modifier = Modifier.height(60.dp))

        // Feature Description (Quiet Mono)
        AnimatedVisibility(
            visible = startAnimation,
            enter = fadeIn(animationSpec = tween(1200, delayMillis = 500)) + 
                    slideInVertically(initialOffsetY = { 20 })
        ) {
            Text(
                text = "HYPER-PERFORMANCE REASONING ENGINE.\nBUILT FOR COUTRE SOLUTIONS.",
                style = KiriTypography.labelMedium.copy(
                    color = SilverMist,
                    textAlign = TextAlign.Center,
                    lineHeight = 24.sp
                ),
                modifier = Modifier.padding(horizontal = 32.dp)
            )
        }

        Spacer(modifier = Modifier.height(120.dp))

        // Primary Call to Action
        AnimatedVisibility(
            visible = startAnimation,
            enter = fadeIn(animationSpec = tween(1000, delayMillis = 800))
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                KiriButton(
                    text = "ENTER THE ATELIER",
                    onClick = { navController.navigate("register") },
                    modifier = Modifier.width(280.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                KiriSecondaryButton(
                    text = "SIGN IN",
                    onClick = { navController.navigate("login") },
                    modifier = Modifier.width(200.dp)
                )
            }
        }

        // Bottom Decorative Spacer
        Spacer(modifier = Modifier.height(100.dp))
        
        Text(
            text = "V1.1 // CORE STABILITY ACTIVE",
            style = KiriTypography.labelMedium.copy(color = SilverMist.copy(alpha = 0.3f)),
            textAlign = TextAlign.Center
        )
        
        Spacer(modifier = Modifier.height(48.dp))
    }
}
