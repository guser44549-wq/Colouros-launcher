package com.example.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.Spring
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.model.AppModel
import com.example.model.IconBadgeStyle
import com.example.model.IconDesignStyle
import com.example.model.IconShape

@Composable
fun AppIconView(
    app: AppModel,
    iconShape: IconShape,
    iconScale: Float,
    showLabel: Boolean,
    modifier: Modifier = Modifier,
    designStyle: IconDesignStyle = IconDesignStyle.COLOROS_15,
    badgeStyle: IconBadgeStyle = IconBadgeStyle.NUMBER,
    onClick: () -> Unit = {},
    onLongClick: (() -> Unit)? = null
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val animatedScale by animateFloatAsState(
        targetValue = if (isPressed) 0.90f else 1.0f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = Spring.StiffnessMedium),
        label = "icon_press_scale"
    )

    val shape = when (iconShape) {
        IconShape.SQUIRCLE -> RoundedCornerShape(22.dp)
        IconShape.ROUNDED_SQUARE -> RoundedCornerShape(14.dp)
        IconShape.CIRCLE -> CircleShape
        IconShape.PEBBLE -> RoundedCornerShape(26.dp)
    }

    val baseIconSize = (54 * iconScale).dp

    // Design Style Background Colors & Gradients
    val backgroundBrush = when (designStyle) {
        IconDesignStyle.NORMAL -> Brush.verticalGradient(
            colors = listOf(app.primaryColor, app.primaryColor.copy(alpha = 0.85f))
        )
        IconDesignStyle.COLOROS_15 -> Brush.linearGradient(
            colors = listOf(
                app.primaryColor.copy(alpha = 0.95f),
                app.primaryColor,
                app.primaryColor.copy(alpha = 0.85f)
            )
        )
        IconDesignStyle.COLOROS_16 -> Brush.verticalGradient(
            colors = listOf(
                Color(0xFF0F172A),
                Color(0xFF1E1B4B),
                Color(0xFF0A0F1D)
            )
        )
        IconDesignStyle.CLASSIC -> {
            val classicColor = getClassicColor(app.iconKey, app.primaryColor)
            Brush.verticalGradient(colors = listOf(classicColor, classicColor))
        }
        IconDesignStyle.GLASSY -> Brush.linearGradient(
            colors = listOf(
                Color(0x60FFFFFF),
                Color(0x18FFFFFF),
                Color(0x35FFFFFF)
            )
        )
        IconDesignStyle.PURE_BLACK -> Brush.verticalGradient(
            colors = listOf(Color(0xFF000000), Color(0xFF000000))
        )
        IconDesignStyle.PURE_WHITE -> Brush.verticalGradient(
            colors = listOf(Color(0xFFFFFFFF), Color(0xFFF3F4F6))
        )
    }

    val borderWidth = when (designStyle) {
        IconDesignStyle.GLASSY -> 1.5.dp
        IconDesignStyle.COLOROS_16 -> 1.4.dp
        IconDesignStyle.PURE_BLACK -> 1.dp
        IconDesignStyle.PURE_WHITE -> 1.dp
        IconDesignStyle.CLASSIC -> 0.8.dp
        else -> 0.5.dp
    }

    val borderBrush: Brush = when (designStyle) {
        IconDesignStyle.GLASSY -> Brush.linearGradient(
            listOf(Color(0xE6FFFFFF), Color(0x33FFFFFF), Color(0x80FFFFFF))
        )
        IconDesignStyle.COLOROS_16 -> Brush.sweepGradient(
            listOf(Color(0xFF00F0FF), Color(0xFF7000FF), Color(0xFFFF007F), Color(0xFF00F0FF))
        )
        IconDesignStyle.PURE_BLACK -> Brush.verticalGradient(
            listOf(Color(0x40FFFFFF), Color(0x1AFFFFFF))
        )
        IconDesignStyle.PURE_WHITE -> Brush.verticalGradient(
            listOf(Color(0x1F000000), Color(0x0F000000))
        )
        IconDesignStyle.CLASSIC -> Brush.verticalGradient(
            listOf(Color(0x22000000), Color(0x22000000))
        )
        else -> Brush.verticalGradient(
            listOf(Color(0x4DFFFFFF), Color(0x1AFFFFFF))
        )
    }

    val glyphTint = when (designStyle) {
        IconDesignStyle.PURE_BLACK -> Color.White
        IconDesignStyle.PURE_WHITE -> Color(0xFF0F172A)
        IconDesignStyle.GLASSY -> app.primaryColor
        IconDesignStyle.COLOROS_16 -> Color(0xFF00F0FF)
        else -> Color.White
    }

    val badgeContent: String? = remember(app.iconKey, app.label) {
        when (app.iconKey.lowercase()) {
            "messages" -> "4"
            "phone" -> "2"
            "themes" -> "NEW"
            "health" -> "✓"
            "notes" -> "1"
            else -> if (app.label.contains("mail", ignoreCase = true)) "7" else null
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .testTag("app_icon_${app.packageName}")
            .scale(animatedScale)
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = { onClick() },
                    onLongPress = { onLongClick?.invoke() }
                )
            }
            .padding(vertical = 4.dp)
    ) {
        Box(contentAlignment = Alignment.TopEnd) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(baseIconSize)
                    .shadow(
                        elevation = when (designStyle) {
                            IconDesignStyle.GLASSY -> 8.dp
                            IconDesignStyle.COLOROS_16 -> 8.dp
                            IconDesignStyle.PURE_WHITE -> 4.dp
                            IconDesignStyle.PURE_BLACK -> 2.dp
                            else -> 6.dp
                        },
                        shape = shape,
                        spotColor = when (designStyle) {
                            IconDesignStyle.PURE_BLACK -> Color.Black
                            IconDesignStyle.COLOROS_16 -> Color(0xFF00F0FF).copy(alpha = 0.6f)
                            IconDesignStyle.PURE_WHITE -> Color(0x40000000)
                            else -> app.primaryColor.copy(alpha = 0.5f)
                        }
                    )
                    .clip(shape)
                    .background(backgroundBrush)
                    .border(
                        if (badgeStyle == IconBadgeStyle.GLOW && badgeContent != null) 2.dp else borderWidth,
                        if (badgeStyle == IconBadgeStyle.GLOW && badgeContent != null) Brush.sweepGradient(listOf(Color(0xFF00F0FF), Color(0xFF7000FF), Color(0xFF00F0FF))) else borderBrush,
                        shape
                    )
            ) {
            when (designStyle) {
                IconDesignStyle.COLOROS_15 -> {
                    if (app.iconKey.isNotBlank()) {
                        ColorOS15IconGraphic(
                            iconKey = app.iconKey,
                            size = baseIconSize
                        )
                    } else if (app.iconDrawable != null && app.isRealApp) {
                        Image(
                            painter = rememberAsyncImagePainter(model = app.iconDrawable),
                            contentDescription = app.label,
                            modifier = Modifier.size(baseIconSize * 0.72f)
                        )
                    } else {
                        ColorOSVectorIcon(
                            iconKey = app.iconKey,
                            size = baseIconSize * 0.58f,
                            tint = Color.White
                        )
                    }
                }

                IconDesignStyle.COLOROS_16 -> {
                    // Futuristic Neon Fluid Icon
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        if (app.iconDrawable != null && app.isRealApp && app.iconKey.isBlank()) {
                            Image(
                                painter = rememberAsyncImagePainter(model = app.iconDrawable),
                                contentDescription = app.label,
                                modifier = Modifier.size(baseIconSize * 0.70f)
                            )
                        } else {
                            ColorOSVectorIcon(
                                iconKey = app.iconKey,
                                size = baseIconSize * 0.56f,
                                tint = Color(0xFF00F0FF)
                            )
                        }

                        // Diagonal holographic reflection
                        Canvas(modifier = Modifier.fillMaxSize()) {
                            val w = size.width
                            val h = size.height
                            drawArc(
                                brush = Brush.linearGradient(
                                    listOf(Color(0x6600F0FF), Color(0x007000FF)),
                                    start = Offset(0f, 0f),
                                    end = Offset(w, h)
                                ),
                                startAngle = -45f,
                                sweepAngle = 90f,
                                useCenter = false,
                                topLeft = Offset(-w * 0.2f, -h * 0.2f),
                                size = Size(w * 1.4f, h * 0.8f)
                            )
                        }
                    }
                }

                IconDesignStyle.GLASSY -> {
                    // Frosted Glassmorphism with 45° specular glass shine
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        if (app.iconDrawable != null && app.isRealApp && app.iconKey.isBlank()) {
                            Image(
                                painter = rememberAsyncImagePainter(model = app.iconDrawable),
                                contentDescription = app.label,
                                modifier = Modifier.size(baseIconSize * 0.68f)
                            )
                        } else {
                            ColorOSVectorIcon(
                                iconKey = app.iconKey,
                                size = baseIconSize * 0.58f,
                                tint = app.primaryColor
                            )
                        }

                        // Specular glass reflection beam across top-right to center
                        Canvas(modifier = Modifier.fillMaxSize()) {
                            val w = size.width
                            val h = size.height
                            val specular = Brush.linearGradient(
                                colors = listOf(
                                    Color.White.copy(alpha = 0.50f),
                                    Color.White.copy(alpha = 0.15f),
                                    Color.Transparent
                                ),
                                start = Offset(0f, 0f),
                                end = Offset(w * 0.75f, h * 0.75f)
                            )
                            drawRect(brush = specular)
                        }
                    }
                }

                IconDesignStyle.PURE_BLACK -> {
                    // Pure OLED Stealth Black with laser-sharp white glyph
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        if (app.iconDrawable != null && app.isRealApp && app.iconKey.isBlank()) {
                            Image(
                                painter = rememberAsyncImagePainter(model = app.iconDrawable),
                                contentDescription = app.label,
                                modifier = Modifier.size(baseIconSize * 0.70f)
                            )
                        } else {
                            ColorOSVectorIcon(
                                iconKey = app.iconKey,
                                size = baseIconSize * 0.58f,
                                tint = Color.White
                            )
                        }

                        // Piano-black specular top edge
                        Canvas(modifier = Modifier.fillMaxSize()) {
                            val w = size.width
                            drawLine(
                                color = Color.White.copy(alpha = 0.25f),
                                start = Offset(w * 0.15f, 1f),
                                end = Offset(w * 0.85f, 1f),
                                strokeWidth = 1.5f
                            )
                        }
                    }
                }

                IconDesignStyle.PURE_WHITE -> {
                    // Porcelain Ceramic White with deep obsidian glyph
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        if (app.iconDrawable != null && app.isRealApp && app.iconKey.isBlank()) {
                            Image(
                                painter = rememberAsyncImagePainter(model = app.iconDrawable),
                                contentDescription = app.label,
                                modifier = Modifier.size(baseIconSize * 0.70f)
                            )
                        } else {
                            ColorOSVectorIcon(
                                iconKey = app.iconKey,
                                size = baseIconSize * 0.58f,
                                tint = Color(0xFF0F172A)
                            )
                        }

                        // Ceramic soft reflection
                        Canvas(modifier = Modifier.fillMaxSize()) {
                            val w = size.width
                            drawLine(
                                color = Color.White.copy(alpha = 0.8f),
                                start = Offset(w * 0.10f, 1f),
                                end = Offset(w * 0.90f, 1f),
                                strokeWidth = 1.5f
                            )
                        }
                    }
                }

                IconDesignStyle.CLASSIC -> {
                    // Authentic Classic ColorOS Heritage Retro Flat Style
                    if (app.iconKey.isNotBlank()) {
                        ClassicColorOSIconGraphic(
                            iconKey = app.iconKey,
                            size = baseIconSize
                        )
                    } else if (app.iconDrawable != null && app.isRealApp) {
                        Image(
                            painter = rememberAsyncImagePainter(model = app.iconDrawable),
                            contentDescription = app.label,
                            modifier = Modifier.size(baseIconSize * 0.72f)
                        )
                    } else {
                        ClassicColorOSIconGraphic(
                            iconKey = "app",
                            size = baseIconSize
                        )
                    }
                }

                IconDesignStyle.NORMAL -> {
                    // Authentic Normal Stock Android / Material You Style
                    if (app.iconKey.isNotBlank()) {
                        NormalStockIconGraphic(
                            iconKey = app.iconKey,
                            size = baseIconSize
                        )
                    } else if (app.iconDrawable != null && app.isRealApp) {
                        Image(
                            painter = rememberAsyncImagePainter(model = app.iconDrawable),
                            contentDescription = app.label,
                            modifier = Modifier.size(baseIconSize * 0.72f)
                        )
                    } else {
                        NormalStockIconGraphic(
                            iconKey = "app",
                            size = baseIconSize
                        )
                    }
                }
            }

            // Notification Add-on Badge
            if (badgeContent != null) {
                when (badgeStyle) {
                    IconBadgeStyle.NUMBER -> {
                        Box(
                            modifier = Modifier
                                .offset(x = 5.dp, y = (-4).dp)
                                .clip(RoundedCornerShape(9.dp))
                                .background(
                                    if (badgeContent == "NEW") Color(0xFF8B5CF6)
                                    else if (badgeContent == "✓") Color(0xFF10B981)
                                    else Color(0xFFEF4444)
                                )
                                .border(1.2.dp, Color.White, RoundedCornerShape(9.dp))
                                .padding(horizontal = if (badgeContent.length > 1) 5.dp else 4.dp, vertical = 1.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = badgeContent,
                                color = Color.White,
                                fontSize = if (badgeContent.length > 2) 8.sp else 9.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                    IconBadgeStyle.DOT -> {
                        Box(
                            modifier = Modifier
                                .offset(x = 2.dp, y = (-2).dp)
                                .size(9.dp)
                                .clip(CircleShape)
                                .background(Color(0xFFEF4444))
                                .border(1.2.dp, Color.White, CircleShape)
                        )
                    }
                    IconBadgeStyle.GLOW, IconBadgeStyle.NONE -> {}
                }
            }
        }

        if (showLabel) {
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = app.label,
                style = TextStyle(
                    color = Color.White,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    shadow = Shadow(
                        color = Color.Black.copy(alpha = 0.85f),
                        offset = Offset(0f, 2f),
                        blurRadius = 4f
                    )
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center,
                modifier = Modifier.widthIn(max = (baseIconSize + 20.dp))
            )
        }
    }
}

private fun getClassicColor(iconKey: String, fallback: Color): Color = when (iconKey.lowercase()) {
    "phone" -> Color(0xFF00C853)
    "messages" -> Color(0xFF00B0FF)
    "browser" -> Color(0xFFFF6D00)
    "camera" -> Color(0xFF263238)
    "photos" -> Color(0xFFFF9100)
    "settings" -> Color(0xFF546E7A)
    "music" -> Color(0xFFD50000)
    "themes" -> Color(0xFF8E24AA)
    "weather" -> Color(0xFF0288D1)
    "notes" -> Color(0xFFFBC02D)
    "calculator" -> Color(0xFF455A64)
    "calendar" -> Color(0xFFE53935)
    "files" -> Color(0xFF1976D2)
    "recorder" -> Color(0xFFC2185B)
    "compass" -> Color(0xFF00897B)
    "games" -> Color(0xFF5E35B1)
    "health" -> Color(0xFF43A047)
    else -> fallback
}

@Composable
fun ColorOSVectorIcon(
    iconKey: String,
    size: Dp,
    modifier: Modifier = Modifier,
    tint: Color = Color.White
) {
    val iconVector: ImageVector = when (iconKey.lowercase()) {
        "phone" -> Icons.Rounded.Phone
        "messages" -> Icons.Rounded.Message
        "browser" -> Icons.Rounded.Language
        "camera" -> Icons.Rounded.PhotoCamera
        "photos" -> Icons.Rounded.Collections
        "settings" -> Icons.Rounded.Settings
        "weather" -> Icons.Rounded.WbSunny
        "clock" -> Icons.Rounded.AccessTime
        "music" -> Icons.Rounded.MusicNote
        "notes" -> Icons.Rounded.EditNote
        "calculator" -> Icons.Rounded.Calculate
        "calendar" -> Icons.Rounded.CalendarMonth
        "files" -> Icons.Rounded.Folder
        "recorder" -> Icons.Rounded.Mic
        "compass" -> Icons.Rounded.Explore
        "games" -> Icons.Rounded.SportsEsports
        "health" -> Icons.Rounded.Favorite
        "themes" -> Icons.Rounded.Palette
        else -> Icons.Rounded.Apps
    }

    Icon(
        imageVector = iconVector,
        contentDescription = null,
        tint = tint,
        modifier = modifier.size(size)
    )
}
