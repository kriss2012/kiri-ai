package com.kiri.ai.ui.theme

import android.app.Activity
import com.kiri.ai.utils.findActivity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import androidx.compose.animation.core.tween

private val KiriDarkColorScheme = darkColorScheme(
    primary = ShowroomWhite,
    secondary = SilverMist,
    tertiary = DarkGray,
    background = VelvetBlack,
    surface = Color(0xFF0A0A0A), // Slightly elevated from pure black
    onPrimary = VelvetBlack,
    onSecondary = ShowroomWhite,
    onBackground = ShowroomWhite,
    onSurface = ShowroomWhite,
    onSurfaceVariant = SilverMist,
    error = Color(0xFFCF6679),
    outline = SilverMist,
    surfaceVariant = Color(0xFF1A1A1A)
)

private val KiriLightColorScheme = lightColorScheme(
    primary = VelvetBlack,
    secondary = DarkGray,
    tertiary = SilverMist,
    background = Color(0xFFF9F7F2), // Premium Parchment White
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = VelvetBlack,
    onBackground = Color(0xFF1C1C1C), // Off-black for better readability
    onSurface = Color(0xFF1C1C1C),
    onSurfaceVariant = Color(0xFF4A4A4A),
    error = Color(0xFFB00020),
    outline = Color(0xFF8E8E8E),
    surfaceVariant = Color(0xFFECEAE4) // Slightly warmer variant
)

val LocalThemeMode = compositionLocalOf { true } // true = dark

@Composable
fun KiriTheme(
    darkTheme: Boolean = LocalThemeMode.current,
    content: @Composable () -> Unit
) {
    val targetColorScheme = if (darkTheme) KiriDarkColorScheme else KiriLightColorScheme
    
    // CINEMATIC_TRANSITION: Animate all core colors to prevent jarring theme jumps
    val background by androidx.compose.animation.animateColorAsState(targetColorScheme.background, tween(500), label = "bg")
    val surface by androidx.compose.animation.animateColorAsState(targetColorScheme.surface, tween(500), label = "surface")
    val primary by androidx.compose.animation.animateColorAsState(targetColorScheme.primary, tween(500), label = "primary")
    val onBackground by androidx.compose.animation.animateColorAsState(targetColorScheme.onBackground, tween(500), label = "onBg")
    val onSurface by androidx.compose.animation.animateColorAsState(targetColorScheme.onSurface, tween(500), label = "onSurface")
    val surfaceVariant by androidx.compose.animation.animateColorAsState(targetColorScheme.surfaceVariant, tween(500), label = "surfaceVariant")

    val animatedColorScheme = targetColorScheme.copy(
        background = background,
        surface = surface,
        primary = primary,
        onBackground = onBackground,
        onSurface = onSurface,
        surfaceVariant = surfaceVariant
    )

    val view = LocalView.current
    
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context.findActivity() as? Activity)?.window
            window?.let {
                it.statusBarColor = animatedColorScheme.background.toArgb()
                it.navigationBarColor = animatedColorScheme.background.toArgb()
                
                val controller = WindowCompat.getInsetsController(it, view)
                controller.isAppearanceLightStatusBars = !darkTheme
                controller.isAppearanceLightNavigationBars = !darkTheme
            }
        }
    }

    MaterialTheme(
        colorScheme = animatedColorScheme,
        typography = KiriTypography,
        content = content
    )
}
