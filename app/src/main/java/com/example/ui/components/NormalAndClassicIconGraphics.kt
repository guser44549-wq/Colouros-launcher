package com.example.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.Text

/**
 * Normal (Stock Android / Material You) Icon Graphics
 * Clean, standard Google Android / AOSP aesthetic:
 * 4-color Chrome, 4-color Google Photos pinwheel, clean Google Phone & Messages,
 * YouTube Music red disc, Google Calendar date tile, etc.
 */
@Composable
fun NormalStockIconGraphic(
    iconKey: String,
    size: Dp,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(size)
            .clip(CircleShape),
        contentAlignment = Alignment.Center
    ) {
        when (iconKey.lowercase()) {
            "phone" -> {
                // Stock Android Phone: Crisp emerald green circular disc with white receiver
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFF34A853)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Phone,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(size * 0.54f)
                    )
                }
            }

            "messages" -> {
                // Stock Google Messages: Royal Blue with rounded white chat pill
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFF1A73E8)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Rounded.ChatBubble,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(size * 0.52f)
                    )
                }
            }

            "browser" -> {
                // Stock Google Chrome: Pure white disc with signature 4-color spinner ring
                Canvas(modifier = Modifier.fillMaxSize().background(Color.White)) {
                    val w = this.size.width
                    val h = this.size.height
                    val center = Offset(w / 2f, h / 2f)
                    val r = w * 0.42f

                    // Red top
                    drawArc(
                        color = Color(0xFFEA4335),
                        startAngle = -150f,
                        sweepAngle = 120f,
                        useCenter = true,
                        topLeft = Offset(center.x - r, center.y - r),
                        size = Size(r * 2, r * 2)
                    )
                    // Yellow right
                    drawArc(
                        color = Color(0xFFFBBC04),
                        startAngle = -30f,
                        sweepAngle = 120f,
                        useCenter = true,
                        topLeft = Offset(center.x - r, center.y - r),
                        size = Size(r * 2, r * 2)
                    )
                    // Green bottom-left
                    drawArc(
                        color = Color(0xFF34A853),
                        startAngle = 90f,
                        sweepAngle = 120f,
                        useCenter = true,
                        topLeft = Offset(center.x - r, center.y - r),
                        size = Size(r * 2, r * 2)
                    )

                    // White separator circle
                    drawCircle(color = Color.White, radius = w * 0.20f, center = center)

                    // Blue center circle
                    drawCircle(color = Color(0xFF4285F4), radius = w * 0.16f, center = center)
                }
            }

            "camera" -> {
                // Stock Android Camera: Dark graphite circle with cyan/blue lens ring
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFF3C4043)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Rounded.PhotoCamera,
                        contentDescription = null,
                        tint = Color(0xFF8AB4F8),
                        modifier = Modifier.size(size * 0.56f)
                    )
                }
            }

            "photos" -> {
                // Google Photos: Pure white circle with signature 4-color pinwheel petals
                Canvas(modifier = Modifier.fillMaxSize().background(Color.White)) {
                    val w = this.size.width
                    val center = Offset(w / 2f, w / 2f)
                    val petalR = w * 0.18f

                    // Red petal (Top)
                    drawCircle(color = Color(0xFFEA4335), radius = petalR, center = Offset(center.x, center.y - petalR * 0.8f))
                    // Yellow petal (Right)
                    drawCircle(color = Color(0xFFFBBC05), radius = petalR, center = Offset(center.x + petalR * 0.8f, center.y))
                    // Green petal (Bottom)
                    drawCircle(color = Color(0xFF34A853), radius = petalR, center = Offset(center.x, center.y + petalR * 0.8f))
                    // Blue petal (Left)
                    drawCircle(color = Color(0xFF4285F4), radius = petalR, center = Offset(center.x - petalR * 0.8f, center.y))
                }
            }

            "settings" -> {
                // Stock Android Settings: Light slate gray circle with dark gear
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFF5F6368)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Settings,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(size * 0.54f)
                    )
                }
            }

            "clock" -> {
                // Stock Android Clock: Pure white circle with fine black hands and blue pivot
                Canvas(modifier = Modifier.fillMaxSize().background(Color.White)) {
                    val w = this.size.width
                    val center = Offset(w / 2f, w / 2f)

                    // Dial rim
                    drawCircle(
                        color = Color(0xFFDADCE0),
                        radius = w * 0.44f,
                        center = center,
                        style = Stroke(width = w * 0.04f)
                    )

                    // Hour hand (pointing to 10)
                    drawLine(
                        color = Color(0xFF202124),
                        start = center,
                        end = Offset(center.x - w * 0.18f, center.y - w * 0.14f),
                        strokeWidth = w * 0.05f,
                        cap = StrokeCap.Round
                    )

                    // Minute hand (pointing to 2)
                    drawLine(
                        color = Color(0xFF202124),
                        start = center,
                        end = Offset(center.x + w * 0.20f, center.y - w * 0.22f),
                        strokeWidth = w * 0.035f,
                        cap = StrokeCap.Round
                    )

                    // Center dot
                    drawCircle(color = Color(0xFF1A73E8), radius = w * 0.05f, center = center)
                }
            }

            "music" -> {
                // YouTube Music Stock: Deep red circle with concentric rings and play triangle
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFFFF0000)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Rounded.PlayCircle,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(size * 0.58f)
                    )
                }
            }

            "calendar" -> {
                // Google Calendar Stock: White circle with royal blue top header and date "31"
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .fillMaxSize(0.72f)
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color(0xFFF1F3F4))
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(size * 0.18f)
                                .background(Color(0xFF1A73E8))
                        )
                        Box(
                            modifier = Modifier.weight(1f),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "31",
                                color = Color(0xFF202124),
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }

            "files" -> {
                // Google Files Stock: Sky blue circle with folder silhouette
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFF1A73E8)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Folder,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(size * 0.56f)
                    )
                }
            }

            "weather" -> {
                // Google Weather Stock: Sky blue with sun & cloud
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFF4285F4)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Rounded.WbSunny,
                        contentDescription = null,
                        tint = Color(0xFFFBBC04),
                        modifier = Modifier.size(size * 0.56f)
                    )
                }
            }

            "notes" -> {
                // Google Keep Notes Stock: Amber yellow circle with note icon
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFFFBBC04)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Lightbulb,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(size * 0.54f)
                    )
                }
            }

            "calculator" -> {
                // Google Calculator Stock: Slate circle with white arithmetic operations
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFF00796B)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Calculate,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(size * 0.56f)
                    )
                }
            }

            "themes" -> {
                // Stock Android "Wallpaper & Style": Violet/indigo circle with brush
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFF673AB7)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Rounded.ColorLens,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(size * 0.54f)
                    )
                }
            }

            else -> {
                // General Stock Android app: Clean tonal Material circle
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFF1A73E8)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Apps,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(size * 0.52f)
                    )
                }
            }
        }
    }
}

/**
 * Classic ColorOS (Heritage Retro Flat) Icon Graphics
 * Clean, bold solid flat retro ColorOS 6/7 aesthetic:
 * Solid Kelly Green phone, retro split-tone camera, orange compass browser,
 * ruby vinyl music, dual interlocking settings gears, and flat drop-shadows.
 */
@Composable
fun ClassicColorOSIconGraphic(
    iconKey: String,
    size: Dp,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(size)
            .clip(RoundedCornerShape(size * 0.22f)),
        contentAlignment = Alignment.Center
    ) {
        when (iconKey.lowercase()) {
            "phone" -> {
                // Classic ColorOS Phone: Solid Kelly Green (#00C853) with white phone
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFF00C853)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Phone,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(size * 0.54f)
                    )
                }
            }

            "messages" -> {
                // Classic ColorOS Messages: Solid Dodger Blue (#00A0E9) with chat bubble
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFF00A0E9)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Message,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(size * 0.52f)
                    )
                }
            }

            "browser" -> {
                // Classic ColorOS Browser: Solid Vivid Orange (#FF6D00) with compass
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFFFF6D00)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Explore,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(size * 0.56f)
                    )
                }
            }

            "camera" -> {
                // Classic ColorOS Camera: Two-tone split (Dark Charcoal top & Metallic Silver bottom) with red dot
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val w = this.size.width
                    val h = this.size.height

                    // Top charcoal half
                    drawRect(
                        color = Color(0xFF263238),
                        topLeft = Offset.Zero,
                        size = Size(w, h * 0.55f)
                    )
                    // Bottom silver half
                    drawRect(
                        color = Color(0xFFB0BEC5),
                        topLeft = Offset(0f, h * 0.55f),
                        size = Size(w, h * 0.45f)
                    )

                    // Center camera lens
                    drawCircle(color = Color(0xFF263238), radius = w * 0.22f, center = Offset(w / 2f, h * 0.52f))
                    drawCircle(color = Color(0xFF37474F), radius = w * 0.16f, center = Offset(w / 2f, h * 0.52f))
                    drawCircle(color = Color(0xFF00E5FF), radius = w * 0.08f, center = Offset(w / 2f, h * 0.52f))

                    // Classic red recording dot
                    drawCircle(color = Color(0xFFD50000), radius = w * 0.04f, center = Offset(w * 0.76f, h * 0.28f))
                }
            }

            "photos" -> {
                // Classic ColorOS Photos: Warm Amber Yellow (#FF9100) with classic mountain & sun
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFFFF9100)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Collections,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(size * 0.56f)
                    )
                }
            }

            "settings" -> {
                // Classic ColorOS Settings: Solid Graphite (#546E7A) with dual interlocking gears
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFF455A64)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Settings,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(size * 0.54f)
                    )
                }
            }

            "clock" -> {
                // Classic ColorOS Clock: Pure flat white square with vintage numbers and red second hand
                Canvas(modifier = Modifier.fillMaxSize().background(Color(0xFFFAFAFA))) {
                    val w = this.size.width
                    val center = Offset(w / 2f, w / 2f)

                    // Minute track circle
                    drawCircle(
                        color = Color(0xFFB0BEC5),
                        radius = w * 0.38f,
                        center = center,
                        style = Stroke(width = w * 0.03f)
                    )

                    // Black hands
                    drawLine(
                        color = Color(0xFF263238),
                        start = center,
                        end = Offset(center.x - w * 0.16f, center.y - w * 0.12f),
                        strokeWidth = w * 0.045f,
                        cap = StrokeCap.Round
                    )
                    drawLine(
                        color = Color(0xFF263238),
                        start = center,
                        end = Offset(center.x + w * 0.18f, center.y - w * 0.20f),
                        strokeWidth = w * 0.03f,
                        cap = StrokeCap.Round
                    )

                    // Red second hand
                    drawLine(
                        color = Color(0xFFD50000),
                        start = center,
                        end = Offset(center.x, center.y + w * 0.24f),
                        strokeWidth = w * 0.018f,
                        cap = StrokeCap.Round
                    )

                    // Center pivot
                    drawCircle(color = Color(0xFFD50000), radius = w * 0.035f, center = center)
                }
            }

            "music" -> {
                // Classic ColorOS Music: Deep Crimson (#D50000) with vinyl record
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFFD50000)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Rounded.MusicNote,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(size * 0.56f)
                    )
                }
            }

            "weather" -> {
                // Classic ColorOS Weather: Solid Sky Cyan (#0288D1) with flat sun
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFF0288D1)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Rounded.WbSunny,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(size * 0.56f)
                    )
                }
            }

            "themes" -> {
                // Classic ColorOS Themes: Royal Purple (#8E24AA) with artist palette
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFF8E24AA)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Palette,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(size * 0.54f)
                    )
                }
            }

            "files" -> {
                // Classic ColorOS Files: Solid Cobalt Blue (#1976D2) with document folder
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFF1976D2)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Folder,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(size * 0.56f)
                    )
                }
            }

            "notes" -> {
                // Classic ColorOS Notes: Warm Golden (#FBC02D) with notebook
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFFFBC02D)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Rounded.EditNote,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(size * 0.58f)
                    )
                }
            }

            "calculator" -> {
                // Classic ColorOS Calculator: Solid Slate (#455A64) with orange accent button
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFF455A64)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Calculate,
                        contentDescription = null,
                        tint = Color(0xFFFFB300),
                        modifier = Modifier.size(size * 0.56f)
                    )
                }
            }

            "calendar" -> {
                // Classic ColorOS Calendar: White square with red banner
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(size * 0.28f)
                            .background(Color(0xFFD50000))
                    )
                    Box(
                        modifier = Modifier.weight(1f),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "28",
                            color = Color(0xFF263238),
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            else -> {
                // Default Classic ColorOS app
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFF00897B)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Apps,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(size * 0.52f)
                    )
                }
            }
        }
    }
}
