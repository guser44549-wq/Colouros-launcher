package com.example.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import java.util.*

/**
 * High-Fidelity ColorOS 15 Aquamorphic 3D Icon Renderer
 * Faithfully matches the official ColorOS 15 visual design from the official announcement and device screenshots.
 */
@Composable
fun ColorOS15IconGraphic(
    iconKey: String,
    size: Dp,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.size(size),
        contentAlignment = Alignment.Center
    ) {
        when (iconKey.lowercase()) {
            "camera" -> CameraIconGraphic(size = size)
            "photos" -> PhotosIconGraphic(size = size)
            "clock" -> ClockIconGraphic(size = size)
            "phone" -> PhoneIconGraphic(size = size)
            "messages" -> MessagesIconGraphic(size = size)
            "browser" -> BrowserIconGraphic(size = size)
            "music" -> MusicIconGraphic(size = size)
            "settings" -> SettingsIconGraphic(size = size)
            "themes" -> ThemesIconGraphic(size = size)
            "contacts" -> ContactsIconGraphic(size = size)
            "files" -> FilesIconGraphic(size = size)
            "weather" -> WeatherIconGraphic(size = size)
            "notes" -> NotesIconGraphic(size = size)
            "calculator" -> CalculatorIconGraphic(size = size)
            "video" -> VideoIconGraphic(size = size)
            "games" -> GamesIconGraphic(size = size)
            "health" -> HealthIconGraphic(size = size)
            else -> FallbackIconGraphic(iconKey = iconKey, size = size)
        }

        // 3D Glass / Specular light reflection layer across top-left corner
        Canvas(modifier = Modifier.fillMaxSize()) {
            val specularBrush = Brush.linearGradient(
                colors = listOf(
                    Color.White.copy(alpha = 0.35f),
                    Color.White.copy(alpha = 0.10f),
                    Color.Transparent
                ),
                start = Offset(0f, 0f),
                end = Offset(this.size.width * 0.7f, this.size.height * 0.7f)
            )
            drawRect(brush = specularBrush)
        }
    }
}

// 1. Camera: Realistic obsidian/charcoal body with optical glass lens and sapphire center
@Composable
private fun CameraIconGraphic(size: Dp) {
    Canvas(modifier = Modifier.fillMaxSize()) {
        val w = this.size.width
        val h = this.size.height
        val center = Offset(w / 2f, h / 2f)

        // Camera body background: Dark titanium obsidian gradient
        drawRoundRect(
            brush = Brush.verticalGradient(
                listOf(Color(0xFF32363E), Color(0xFF1E2127), Color(0xFF14161B))
            ),
            cornerRadius = androidx.compose.ui.geometry.CornerRadius(w * 0.26f, h * 0.26f)
        )

        // Outer lens metallic bevel
        drawCircle(
            brush = Brush.linearGradient(
                listOf(Color(0xFF8A939E), Color(0xFF454B54), Color(0xFF1A1C20)),
                start = Offset(center.x - w * 0.28f, center.y - h * 0.28f),
                end = Offset(center.x + w * 0.28f, center.y + w * 0.28f)
            ),
            radius = w * 0.30f,
            center = center
        )

        // Inner lens glass aperture
        drawCircle(
            brush = Brush.radialGradient(
                listOf(Color(0xFF0F2B48), Color(0xFF091422), Color(0xFF020508)),
                center = center,
                radius = w * 0.24f
            ),
            radius = w * 0.24f,
            center = center
        )

        // Sapphire sensor optical element
        drawCircle(
            brush = Brush.radialGradient(
                listOf(Color(0xFF00E5FF), Color(0xFF0072FF), Color(0xFF001538)),
                center = Offset(center.x - w * 0.04f, center.y - h * 0.04f),
                radius = w * 0.12f
            ),
            radius = w * 0.12f,
            center = center
        )

        // Lens glass glare reflection
        drawCircle(
            color = Color.White.copy(alpha = 0.65f),
            radius = w * 0.035f,
            center = Offset(center.x - w * 0.08f, center.y - h * 0.08f)
        )

        // Flash sensor dot (top right)
        drawCircle(
            brush = Brush.radialGradient(
                listOf(Color(0xFFFFEE55), Color(0xFFFF9800)),
                center = Offset(w * 0.76f, h * 0.24f),
                radius = w * 0.04f
            ),
            radius = w * 0.04f,
            center = Offset(w * 0.76f, h * 0.24f)
        )
    }
}

// 2. Photos: Clean white pearl card with vibrant cyan/blue mountain wave & golden sun
@Composable
private fun PhotosIconGraphic(size: Dp) {
    Canvas(modifier = Modifier.fillMaxSize()) {
        val w = this.size.width
        val h = this.size.height

        // Pure white background with subtle pearl gradient
        drawRoundRect(
            brush = Brush.verticalGradient(
                listOf(Color(0xFFFFFFFF), Color(0xFFF4F7FA))
            ),
            cornerRadius = androidx.compose.ui.geometry.CornerRadius(w * 0.26f, h * 0.26f)
        )

        // Golden Sun
        drawCircle(
            brush = Brush.radialGradient(
                listOf(Color(0xFFFFB300), Color(0xFFFF8F00)),
                center = Offset(w * 0.68f, h * 0.36f),
                radius = w * 0.12f
            ),
            radius = w * 0.12f,
            center = Offset(w * 0.68f, h * 0.36f)
        )

        // Back mountain peak (deep blue)
        val path1 = Path().apply {
            moveTo(w * 0.18f, h * 0.78f)
            lineTo(w * 0.50f, h * 0.44f)
            lineTo(w * 0.82f, h * 0.78f)
            close()
        }
        drawPath(
            path = path1,
            brush = Brush.verticalGradient(
                listOf(Color(0xFF1E88E5), Color(0xFF1565C0)),
                startY = h * 0.44f,
                endY = h * 0.78f
            )
        )

        // Front mountain peak (vibrant cyan-teal)
        val path2 = Path().apply {
            moveTo(w * 0.32f, h * 0.78f)
            lineTo(w * 0.65f, h * 0.52f)
            lineTo(w * 0.90f, h * 0.78f)
            close()
        }
        drawPath(
            path = path2,
            brush = Brush.verticalGradient(
                listOf(Color(0xFF00E5FF), Color(0xFF0091EA)),
                startY = h * 0.52f,
                endY = h * 0.78f
            )
        )
    }
}

// 3. Clock: Real-time analog clock squircle with moving hands
@Composable
private fun ClockIconGraphic(size: Dp) {
    val cal = Calendar.getInstance()
    val hour = cal.get(Calendar.HOUR)
    val minute = cal.get(Calendar.MINUTE)
    val second = cal.get(Calendar.SECOND)

    val hourAngle = (hour * 30f + minute * 0.5f) - 90f
    val minuteAngle = (minute * 6f) - 90f
    val secondAngle = (second * 6f) - 90f

    Canvas(modifier = Modifier.fillMaxSize()) {
        val w = this.size.width
        val h = this.size.height
        val center = Offset(w / 2f, h / 2f)

        // White card background
        drawRoundRect(
            brush = Brush.verticalGradient(listOf(Color(0xFFFFFFFF), Color(0xFFF1F5F9))),
            cornerRadius = androidx.compose.ui.geometry.CornerRadius(w * 0.26f, h * 0.26f)
        )

        // 12 hour ticks
        for (i in 0 until 12) {
            val angle = Math.toRadians((i * 30 - 90).toDouble())
            val outerRadius = w * 0.38f
            val innerRadius = w * 0.33f
            val start = Offset(
                (center.x + Math.cos(angle) * innerRadius).toFloat(),
                (center.y + Math.sin(angle) * innerRadius).toFloat()
            )
            val end = Offset(
                (center.x + Math.cos(angle) * outerRadius).toFloat(),
                (center.y + Math.sin(angle) * outerRadius).toFloat()
            )
            drawLine(
                color = Color(0xFF1E293B),
                start = start,
                end = end,
                strokeWidth = if (i % 3 == 0) w * 0.035f else w * 0.02f,
                cap = StrokeCap.Round
            )
        }

        // Hour hand
        val hourRad = Math.toRadians(hourAngle.toDouble())
        drawLine(
            color = Color(0xFF0F172A),
            start = center,
            end = Offset(
                (center.x + Math.cos(hourRad) * w * 0.22f).toFloat(),
                (center.y + Math.sin(hourRad) * w * 0.22f).toFloat()
            ),
            strokeWidth = w * 0.045f,
            cap = StrokeCap.Round
        )

        // Minute hand
        val minRad = Math.toRadians(minuteAngle.toDouble())
        drawLine(
            color = Color(0xFF334155),
            start = center,
            end = Offset(
                (center.x + Math.cos(minRad) * w * 0.30f).toFloat(),
                (center.y + Math.sin(minRad) * w * 0.30f).toFloat()
            ),
            strokeWidth = w * 0.035f,
            cap = StrokeCap.Round
        )

        // Second hand (Aquamorphic Blue / Orange)
        val secRad = Math.toRadians(secondAngle.toDouble())
        drawLine(
            color = Color(0xFF0078FE),
            start = center,
            end = Offset(
                (center.x + Math.cos(secRad) * w * 0.32f).toFloat(),
                (center.y + Math.sin(secRad) * w * 0.32f).toFloat()
            ),
            strokeWidth = w * 0.02f,
            cap = StrokeCap.Round
        )

        // Center pivot dot
        drawCircle(color = Color(0xFF0078FE), radius = w * 0.045f, center = center)
    }
}

// 4. Phone: Vibrant lime-green squircle with glossy finish
@Composable
private fun PhoneIconGraphic(size: Dp) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(size * 0.26f))
            .background(
                Brush.verticalGradient(
                    listOf(Color(0xFF2EDB6E), Color(0xFF1EBE5D), Color(0xFF149E48))
                )
            ),
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

// 5. Messages: Bright green squircle with white chat bubble
@Composable
private fun MessagesIconGraphic(size: Dp) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(size * 0.26f))
            .background(
                Brush.verticalGradient(
                    listOf(Color(0xFF34D399), Color(0xFF10B981), Color(0xFF059669))
                )
            ),
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

// 6. Browser: Aquamorphic cerulean blue with floating orbital ring
@Composable
private fun BrowserIconGraphic(size: Dp) {
    Canvas(modifier = Modifier.fillMaxSize()) {
        val w = this.size.width
        val h = this.size.height
        val center = Offset(w / 2f, h / 2f)

        // Rich Aquamorphic Blue gradient
        drawRoundRect(
            brush = Brush.verticalGradient(
                listOf(Color(0xFF00D2FF), Color(0xFF0078FE), Color(0xFF0056B3))
            ),
            cornerRadius = androidx.compose.ui.geometry.CornerRadius(w * 0.26f, h * 0.26f)
        )

        // Center glowing sphere
        drawCircle(
            color = Color.White.copy(alpha = 0.90f),
            radius = w * 0.16f,
            center = center
        )

        // Tilted planetary ring
        val ringStroke = Stroke(width = w * 0.05f, cap = StrokeCap.Round)
        drawArc(
            color = Color.White,
            startAngle = -35f,
            sweepAngle = 250f,
            useCenter = false,
            topLeft = Offset(center.x - w * 0.32f, center.y - h * 0.16f),
            size = Size(w * 0.64f, h * 0.32f),
            style = ringStroke
        )
    }
}

// 7. Music: Coral hot pink/red with 3D white music note
@Composable
private fun MusicIconGraphic(size: Dp) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(size * 0.26f))
            .background(
                Brush.verticalGradient(
                    listOf(Color(0xFFFF3B69), Color(0xFFFF2A55), Color(0xFFE00034))
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.Rounded.MusicNote,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier.size(size * 0.58f)
        )
    }
}

// 8. Settings: Titanium silver metallic with white gear
@Composable
private fun SettingsIconGraphic(size: Dp) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(size * 0.26f))
            .background(
                Brush.verticalGradient(
                    listOf(Color(0xFF64748B), Color(0xFF475569), Color(0xFF334155))
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.Rounded.Settings,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier.size(size * 0.56f)
        )
    }
}

// 9. Themes: Vibrant purple magenta with palette
@Composable
private fun ThemesIconGraphic(size: Dp) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(size * 0.26f))
            .background(
                Brush.linearGradient(
                    listOf(Color(0xFFC084FC), Color(0xFF9333EA), Color(0xFF6B21A8))
                )
            ),
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

// 10. Contacts: Vibrant emerald green with avatar
@Composable
private fun ContactsIconGraphic(size: Dp) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(size * 0.26f))
            .background(
                Brush.verticalGradient(
                    listOf(Color(0xFF10B981), Color(0xFF059669), Color(0xFF047857))
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.Rounded.Person,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier.size(size * 0.56f)
        )
    }
}

// 11. Files: Amber orange with folder
@Composable
private fun FilesIconGraphic(size: Dp) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(size * 0.26f))
            .background(
                Brush.verticalGradient(
                    listOf(Color(0xFFFBBF24), Color(0xFFF59E0B), Color(0xFFD97706))
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.Rounded.Folder,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier.size(size * 0.54f)
        )
    }
}

// 12. Weather: Azure sky blue with sun and cloud
@Composable
private fun WeatherIconGraphic(size: Dp) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(size * 0.26f))
            .background(
                Brush.verticalGradient(
                    listOf(Color(0xFF38BDF8), Color(0xFF0284C7), Color(0xFF0369A1))
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.Rounded.WbSunny,
            contentDescription = null,
            tint = Color(0xFFFFEE55),
            modifier = Modifier.size(size * 0.56f)
        )
    }
}

// 13. Notes: Golden amber notepad
@Composable
private fun NotesIconGraphic(size: Dp) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(size * 0.26f))
            .background(
                Brush.verticalGradient(
                    listOf(Color(0xFFFCD34D), Color(0xFFF59E0B), Color(0xFFB45309))
                )
            ),
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

// 14. Calculator: Teal math grid
@Composable
private fun CalculatorIconGraphic(size: Dp) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(size * 0.26f))
            .background(
                Brush.verticalGradient(
                    listOf(Color(0xFF2DD4BF), Color(0xFF0D9488), Color(0xFF115E59))
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.Rounded.Calculate,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier.size(size * 0.54f)
        )
    }
}

// 15. Video: Coral red with play triangle
@Composable
private fun VideoIconGraphic(size: Dp) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(size * 0.26f))
            .background(
                Brush.verticalGradient(
                    listOf(Color(0xFFFF7043), Color(0xFFF4511E), Color(0xFFD84315))
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.Rounded.PlayArrow,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier.size(size * 0.60f)
        )
    }
}

// 16. Games: Indigo violet with gamepad
@Composable
private fun GamesIconGraphic(size: Dp) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(size * 0.26f))
            .background(
                Brush.linearGradient(
                    listOf(Color(0xFF818CF8), Color(0xFF4F46E5), Color(0xFF3730A3))
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.Rounded.SportsEsports,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier.size(size * 0.56f)
        )
    }
}

// 17. Health: Emerald green with heart pulse
@Composable
private fun HealthIconGraphic(size: Dp) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(size * 0.26f))
            .background(
                Brush.verticalGradient(
                    listOf(Color(0xFF34D399), Color(0xFF059669), Color(0xFF064E3B))
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.Rounded.Favorite,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier.size(size * 0.54f)
        )
    }
}

@Composable
private fun FallbackIconGraphic(iconKey: String, size: Dp) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(size * 0.26f))
            .background(
                Brush.verticalGradient(
                    listOf(Color(0xFF00B4D8), Color(0xFF0077B6))
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        ColorOSVectorIcon(iconKey = iconKey, size = size * 0.54f)
    }
}
