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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
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
    val bgColor = if (isDark) BrutalistBlack else BrutalistWhite
    val textColor = if (isDark) BrutalistWhite else BrutalistBlack

    LaunchedEffect(Unit) {
        startAnimation = true
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(bgColor)
            .verticalScroll(scrollState)
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(80.dp))

        AnimatedVisibility(
            visible = startAnimation,
            enter = fadeIn(animationSpec = tween(1000, delayMillis = 200)) +
                    scaleIn(animationSpec = tween(800, easing = EaseOutExpo), initialScale = 0.8f)
        ) {
            BugattiLogo(modifier = Modifier.padding(bottom = 48.dp))
        }

        AnimatedVisibility(
            visible = startAnimation,
            enter = fadeIn(animationSpec = tween(1000, delayMillis = 400))
        ) {
            Text(
                text = "HYPER-PERFORMANCE REASONING // ATELIER_V1",
                style = KiriTypography.labelLarge.copy(
                    color = if (isDark) BrutalistYellow else BrutalistDarkGray,
                    textAlign = TextAlign.Center,
                    letterSpacing = 2.sp,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.padding(horizontal = 32.dp)
            )
        }

        Spacer(modifier = Modifier.height(40.dp))

        AnimatedVisibility(
            visible = startAnimation,
            enter = fadeIn(animationSpec = tween(1000, delayMillis = 500)) + 
                    slideInVertically(initialOffsetY = { 20 })
        ) {
            Text(
                text = "HYPER-PERFORMANCE REASONING ENGINE.\nBUILT FOR COUTRE SOLUTIONS.",
                style = KiriTypography.labelMedium.copy(
                    color = if (isDark) BrutalistLightGray else BrutalistDarkGray,
                    textAlign = TextAlign.Center,
                    lineHeight = 24.sp,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.padding(horizontal = 32.dp)
            )
        }

        Spacer(modifier = Modifier.height(80.dp))

        AnimatedVisibility(
            visible = startAnimation,
            enter = fadeIn(animationSpec = tween(800, delayMillis = 600))
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

                Spacer(modifier = Modifier.height(20.dp))

                KiriSecondaryButton(
                    text = "SIGN IN",
                    onClick = { navController.navigate("login") },
                    modifier = Modifier.width(200.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(100.dp))
        
        Text(
            text = "KIRI AI // MULTIMODAL_INTEL // V2.0",
            style = KiriTypography.labelMedium.copy(
                color = if (isDark) BrutalistYellowDark.copy(alpha = 0.5f) else BrutalistDarkGray.copy(alpha = 0.5f),
                fontWeight = FontWeight.Bold
            ),
            textAlign = TextAlign.Center
        )
        
        Spacer(modifier = Modifier.height(48.dp))
    }
}
