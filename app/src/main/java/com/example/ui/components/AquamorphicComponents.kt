package com.example.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.Spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.WallpaperType
import com.example.ui.theme.*

@Composable
fun AquamorphicWallpaperBackground(
    wallpaperType: WallpaperType,
    modifier: Modifier = Modifier,
    blurLevel: Float = 0f,
    dimLevel: Float = 0.15f
) {
    Box(modifier = modifier.fillMaxSize()) {
        when (wallpaperType) {
            WallpaperType.AQUAMORPHIC_WAVE -> {
                val context = LocalContext.current
                val resId = remember(context) {
                    context.resources.getIdentifier(
                        "coloros_aquamorphic_wallpaper_1790852018513",
                        "drawable",
                        context.packageName
                    )
                }
                if (resId != 0) {
                    Image(
                        painter = painterResource(id = resId),
                        contentDescription = "ColorOS 15 Aquamorphic Wallpaper",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                } else {
                    Box(modifier = Modifier.fillMaxSize().background(WallpaperOceanGradient))
                }
            }
            WallpaperType.AURORA -> {
                val context = LocalContext.current
                val resId = remember(context) {
                    context.resources.getIdentifier(
                        "coloros_aurora_wp_1790853780811",
                        "drawable",
                        context.packageName
                    )
                }
                if (resId != 0) {
                    Image(
                        painter = painterResource(id = resId),
                        contentDescription = "ColorOS 15 Aurora Wallpaper",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                } else {
                    Box(modifier = Modifier.fillMaxSize().background(WallpaperAuroraGradient))
                }
            }
            WallpaperType.MIDNIGHT -> {
                val context = LocalContext.current
                val resId = remember(context) {
                    context.resources.getIdentifier(
                        "coloros_midnight_wp_1790853795025",
                        "drawable",
                        context.packageName
                    )
                }
                if (resId != 0) {
                    Image(
                        painter = painterResource(id = resId),
                        contentDescription = "ColorOS 15 Midnight OLED Wallpaper",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                } else {
                    Box(modifier = Modifier.fillMaxSize().background(WallpaperMidnightGradient))
                }
            }
            WallpaperType.SUNSET -> {
                val context = LocalContext.current
                val resId = remember(context) {
                    context.resources.getIdentifier(
                        "coloros_sunset_wp_1790853812088",
                        "drawable",
                        context.packageName
                    )
                }
                if (resId != 0) {
                    Image(
                        painter = painterResource(id = resId),
                        contentDescription = "ColorOS 15 Sunset Wallpaper",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                } else {
                    Box(modifier = Modifier.fillMaxSize().background(WallpaperSunsetGradient))
                }
            }
        }

        // Frosted / dark dim overlay for crisp contrast
        if (dimLevel > 0f) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = dimLevel))
            )
        }
    }
}

@Composable
fun FrostedGlassCard(
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(22.dp),
    backgroundColor: Color = Color(0x381E293B),
    borderColor: Color = Color(0x40FFFFFF),
    borderWidth: Dp = 1.dp,
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = modifier
            .clip(shape)
            .background(backgroundColor)
            .border(borderWidth, borderColor, shape)
    ) {
        content()
    }
}

@Composable
fun ColorOSPageIndicator(
    pageCount: Int,
    currentPage: Int,
    accentColor: Color,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .clip(CircleShape)
            .background(Color(0x33000000))
            .padding(horizontal = 8.dp, vertical = 5.dp),
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        for (i in 0 until pageCount) {
            val isSelected = i == currentPage
            val width by animateFloatAsState(
                targetValue = if (isSelected) 22f else 6f,
                animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = Spring.StiffnessLow),
                label = "page_indicator_width"
            )
            Box(
                modifier = Modifier
                    .height(6.dp)
                    .width(width.dp)
                    .clip(CircleShape)
                    .background(if (isSelected) accentColor else Color.White.copy(alpha = 0.5f))
            )
        }
    }
}

fun Modifier.bouncyClick(
    onClick: () -> Unit,
    onLongClick: (() -> Unit)? = null
): Modifier = pointerInput(Unit) {
    detectTapGestures(
        onTap = { onClick() },
        onLongPress = { onLongClick?.invoke() }
    )
}
