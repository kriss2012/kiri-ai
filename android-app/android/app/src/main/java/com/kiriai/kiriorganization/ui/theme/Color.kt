package com.kiriai.kiriorganization.ui.theme

import androidx.compose.ui.graphics.Color

/**
 * Modern Neo-Brutalist Design System Palette
 */

// Core Canvas Tones
val BrutalistWhite = Color(0xFFFFFFFF)
val BrutalistYellow = Color(0xFFFFF4B8)      // Primary accent / highlight (80% Light)
val BrutalistYellowDark = Color(0xFFFFE680)  // Selected/Active state indicator
val BrutalistBlack = Color(0xFF000000)
val BrutalistDarkGray = Color(0xFF333333)
val BrutalistLightGray = Color(0xFFF3F3F3)

// Semantic Mappings
val KiriBackground = BrutalistWhite
val KiriSurface = BrutalistWhite
val KiriPrimary = BrutalistYellow
val KiriSecondary = BrutalistYellowDark
val KiriError = Color(0xFFE1251B)

// Legacy aliases mapping to Brutalist tokens for zero-break compatibility
val VelvetBlack = BrutalistBlack
val ShowroomWhite = BrutalistWhite
val SilverMist = BrutalistDarkGray
val DarkGray = BrutalistDarkGray
val Ivory = BrutalistWhite
val Parchment = BrutalistWhite
val OliveGray = BrutalistDarkGray
val StoneGray = BrutalistDarkGray
val TerracottaBrand = BrutalistYellow
val ErrorCrimson = Color(0xFFE1251B)
val AnthropicNearBlack = BrutalistBlack
val BorderCream = BrutalistBlack
val LogoGradient = BrutalistBlack
val WarmSand = BrutalistLightGray
val CharcoalWarm = BrutalistDarkGray
val SerifFont = androidx.compose.ui.text.font.FontFamily.SansSerif

// Technical / Semantic
val ObsidianSurface = BrutalistWhite

// Interaction states
val HoverOverlay = Color(0x1A000000)
val ActiveOverlay = Color(0x33000000)

// Glassmorphism aliases mapped to flat colors for Neo-Brutalist compatibility
val GlassWhite = BrutalistWhite
val GlassBlack = BrutalistLightGray
val GlassBorderWhite = BrutalistBlack
val GlassBorderBlack = BrutalistBlack
val GlassBackground = BrutalistWhite
val GlassWhiteLight = BrutalistWhite
val GlassBlackLight = BrutalistLightGray

// Flat Accent Colors
val DeepSpaceBlue = BrutalistWhite
val MidnightGrey = BrutalistWhite
val StarlightBlue = BrutalistWhite
val ElectricBlue = BrutalistYellow
