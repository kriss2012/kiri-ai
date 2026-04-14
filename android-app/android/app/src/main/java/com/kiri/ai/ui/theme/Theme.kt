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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val KiriDarkColorScheme = darkColorScheme(
    primary = ShowroomWhite,
    secondary = SilverMist,
    tertiary = DarkGray,
    background = VelvetBlack,
    surface = Color(0xFF121212), // Elevated dark surface
    onPrimary = VelvetBlack,
    onSecondary = ShowroomWhite,
    onBackground = ShowroomWhite,
    onSurface = ShowroomWhite,
    onSurfaceVariant = SilverMist,
    error = Color(0xFFCF6679),
    outline = SilverMist,
    surfaceVariant = Color(0xFF1E1E1E)
)

private val KiriLightColorScheme = lightColorScheme(
    primary = VelvetBlack,
    secondary = DarkGray,
    tertiary = SilverMist,
    background = Color(0xFFFBFBFB), // Clean Paper White
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = VelvetBlack,
    onBackground = VelvetBlack,
    onSurface = VelvetBlack,
    onSurfaceVariant = Color(0xFF424242),
    error = Color(0xFFB00020),
    outline = Color(0xFF757575),
    surfaceVariant = Color(0xFFF1F1F1) // Soft background for input/bubbles
)

val LocalThemeMode = compositionLocalOf { mutableStateOf(true) } // true = dark

@Composable
fun KiriTheme(
    darkTheme: Boolean = LocalThemeMode.current.value,
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
