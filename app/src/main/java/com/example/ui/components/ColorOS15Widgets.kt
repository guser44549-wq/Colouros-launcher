package com.example.ui.components

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.*

/**
 * Authentic ColorOS 15 Widgets matching the official announcement and screenshots:
 * 1. Step Counter Capsule (18,031 Steps)
 * 2. Recorder Capsule (00:27)
 * 3. Sapphire Weather Card (27° Mostly Clear)
 * 4. Battery Availability Chart (53% Estimated availability 12 hours)
 * 5. Analog Clock Squircle Card
 * 6. Storage & RAM Cleaner (75% Clean Up)
 * 7. ColorOS 15 Media Player Card
 */

@Composable
fun ColorOS15StepsCapsule(
    steps: Int = 18031,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    FrostedGlassCard(
        shape = RoundedCornerShape(22.dp),
        backgroundColor = Color(0xD9F1F5F9),
        borderColor = Color(0x66FFFFFF),
        borderWidth = 1.dp,
        modifier = modifier
            .testTag("steps_capsule")
            .clip(RoundedCornerShape(22.dp))
            .clickable { onClick() }
            .padding(2.dp)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(26.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF10B981)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Rounded.DirectionsRun,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(16.dp)
                )
            }
            Column {
                Text(
                    text = String.format("%,d", steps),
                    color = Color(0xFF0F172A),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Steps",
                    color = Color(0xFF64748B),
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

@Composable
fun ColorOS15RecorderCapsule(
    isRecording: Boolean = false,
    durationText: String = "00:27",
    onToggle: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    FrostedGlassCard(
        shape = RoundedCornerShape(22.dp),
        backgroundColor = Color(0xD9F8FAFC),
        borderColor = Color(0x66FFFFFF),
        borderWidth = 1.dp,
        modifier = modifier
            .testTag("recorder_capsule")
            .clip(RoundedCornerShape(22.dp))
            .clickable { onToggle() }
            .padding(2.dp)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = durationText,
                color = Color(0xFF0F172A),
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold
            )
            Box(
                modifier = Modifier
                    .size(22.dp)
                    .clip(CircleShape)
                    .background(if (isRecording) Color(0xFFEF4444) else Color(0xFFF97316)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = if (isRecording) Icons.Rounded.Pause else Icons.Rounded.Mic,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(13.dp)
                )
            }
        }
    }
}

@Composable
fun ColorOS15WeatherCard(
    modifier: Modifier = Modifier,
    temp: String = "27°",
    condition: String = "Mostly clear",
    location: String = "NanShan District",
    highLow: String = "23° / 30°",
    onClick: () -> Unit = {}
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(26.dp))
            .background(
                Brush.verticalGradient(
                    listOf(Color(0xFF0078FE), Color(0xFF0052B4), Color(0xFF02367B))
                )
            )
            .border(1.dp, Color(0x40FFFFFF), RoundedCornerShape(26.dp))
            .clickable { onClick() }
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column {
                    Text(
                        text = location,
                        color = Color.White.copy(alpha = 0.85f),
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        text = temp,
                        color = Color.White,
                        fontSize = 34.sp,
                        fontWeight = FontWeight.Light
                    )
                }

                // Weather icon
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(Color(0x33FFFFFF)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Rounded.NightsStay,
                        contentDescription = null,
                        tint = Color(0xFFFFD54F),
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            Column {
                Text(
                    text = condition,
                    color = Color.White,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = highLow,
                    color = Color.White.copy(alpha = 0.7f),
                    fontSize = 10.sp
                )
            }
        }
    }
}

@Composable
fun ColorOS15BatteryChartCard(
    batteryPercent: Int = 53,
    hoursRemaining: Int = 12,
    modifier: Modifier = Modifier
) {
    FrostedGlassCard(
        shape = RoundedCornerShape(24.dp),
        backgroundColor = Color(0xE6FFFFFF),
        borderColor = Color(0x66FFFFFF),
        borderWidth = 1.dp,
        modifier = modifier.padding(2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(14.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "$batteryPercent%",
                        color = Color(0xFF0F172A),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(Color(0xFFE2E8F0))
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = "Good",
                            color = Color(0xFF10B981),
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Text(
                    text = "Estimated $hoursRemaining hrs",
                    color = Color(0xFF64748B),
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            // Green availability wave graph
            Canvas(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(34.dp)
            ) {
                val w = size.width
                val h = size.height

                val path = Path().apply {
                    moveTo(0f, h * 0.7f)
                    cubicTo(w * 0.25f, h * 0.2f, w * 0.45f, h * 0.8f, w * 0.70f, h * 0.3f)
                    quadraticTo(w * 0.85f, h * 0.1f, w, h * 0.4f)
                }

                drawPath(
                    path = path,
                    color = Color(0xFF10B981),
                    style = Stroke(width = 3.dp.toPx(), cap = StrokeCap.Round)
                )

                // Fill gradient underneath
                val fillPath = Path().apply {
                    addPath(path)
                    lineTo(w, h)
                    lineTo(0f, h)
                    close()
                }

                drawPath(
                    path = fillPath,
                    brush = Brush.verticalGradient(
                        listOf(Color(0x3310B981), Color.Transparent)
                    )
                )
            }
        }
    }
}

@Composable
fun ColorOS15AnalogClockCard(
    modifier: Modifier = Modifier,
    temp: String = "26°"
) {
    val cal = Calendar.getInstance()
    val hour = cal.get(Calendar.HOUR)
    val minute = cal.get(Calendar.MINUTE)
    val second = cal.get(Calendar.SECOND)

    val hourAngle = (hour * 30f + minute * 0.5f) - 90f
    val minuteAngle = (minute * 6f) - 90f
    val secondAngle = (second * 6f) - 90f

    FrostedGlassCard(
        shape = RoundedCornerShape(26.dp),
        backgroundColor = Color(0xE6FFFFFF),
        borderColor = Color(0x66FFFFFF),
        borderWidth = 1.dp,
        modifier = modifier.padding(2.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            contentAlignment = Alignment.Center
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                val w = size.width
                val h = size.height
                val center = Offset(w / 2f, h / 2f)

                // Hour marks
                for (i in 0 until 12) {
                    val angle = Math.toRadians((i * 30 - 90).toDouble())
                    val outer = w * 0.44f
                    val inner = w * 0.38f
                    val start = Offset(
                        (center.x + Math.cos(angle) * inner).toFloat(),
                        (center.y + Math.sin(angle) * inner).toFloat()
                    )
                    val end = Offset(
                        (center.x + Math.cos(angle) * outer).toFloat(),
                        (center.y + Math.sin(angle) * outer).toFloat()
                    )
                    drawLine(
                        color = Color(0xFF64748B),
                        start = start,
                        end = end,
                        strokeWidth = if (i % 3 == 0) 2.5.dp.toPx() else 1.5.dp.toPx(),
                        cap = StrokeCap.Round
                    )
                }

                // Hour hand
                val hourRad = Math.toRadians(hourAngle.toDouble())
                drawLine(
                    color = Color(0xFF0F172A),
                    start = center,
                    end = Offset(
                        (center.x + Math.cos(hourRad) * w * 0.24f).toFloat(),
                        (center.y + Math.sin(hourRad) * w * 0.24f).toFloat()
                    ),
                    strokeWidth = 3.5.dp.toPx(),
                    cap = StrokeCap.Round
                )

                // Minute hand
                val minRad = Math.toRadians(minuteAngle.toDouble())
                drawLine(
                    color = Color(0xFF334155),
                    start = center,
                    end = Offset(
                        (center.x + Math.cos(minRad) * w * 0.34f).toFloat(),
                        (center.y + Math.sin(minRad) * w * 0.34f).toFloat()
                    ),
                    strokeWidth = 2.5.dp.toPx(),
                    cap = StrokeCap.Round
                )

                // Second hand
                val secRad = Math.toRadians(secondAngle.toDouble())
                drawLine(
                    color = Color(0xFF0078FE),
                    start = center,
                    end = Offset(
                        (center.x + Math.cos(secRad) * w * 0.38f).toFloat(),
                        (center.y + Math.sin(secRad) * w * 0.38f).toFloat()
                    ),
                    strokeWidth = 1.5.dp.toPx(),
                    cap = StrokeCap.Round
                )

                // Center pivot
                drawCircle(color = Color(0xFF0078FE), radius = 3.dp.toPx(), center = center)
            }

            // Temp label in corner
            Text(
                text = temp,
                color = Color(0xFF64748B),
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 2.dp)
            )
        }
    }
}

@Composable
fun ColorOS15StorageCleanerCard(
    usagePercent: Int = 75,
    onCleanUp: () -> Unit = {},
    isCleaning: Boolean = false,
    modifier: Modifier = Modifier
) {
    FrostedGlassCard(
        shape = RoundedCornerShape(26.dp),
        backgroundColor = Color(0xE6FFFFFF),
        borderColor = Color(0x66FFFFFF),
        borderWidth = 1.dp,
        modifier = modifier.padding(2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(14.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "$usagePercent%",
                        color = Color(0xFF0F172A),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "217GB / 256GB",
                        color = Color(0xFF64748B),
                        fontSize = 10.sp
                    )
                }

                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFE2E8F0)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Storage,
                        contentDescription = null,
                        tint = Color(0xFF0078FE),
                        modifier = Modifier.size(18.dp)
                    )
                }
            }

            // Clean Up Button
            Button(
                onClick = onCleanUp,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFF1F5F9),
                    contentColor = Color(0xFF0F172A)
                ),
                shape = RoundedCornerShape(12.dp),
                contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(30.dp)
            ) {
                if (isCleaning) {
                    CircularProgressIndicator(
                        color = Color(0xFF0078FE),
                        strokeWidth = 2.dp,
                        modifier = Modifier.size(14.dp)
                    )
                } else {
                    Icon(
                        imageVector = Icons.Rounded.Check,
                        contentDescription = null,
                        tint = Color(0xFF10B981),
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = "Clean Up", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

/**
 * Top "Fluid Cloud" / Dynamic Capsule Status Banner (ColorOS 15 signature feature)
 * Seen in Screenshots 2 and 3!
 */
@Composable
fun ColorOS15FluidCloudPill(
    isPlaying: Boolean,
    trackTitle: String,
    onTogglePlay: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .testTag("coloros15_fluid_cloud")
            .clip(RoundedCornerShape(20.dp))
            .background(Color(0xCC000000))
            .border(0.75.dp, Color(0x33FFFFFF), RoundedCornerShape(20.dp))
            .clickable { onTogglePlay() }
            .padding(horizontal = 14.dp, vertical = 6.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Mini album disc
            Box(
                modifier = Modifier
                    .size(16.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFFF3B69)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Rounded.MusicNote,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(10.dp)
                )
            }

            Text(
                text = trackTitle,
                color = Color.White,
                fontSize = 11.sp,
                fontWeight = FontWeight.Medium,
                maxLines = 1
            )

            // Mini equalizer animation
            Row(
                horizontalArrangement = Arrangement.spacedBy(2.dp),
                verticalAlignment = Alignment.Bottom,
                modifier = Modifier.height(10.dp)
            ) {
                for (i in 0 until 3) {
                    val barHeight by rememberInfiniteTransition(label = "fluid_eq_$i").animateFloat(
                        initialValue = if (isPlaying) 3f + (i * 2f) else 2f,
                        targetValue = if (isPlaying) 10f - (i * 2f) else 2f,
                        animationSpec = infiniteRepeatable(
                            animation = tween(300 + i * 100, easing = FastOutSlowInEasing),
                            repeatMode = RepeatMode.Reverse
                        ),
                        label = "fluid_bar_$i"
                    )
                    Box(
                        modifier = Modifier
                            .width(2.dp)
                            .height(barHeight.dp)
                            .clip(CircleShape)
                            .background(Color(0xFF00E5FF))
                    )
                }
            }
        }
    }
}

/**
 * Floating "Q Search" pill directly above dock
 * Seen in all 3 screenshots (Image 1, Image 2, Image 3)
 */
@Composable
fun ColorOS15SearchPill(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .testTag("floating_search_pill")
            .clip(RoundedCornerShape(20.dp))
            .background(Color(0x381E293B))
            .border(1.dp, Color(0x33FFFFFF), RoundedCornerShape(20.dp))
            .clickable { onClick() }
            .padding(horizontal = 22.dp, vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Icon(
                imageVector = Icons.Rounded.Search,
                contentDescription = "Search",
                tint = Color.White.copy(alpha = 0.85f),
                modifier = Modifier.size(16.dp)
            )
            Text(
                text = "Search",
                color = Color.White.copy(alpha = 0.85f),
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}
