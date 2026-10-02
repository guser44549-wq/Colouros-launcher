package com.example.ui.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

// ColorOS 15 Aquamorphic Signature Colors
val AquaOceanPrimary = Color(0xFF00B4D8)
val AquaOceanSecondary = Color(0xFF0077B6)
val AquaOceanAccent = Color(0xFF90E0EF)

val EmeraldPrimary = Color(0xFF00C9A7)
val EmeraldSecondary = Color(0xFF058E73)
val EmeraldAccent = Color(0xFF80E27E)

val SunsetPrimary = Color(0xFFFF8C00)
val SunsetSecondary = Color(0xFFFF5E62)
val SunsetAccent = Color(0xFFFFD166)

val AmethystPrimary = Color(0xFF9B51E0)
val AmethystSecondary = Color(0xFF7000FF)
val AmethystAccent = Color(0xFFC77DFF)

val SakuraPrimary = Color(0xFFFF6B8B)
val SakuraSecondary = Color(0xFFFF8E53)
val SakuraAccent = Color(0xFFFFCCD5)

// Glassmorphism surfaces
val GlassLightBackground = Color(0x33FFFFFF)
val GlassLightCard = Color(0x55FFFFFF)
val GlassLightBorder = Color(0x66FFFFFF)

val GlassDarkBackground = Color(0x40121620)
val GlassDarkCard = Color(0x661A2234)
val GlassDarkBorder = Color(0x3380B0FF)

val DarkBackgroundDeep = Color(0xFF090D16)
val LightBackgroundClean = Color(0xFFF4F7FC)

// Wallpaper Gradients
val WallpaperOceanGradient = Brush.verticalGradient(
    colors = listOf(
        Color(0xFF031026),
        Color(0xFF09234A),
        Color(0xFF0B3A68),
        Color(0xFF071933)
    )
)

val WallpaperAuroraGradient = Brush.linearGradient(
    colors = listOf(
        Color(0xFF0A192F),
        Color(0xFF0D3B4C),
        Color(0xFF134E5E),
        Color(0xFF2C2554)
    )
)

val WallpaperSunsetGradient = Brush.verticalGradient(
    colors = listOf(
        Color(0xFF2E0854),
        Color(0xFF6B117A),
        Color(0xFF99224E),
        Color(0xFFDD6B20)
    )
)

val WallpaperMidnightGradient = Brush.radialGradient(
    colors = listOf(
        Color(0xFF161F36),
        Color(0xFF0E1322),
        Color(0xFF05070D)
    )
)
