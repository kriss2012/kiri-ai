package com.kiri.ai.ui.theme

import android.app.Activity
import com.kiri.ai.utils.findActivity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

/**
 * Bugatti Design System Theme
 * 
 * Enforces a strict monochrome cinematic aesthetic.
 * Standard background is pure #000000 (Velvet Black).
 */

private val BugattiColorScheme = darkColorScheme(
    primary = ShowroomWhite,
    secondary = SilverMist,
    tertiary = DarkGray,
    background = VelvetBlack,
    surface = VelvetBlack,
    onPrimary = VelvetBlack,
    onSecondary = ShowroomWhite,
    onBackground = ShowroomWhite,
    onSurface = ShowroomWhite,
    error = SilverMist,
    outline = SilverMist
)

@Composable
fun KiriTheme(
    content: @Composable () -> Unit
) {
    // Bugatti style ignores system light/dark settings. It is always Cinema-Black.
    val colorScheme = BugattiColorScheme
    val view = LocalView.current
    
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context.findActivity() as? Activity)?.window
            window?.let {
                // Ensure pure black status and navigation bars for seamless showroom effect
                it.statusBarColor = VelvetBlack.toArgb()
                it.navigationBarColor = VelvetBlack.toArgb()
                
                val controller = WindowCompat.getInsetsController(it, view)
                // We are always in dark mode (white text on black background)
                controller.isAppearanceLightStatusBars = false
                controller.isAppearanceLightNavigationBars = false
            }
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = KiriTypography,
        content = content
    )
}
