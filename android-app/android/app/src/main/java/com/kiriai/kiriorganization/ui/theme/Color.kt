package com.kiriai.kiriorganization.ui.theme

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

// Glassmorphism Tones (Enhanced for Depth)
val GlassWhite = Color(0x26FFFFFF) // 15% White
val GlassBlack = Color(0x4D000000) // 30% Black
val GlassBorderWhite = Color(0x33FFFFFF)
val GlassBorderBlack = Color(0x1A000000)
val GlassBackground = Color(0x0DFFFFFF) // 5% White overlay for texture
val GlassWhiteLight = Color(0x40FFFFFF) // 25% White for light mode surfaces
val GlassBlackLight = Color(0x0D000000) // 5% Black for light mode depth

// Gradient Accents (Cinematic)
val DeepSpaceBlue = Color(0xFF02060C)
val MidnightGrey = Color(0xFF080808)
val StarlightBlue = Color(0xFF0F172A)
val ElectricBlue = Color(0xFF3B82F6) // For subtle glow effects

// Removed: BrandPink, SoftGray, FocusBlue, BugattiBlue (Brand drift)
