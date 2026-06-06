package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme =
  darkColorScheme(
    primary = MotionPrimary,
    secondary = MotionPrimaryDark,
    background = MotionDarkBg,
    surface = MotionDarkSurface,
    onPrimary = MotionDarkBg,
    onSecondary = MotionTextPrimary,
    onBackground = MotionTextPrimary,
    onSurface = MotionTextPrimary,
    error = MotionUrgent,
    surfaceVariant = MotionBorder,
    onSurfaceVariant = MotionTextSecondary
  )

private val LightColorScheme =
  lightColorScheme(
    primary = MotionPrimary,
    secondary = MotionPrimaryDark,
    background = MotionTextPrimary,
    surface = androidx.compose.ui.graphics.Color.White,
    onPrimary = androidx.compose.ui.graphics.Color.White,
    onSecondary = MotionDarkBg,
    onBackground = MotionDarkBg,
    onSurface = MotionDarkBg,
    error = MotionUrgent,
    surfaceVariant = MotionTextSecondary,
    onSurfaceVariant = MotionDarkSurface
  )

@Composable
fun MyApplicationTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  // Forcing our custom theme over dynamic for consistent branding
  dynamicColor: Boolean = false,
  content: @Composable () -> Unit,
) {
  val colorScheme =
    when {
      dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
        val context = LocalContext.current
        if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
      }

      darkTheme -> DarkColorScheme
      else -> LightColorScheme // Defaulting to light or force dark? Let's respect system but provide our colors.
    }

  MaterialTheme(colorScheme = colorScheme, typography = Typography, content = content)
}
