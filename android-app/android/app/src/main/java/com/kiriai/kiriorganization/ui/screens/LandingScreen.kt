package com.kiriai.kiriorganization.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.kiriai.kiriorganization.ui.components.BugattiLogo
import com.kiriai.kiriorganization.ui.components.KiriButton
import com.kiriai.kiriorganization.ui.components.KiriSecondaryButton
import com.kiriai.kiriorganization.ui.theme.*

@Composable
fun LandingScreen(navController: NavController) {
    val scrollState = rememberScrollState()
    var startAnimation by remember { mutableStateOf(false) }
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
                    color = if (isDark) SilverMist else VelvetBlack.copy(alpha = 0.6f),
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
                    color = if (isDark) SilverMist else VelvetBlack.copy(alpha = 0.8f),
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
                    modifier = Modifier.width(280.dp),
                    contentColor = if (isDark) ShowroomWhite else VelvetBlack,
                    border = androidx.compose.foundation.BorderStroke(1.dp, (if (isDark) ShowroomWhite else VelvetBlack).copy(alpha = 0.8f))
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
