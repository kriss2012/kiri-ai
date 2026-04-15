package com.kiri.ai.ui.theme

import androidx.compose.ui.graphics.Color

/**
 * Bugatti Design System Palette
 * 
 * Based on the extreme monochromatic showroom aesthetic:
 * Pure Black, Brilliant White, and Technical Gray.
 */

// Core Canvas (Velvet Black)
val VelvetBlack = Color(0xFF000000)
val ShowroomWhite = Color(0xFFFFFFFF)
val SilverMist = Color(0xFF999999) 
val DarkGray = Color(0xFF111111) // Technical depth only

// Semantic Aliases for high-performance interaction
val KiriBackground = VelvetBlack
val KiriSurface = VelvetBlack
val KiriPrimary = ShowroomWhite
val KiriSecondary = SilverMist
val KiriError = SilverMist // Bugatti doesn't use red

// Legacy compatibility (re-mapped to Bugatti tokens)
val Ivory = ShowroomWhite
val Parchment = VelvetBlack
val OliveGray = SilverMist
val StoneGray = SilverMist
val TerracottaBrand = ShowroomWhite
val ErrorCrimson = SilverMist
val AnthropicNearBlack = VelvetBlack
val BorderCream = SilverMist
val LogoGradient = ShowroomWhite
val WarmSand = SilverMist
val CharcoalWarm = DarkGray
val SerifFont = androidx.compose.ui.text.font.FontFamily.SansSerif

// Technical / Semantic
val ObsidianSurface = Color(0xFF030303)

// Interaction states
val HoverOverlay = Color(0x1AFFFFFF)
val ActiveOverlay = Color(0x33FFFFFF)

// Removed: BrandPink, SoftGray, FocusBlue, BugattiBlue (Brand drift)
