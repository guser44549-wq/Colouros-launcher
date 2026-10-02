package com.example.ui.shelf

import android.content.Context
import android.hardware.camera2.CameraManager
import android.widget.Toast
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
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
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.FrostedGlassCard

@Composable
fun LiveWeatherWidget(
    accentColor: Color,
    modifier: Modifier = Modifier,
    onOpenWeatherApp: () -> Unit = {}
) {
    FrostedGlassCard(
        shape = RoundedCornerShape(26.dp),
        backgroundColor = Color(0x40102A43),
        borderColor = Color(0x4070B6FF),
        modifier = modifier
            .testTag("weather_widget")
            .fillMaxWidth()
            .clickable { onOpenWeatherApp() }
            .padding(2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Rounded.LocationOn,
                            contentDescription = null,
                            tint = accentColor,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "Tokyo, Japan",
                            color = Color.White,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                    Text(
                        text = "Mostly Sunny · AQI 28",
                        color = Color.White.copy(alpha = 0.7f),
                        fontSize = 12.sp
                    )
                }

                // Animated Sun
                val infiniteTransition = rememberInfiniteTransition(label = "weather_sun")
                val rotation by infiniteTransition.animateFloat(
                    initialValue = 0f,
                    targetValue = 360f,
                    animationSpec = infiniteRepeatable(
                        animation = tween(20000, easing = LinearEasing),
                        repeatMode = RepeatMode.Restart
                    ),
                    label = "sun_rotation"
                )

                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = Icons.Rounded.WbSunny,
                        contentDescription = "Sun",
                        tint = Color(0xFFFFB300),
                        modifier = Modifier
                            .size(38.dp)
                            .rotate(rotation)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                Row(verticalAlignment = Alignment.Top) {
                    Text(
                        text = "24",
                        color = Color.White,
                        fontSize = 42.sp,
                        fontWeight = FontWeight.Light
                    )
                    Text(
                        text = "°C",
                        color = accentColor,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }

                Row(horizontalArrangement = Arrangement.spacedBy(14.dp)) {
                    HourlyWeatherPill("Now", "24°", Icons.Rounded.WbSunny)
                    HourlyWeatherPill("13:00", "25°", Icons.Rounded.WbSunny)
                    HourlyWeatherPill("14:00", "26°", Icons.Rounded.WbCloudy)
                    HourlyWeatherPill("15:00", "23°", Icons.Rounded.WbCloudy)
                }
            }
        }
    }
}

@Composable
private fun HourlyWeatherPill(
    time: String,
    temp: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        Text(text = time, color = Color.White.copy(alpha = 0.6f), fontSize = 10.sp)
        Icon(imageVector = icon, contentDescription = null, tint = Color(0xFFFFD54F), modifier = Modifier.size(16.dp))
        Text(text = temp, color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
    }
}

@Composable
fun HealthStepsWidget(
    currentSteps: Int,
    goalSteps: Int = 10000,
    accentColor: Color,
    onAddSteps: () -> Unit,
    modifier: Modifier = Modifier
) {
    val progress = (currentSteps.toFloat() / goalSteps).coerceIn(0f, 1f)
    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = Spring.StiffnessLow),
        label = "steps_progress"
    )

    FrostedGlassCard(
        shape = RoundedCornerShape(26.dp),
        backgroundColor = Color(0x400C2E24),
        borderColor = Color(0x4000C9A7),
        modifier = modifier
            .testTag("health_widget")
            .fillMaxSize()
            .padding(2.dp)
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
                    Icon(
                        imageVector = Icons.Rounded.DirectionsRun,
                        contentDescription = null,
                        tint = Color(0xFF00C9A7),
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "O-Health",
                        color = Color.White,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                // Simulate steps button
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(Color(0x3300C9A7))
                        .clickable { onAddSteps() }
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                ) {
                    Text(text = "+500", color = Color(0xFF80E27E), fontSize = 10.sp, fontWeight = FontWeight.Bold)
                }
            }

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp)
            ) {
                androidx.compose.foundation.Canvas(modifier = Modifier.size(60.dp)) {
                    val stroke = Stroke(width = 6.dp.toPx(), cap = StrokeCap.Round)
                    drawCircle(
                        color = Color(0x3300C9A7),
                        style = stroke
                    )
                    drawArc(
                        brush = Brush.sweepGradient(
                            listOf(Color(0xFF00C9A7), Color(0xFF80E27E), Color(0xFF00C9A7))
                        ),
                        startAngle = -90f,
                        sweepAngle = animatedProgress * 360f,
                        useCenter = false,
                        style = stroke
                    )
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "$currentSteps",
                        color = Color.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "steps",
                        color = Color.White.copy(alpha = 0.6f),
                        fontSize = 9.sp
                    )
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "${String.format("%.1f", currentSteps * 0.00075)} km",
                    color = Color.White.copy(alpha = 0.8f),
                    fontSize = 11.sp
                )
                Text(
                    text = "${(currentSteps * 0.04).toInt()} kcal",
                    color = Color(0xFFFF8C00),
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

@Composable
fun FluidMusicWidget(
    isPlaying: Boolean,
    onTogglePlay: () -> Unit,
    onNextTrack: () -> Unit,
    trackTitle: String,
    artistName: String,
    accentColor: Color,
    modifier: Modifier = Modifier
) {
    FrostedGlassCard(
        shape = RoundedCornerShape(26.dp),
        backgroundColor = Color(0x402A1A40),
        borderColor = Color(0x409B51E0),
        modifier = modifier
            .testTag("music_widget")
            .fillMaxSize()
            .padding(2.dp)
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
                    Icon(
                        imageVector = Icons.Rounded.MusicNote,
                        contentDescription = null,
                        tint = Color(0xFFC77DFF),
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = "Music", color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                }

                // Animated Equalizer Bars
                Row(
                    horizontalArrangement = Arrangement.spacedBy(2.dp),
                    verticalAlignment = Alignment.Bottom,
                    modifier = Modifier.height(14.dp)
                ) {
                    for (i in 0 until 4) {
                        val barHeight by rememberInfiniteTransition(label = "music_bar_$i").animateFloat(
                            initialValue = if (isPlaying) 4f + (i * 2f) else 3f,
                            targetValue = if (isPlaying) 14f - (i * 2f) else 3f,
                            animationSpec = infiniteRepeatable(
                                animation = tween(350 + i * 80, easing = FastOutSlowInEasing),
                                repeatMode = RepeatMode.Reverse
                            ),
                            label = "bar_$i"
                        )
                        Box(
                            modifier = Modifier
                                .width(2.5.dp)
                                .height(barHeight.dp)
                                .clip(RoundedCornerShape(1.dp))
                                .background(accentColor)
                        )
                    }
                }
            }

            Column {
                Text(
                    text = trackTitle,
                    color = Color.White,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1
                )
                Text(
                    text = artistName,
                    color = Color.White.copy(alpha = 0.65f),
                    fontSize = 10.sp,
                    maxLines = 1
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onTogglePlay, modifier = Modifier.size(34.dp)) {
                    Icon(
                        imageVector = if (isPlaying) Icons.Rounded.PauseCircleFilled else Icons.Rounded.PlayCircleFilled,
                        contentDescription = "Play/Pause",
                        tint = Color.White,
                        modifier = Modifier.size(30.dp)
                    )
                }

                IconButton(onClick = onNextTrack, modifier = Modifier.size(28.dp)) {
                    Icon(
                        imageVector = Icons.Rounded.SkipNext,
                        contentDescription = "Next",
                        tint = Color.White.copy(alpha = 0.8f),
                        modifier = Modifier.size(22.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun QuickToolsCard(
    isTorchOn: Boolean,
    onToggleTorch: () -> Unit,
    onOpenCalculator: () -> Unit,
    onOpenRecorder: () -> Unit,
    onOpenMemo: () -> Unit,
    accentColor: Color,
    modifier: Modifier = Modifier
) {
    FrostedGlassCard(
        shape = RoundedCornerShape(22.dp),
        backgroundColor = Color(0x38192636),
        borderColor = Color(0x40FFFFFF),
        modifier = modifier
            .testTag("quick_tools_widget")
            .fillMaxWidth()
            .padding(2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp, horizontal = 12.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Flashlight Tool
            QuickToolItem(
                icon = Icons.Rounded.FlashlightOn,
                label = "Torch",
                isActive = isTorchOn,
                activeColor = Color(0xFFFFD54F),
                onClick = onToggleTorch
            )

            // Calculator
            QuickToolItem(
                icon = Icons.Rounded.Calculate,
                label = "Calc",
                isActive = false,
                activeColor = accentColor,
                onClick = onOpenCalculator
            )

            // Recorder
            QuickToolItem(
                icon = Icons.Rounded.Mic,
                label = "Record",
                isActive = false,
                activeColor = Color(0xFFFF5252),
                onClick = onOpenRecorder
            )

            // Sticky Note
            QuickToolItem(
                icon = Icons.Rounded.EditNote,
                label = "Memo",
                isActive = false,
                activeColor = Color(0xFF00C9A7),
                onClick = onOpenMemo
            )
        }
    }
}

@Composable
private fun QuickToolItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    isActive: Boolean,
    activeColor: Color,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .clickable { onClick() }
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(42.dp)
                .clip(CircleShape)
                .background(if (isActive) activeColor.copy(alpha = 0.35f) else Color(0x2BFFFFFF))
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = if (isActive) activeColor else Color.White,
                modifier = Modifier.size(22.dp)
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = label,
            color = Color.White.copy(alpha = 0.85f),
            fontSize = 11.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
fun DeviceStatusWidget(
    accentColor: Color,
    onBoost: () -> Unit,
    isBoosting: Boolean,
    modifier: Modifier = Modifier
) {
    FrostedGlassCard(
        shape = RoundedCornerShape(22.dp),
        backgroundColor = Color(0x38192636),
        borderColor = Color(0x40FFFFFF),
        modifier = modifier
            .testTag("device_status_widget")
            .fillMaxWidth()
            .padding(2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Rounded.Speed,
                        contentDescription = null,
                        tint = accentColor,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Device Health",
                        color = Color.White,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "RAM 4.2 GB / 12 GB · Battery 82%",
                    color = Color.White.copy(alpha = 0.65f),
                    fontSize = 11.sp
                )
            }

            Button(
                onClick = onBoost,
                enabled = !isBoosting,
                colors = ButtonDefaults.buttonColors(
                    containerColor = accentColor,
                    contentColor = Color.Black
                ),
                shape = RoundedCornerShape(16.dp),
                contentPadding = PaddingValues(horizontal = 14.dp, vertical = 6.dp),
                modifier = Modifier.height(34.dp)
            ) {
                if (isBoosting) {
                    CircularProgressIndicator(
                        color = Color.Black,
                        strokeWidth = 2.dp,
                        modifier = Modifier.size(16.dp)
                    )
                } else {
                    Icon(Icons.Rounded.Bolt, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = "Boost", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}
