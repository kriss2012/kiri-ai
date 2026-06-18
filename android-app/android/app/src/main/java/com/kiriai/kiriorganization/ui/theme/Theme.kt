package com.kiriai.kiriorganization.ui.theme

import android.app.Activity
import com.kiriai.kiriorganization.utils.findActivity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val KiriDarkColorScheme = darkColorScheme(
    primary = BrutalistYellowDark,
    onPrimary = BrutalistBlack,
    secondary = BrutalistYellow,
    onSecondary = BrutalistBlack,
    tertiary = BrutalistWhite,
    background = BrutalistBlack,
    onBackground = BrutalistWhite,
    surface = BrutalistBlack,
    onSurface = BrutalistWhite,
    onSurfaceVariant = BrutalistLightGray,
    error = Color(0xFFCF6679),
    outline = BrutalistWhite,
    surfaceVariant = BrutalistDarkGray
)

private val KiriLightColorScheme = lightColorScheme(
    primary = BrutalistYellow,
    onPrimary = BrutalistBlack,
    secondary = BrutalistYellowDark,
    onSecondary = BrutalistBlack,
    tertiary = BrutalistDarkGray,
    background = BrutalistWhite,
    onBackground = BrutalistBlack,
    surface = BrutalistWhite,
    onSurface = BrutalistBlack,
    onSurfaceVariant = BrutalistDarkGray,
    error = Color(0xFFE1251B),
    outline = BrutalistBlack,
    surfaceVariant = BrutalistLightGray
)

val LocalThemeMode = compositionLocalOf { false } // Default to light mode (Brutalist style)

@Composable
fun KiriTheme(
    darkTheme: Boolean = LocalThemeMode.current,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) KiriDarkColorScheme else KiriLightColorScheme
    val view = LocalView.current
    
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context.findActivity() as? Activity)?.window
            window?.let {
                it.statusBarColor = colorScheme.background.toArgb()
                it.navigationBarColor = colorScheme.background.toArgb()
                
                val controller = WindowCompat.getInsetsController(it, view)
                controller.isAppearanceLightStatusBars = !darkTheme
                controller.isAppearanceLightNavigationBars = !darkTheme
            }
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = KiriTypography,
        content = content
    )
}
