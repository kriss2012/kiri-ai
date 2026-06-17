package com.kiriai.kiriorganization.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

/**
 * Modern Neo-Brutalist Typography System
 */

val BrutalistFontFamily = FontFamily.SansSerif
val BrutalistMonoFamily = FontFamily.Monospace

val KiriTypography = Typography(
    // Extra large titles
    displayLarge = TextStyle(
        fontFamily = BrutalistFontFamily,
        fontWeight = FontWeight.Black,
        fontSize = 38.sp, 
        lineHeight = 44.sp,
        letterSpacing = (-1).sp
    ),
    // Mid Titles
    headlineLarge = TextStyle(
        fontFamily = BrutalistFontFamily,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 28.sp,
        lineHeight = 34.sp,
        letterSpacing = (-0.5).sp
    ),
    // Section Heading
    headlineMedium = TextStyle(
        fontFamily = BrutalistFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 22.sp,
        lineHeight = 28.sp
    ),
    // Buttons and Large Labels (CAPS)
    labelLarge = TextStyle(
        fontFamily = BrutalistMonoFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 1.sp
    ),
    // Technical Badges/Metadata
    labelMedium = TextStyle(
        fontFamily = BrutalistMonoFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    ),
    // Lead Body text
    bodyLarge = TextStyle(
        fontFamily = BrutalistFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 18.sp,
        lineHeight = 26.sp
    ),
    // Standard Body text
    bodyMedium = TextStyle(
        fontFamily = BrutalistFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 15.sp,
        lineHeight = 22.sp
    ),
    // Compact metadata
    bodySmall = TextStyle(
        fontFamily = BrutalistFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 13.sp,
        lineHeight = 18.sp
    )
)
