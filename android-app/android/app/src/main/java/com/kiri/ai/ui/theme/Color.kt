package com.kiri.ai.ui.theme

import androidx.compose.ui.graphics.Color

/**
 * Bugatti Design System Palette
 * 
 * Based on the extreme monochromatic showroom aesthetic:
 * Pure Black, Brilliant White, and Technical Gray.
 */

// Core Canvas
val VelvetBlack = Color(0xFF000000)
val ShowroomWhite = Color(0xFFFFFFFF)

// Technical / Semantic
val SilverMist = Color(0xFF999999) // Mid Gray for tertiary elements
val DarkGray = Color(0xFF1A1A1A)   // For rare containers that need subtle depth
val ObsidianSurface = Color(0xFF030303) // Near-pure black for slight surface separation

// Interaction states (High Contrast)
val HoverOverlay = Color(0x1AFFFFFF) // 10% white for hover on black
val ActiveOverlay = Color(0x33FFFFFF) // 20% white for active state

// Logic fallback (In case any non-theme elements need standard error/blue)
val FocusBlue = Color(0xFF3898EC)
val ErrorNeutral = Color(0xFF999999) // Bugatti uses gray for errors

// Logo & Brand
val BugattiBlue = Color(0xFF00AEEF) // Optional: Pure EB Blue for small brand moments if needed
val BrandGradient = listOf(Color(0xFFFFFFFF), Color(0xFF999999))
