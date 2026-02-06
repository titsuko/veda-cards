package com.example.vedaapplication.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = ButtonColor,
    onPrimary = Color.White,
    secondary = ButtonClearColorDark,
    onSecondary = PrimaryTextDark,
    background = BackgroundColorDark,
    onBackground = PrimaryTextDark,
    surface = SurfaceDark,
    onSurface = PrimaryTextDark,
    surfaceVariant = ButtonClearColorDark,
    onSurfaceVariant = PrimaryTextDark
)

private val LightColorScheme = lightColorScheme(
    primary = ButtonColor,
    onPrimary = Color.White,
    secondary = ButtonClearColor,
    onSecondary = PrimaryText,
    background = BackgroundColor,
    onBackground = PrimaryText,
    surface = BackgroundColor,
    onSurface = PrimaryText,
    surfaceVariant = SnowWhite,
    onSurfaceVariant = PrimaryText
)

@Composable
fun VedaApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    val view = LocalView.current

    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.background.toArgb()
            window.navigationBarColor = colorScheme.background.toArgb()

            WindowCompat.getInsetsController(window, view).apply {
                isAppearanceLightStatusBars = !darkTheme
                isAppearanceLightNavigationBars = !darkTheme
            }
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}