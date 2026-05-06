package com.kiriai.kiriorganization.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.kiriai.kiriorganization.R
import com.kiriai.kiriorganization.ui.components.KiriButton
import com.kiriai.kiriorganization.ui.components.KiriSecondaryButton
import com.kiriai.kiriorganization.ui.components.BugattiLogo
import com.kiriai.kiriorganization.ui.theme.*

/**
 * Kiri AI - Landing Screen
 * 
 * Cinematic, monumental landing experience implementing the Bugatti Design System.
 * Focused on silent luxury and high-performance branding.
 */

import androidx.compose.ui.graphics.Brush
import androidx.compose.foundation.isSystemInDarkTheme

@Composable
fun LandingScreen(navController: NavController) {
    val scrollState = rememberScrollState()
    var startAnimation by remember { mutableStateOf(false) }
    val isDark = isSystemInDarkTheme()

    val backgroundBrush = Brush.verticalGradient(
        colors = listOf(
            VelvetBlack,
            if (isDark) DeepSpaceBlue else Color(0xFF111111)
        )
    )

    LaunchedEffect(Unit) {
        startAnimation = true
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundBrush)
            .verticalScroll(scrollState)
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Top Spacer for Cinematic Air
        Spacer(modifier = Modifier.height(80.dp))

        // App Logo - Cinematic Enhancement
        AnimatedVisibility(
            visible = startAnimation,
            enter = fadeIn(animationSpec = tween(1200, delayMillis = 200)) +
                    scaleIn(animationSpec = tween(1000, easing = EaseOutExpo), initialScale = 0.8f)
        ) {
            BugattiLogo(modifier = Modifier.padding(bottom = 48.dp))
        }

        // Feature Description (Quiet Mono)
        AnimatedVisibility(
            visible = startAnimation,
            enter = fadeIn(animationSpec = tween(1200, delayMillis = 600))
        ) {
            Text(
                text = "HYPER-PERFORMANCE REASONING // ATELIER_V1",
                style = KiriTypography.labelLarge.copy(
                    color = SilverMist,
                    textAlign = TextAlign.Center,
                    letterSpacing = 4.sp
                ),
                modifier = Modifier.padding(horizontal = 32.dp)
            )
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
            text = "KIRI AI // MULTIMODAL_INTEL // V1.2.3",
            style = KiriTypography.labelMedium.copy(color = SilverMist.copy(alpha = 0.3f)),
            textAlign = TextAlign.Center
        )
        
        Spacer(modifier = Modifier.height(48.dp))
    }
}
