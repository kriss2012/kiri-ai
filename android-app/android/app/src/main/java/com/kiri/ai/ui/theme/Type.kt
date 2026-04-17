package com.kiri.ai.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

/**
 * Bugatti Design System Typography
 * 
 * Hierarchy:
 * - Display: Bugatti Display (Monumental Scale 120sp+)
 * - UI/Labels: Bugatti Monospace (UPPERCASE, Technical)
 * - Body: Bugatti Text (Standard readable sans)
 */

val BugattiDisplayFont = FontFamily.SansSerif // Substitute for Bugatti Display
val BugattiMonoFont = FontFamily.Monospace   // Substitute for Bugatti Monospace
val BugattiTextFont = FontFamily.SansSerif    // Substitute for Bugatti Text

val KiriTypography = Typography(
    // Hero Display (Monumental) - Architectural presence
    displayLarge = TextStyle(
        fontFamily = BugattiDisplayFont,
        fontWeight = FontWeight.Normal,
        fontSize = 120.sp, 
        lineHeight = 110.sp,
        letterSpacing = 0.sp
    ),
    // Mid Display (Feature)
    headlineLarge = TextStyle(
        fontFamily = BugattiDisplayFont,
        fontWeight = FontWeight.Normal,
        fontSize = 64.sp,
        lineHeight = 64.sp,
        letterSpacing = 1.4.sp
    ),
    // Section Heading
    headlineMedium = TextStyle(
        fontFamily = BugattiDisplayFont,
        fontWeight = FontWeight.Normal,
        fontSize = 36.sp,
        lineHeight = 40.sp
    ),
    // UI Link / Button Label (CAPS) - The "Telemetry" feel
    labelLarge = TextStyle(
        fontFamily = BugattiMonoFont,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 1.4.sp
    ),
    // Caption Micro (CAPS)
    labelMedium = TextStyle(
        fontFamily = BugattiMonoFont,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 1.2.sp
    ),
    // Lead Body
    bodyLarge = TextStyle(
        fontFamily = BugattiTextFont,
        fontWeight = FontWeight.Normal,
        fontSize = 20.sp,
        lineHeight = 28.sp
    ),
    // Standard Body
    bodyMedium = TextStyle(
        fontFamily = BugattiTextFont,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp
    ),
    // Compact Body
    bodySmall = TextStyle(
        fontFamily = BugattiTextFont,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp
    )
)
