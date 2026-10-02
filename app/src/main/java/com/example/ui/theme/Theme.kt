package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

enum class AccentThemeOption(val displayName: String, val primary: Color, val secondary: Color, val accent: Color) {
    OCEAN("Ocean Blue", AquaOceanPrimary, AquaOceanSecondary, AquaOceanAccent),
    EMERALD("Emerald Green", EmeraldPrimary, EmeraldSecondary, EmeraldAccent),
    SUNSET("Sunset Amber", SunsetPrimary, SunsetSecondary, SunsetAccent),
    AMETHYST("Amethyst Glow", AmethystPrimary, AmethystSecondary, AmethystAccent),
    SAKURA("Sakura Rose", SakuraPrimary, SakuraSecondary, SakuraAccent)
}

val ColorOSShapes = Shapes(
    extraSmall = RoundedCornerShape(8.dp),
    small = RoundedCornerShape(14.dp),
    medium = RoundedCornerShape(20.dp),
    large = RoundedCornerShape(28.dp),
    extraLarge = RoundedCornerShape(36.dp)
)

@Composable
fun ColorOSLauncherTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    accentTheme: AccentThemeOption = AccentThemeOption.OCEAN,
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val context = LocalContext.current
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> darkColorScheme(
            primary = accentTheme.primary,
            secondary = accentTheme.secondary,
            tertiary = accentTheme.accent,
            background = DarkBackgroundDeep,
            surface = GlassDarkCard,
            surfaceVariant = GlassDarkBackground,
            onPrimary = Color.Black,
            onSecondary = Color.White,
            onBackground = Color.White,
            onSurface = Color(0xFFF0F4FF)
        )
        else -> lightColorScheme(
            primary = accentTheme.secondary,
            secondary = accentTheme.primary,
            tertiary = accentTheme.accent,
            background = LightBackgroundClean,
            surface = Color.White,
            surfaceVariant = Color(0xFFEFF3F8),
            onPrimary = Color.White,
            onSecondary = Color.Black,
            onBackground = Color(0xFF101828),
            onSurface = Color(0xFF1D2939)
        )
    }

    MaterialTheme(
        colorScheme = colorScheme,
        shapes = ColorOSShapes,
        typography = Typography,
        content = content
    )
}
