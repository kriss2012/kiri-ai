package com.kiri.ai.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// Anthropics Serif substitute (Georgia / Serif)
val SerifFont = FontFamily.Serif
// Anthropics Sans substitute (System Sans)
val SansFont = FontFamily.SansSerif

val KiriTypography = Typography(
    displayLarge = TextStyle(
        fontFamily = SerifFont,
        fontWeight = FontWeight.Medium,
        fontSize = 64.sp,
        lineHeight = 70.sp,
        letterSpacing = 0.sp,
        color = AnthropicNearBlack
    ),
    headlineLarge = TextStyle(
        fontFamily = SerifFont,
        fontWeight = FontWeight.Medium,
        fontSize = 52.sp,
        lineHeight = 62.sp,
        color = AnthropicNearBlack
    ),
    headlineMedium = TextStyle(
        fontFamily = SerifFont,
        fontWeight = FontWeight.Medium,
        fontSize = 32.sp,
        lineHeight = 35.sp,
        color = AnthropicNearBlack
    ),
    titleLarge = TextStyle(
        fontFamily = SerifFont,
        fontWeight = FontWeight.Medium,
        fontSize = 25.sp,
        lineHeight = 30.sp,
        color = AnthropicNearBlack
    ),
    titleMedium = TextStyle(
        fontFamily = SerifFont,
        fontWeight = FontWeight.Medium,
        fontSize = 20.sp,
        lineHeight = 24.sp,
        color = AnthropicNearBlack
    ),
    bodyLarge = TextStyle(
        fontFamily = SansFont,
        fontWeight = FontWeight.Normal,
        fontSize = 17.sp,
        lineHeight = 27.sp,
        color = AnthropicNearBlack
    ),
    bodyMedium = TextStyle(
        fontFamily = SansFont,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        color = AnthropicNearBlack
    ),
    bodySmall = TextStyle(
        fontFamily = SansFont,
        fontWeight = FontWeight.Normal,
        fontSize = 15.sp,
        lineHeight = 22.sp,
        color = OliveGray
    ),
    labelLarge = TextStyle(
        fontFamily = SansFont,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        color = OliveGray
    ),
    labelMedium = TextStyle(
        fontFamily = SansFont,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.12.sp,
        color = StoneGray
    )
)
