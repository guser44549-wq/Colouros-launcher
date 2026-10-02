package com.example.ui.shelf

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.FrostedGlassCard
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun ShelfScreen(
    accentColor: Color,
    onCloseShelf: () -> Unit,
    currentSteps: Int,
    onAddSteps: () -> Unit,
    isMusicPlaying: Boolean,
    onToggleMusic: () -> Unit,
    onNextTrack: () -> Unit,
    trackTitle: String,
    artistName: String,
    isTorchOn: Boolean,
    onToggleTorch: () -> Unit,
    onOpenCalculator: () -> Unit,
    onOpenRecorder: () -> Unit,
    onBoostDevice: () -> Unit,
    isBoosting: Boolean,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()
    var quickMemoText by remember { mutableStateOf("ColorOS 15 fluid design test. Tap to edit your quick notes here.") }

    val formattedDate = remember {
        SimpleDateFormat("EEEE, MMMM d", Locale.getDefault()).format(Date())
    }

    Box(
        modifier = modifier
            .testTag("shelf_screen")
            .fillMaxSize()
            .background(Color(0xD909111D))
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Top Bar with ColorOS Shelf Title & Close button
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "ColorOS Shelf",
                        style = TextStyle(
                            color = Color.White,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Text(
                        text = formattedDate,
                        style = TextStyle(
                            color = Color.White.copy(alpha = 0.65f),
                            fontSize = 13.sp
                        )
                    )
                }

                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(Color(0x33FFFFFF))
                        .clickable { onCloseShelf() },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Close,
                        contentDescription = "Close Shelf",
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            // Live Weather Widget
            LiveWeatherWidget(
                accentColor = accentColor,
                onOpenWeatherApp = {}
            )

            // Health and Music Row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(170.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                HealthStepsWidget(
                    currentSteps = currentSteps,
                    accentColor = accentColor,
                    onAddSteps = onAddSteps,
                    modifier = Modifier.weight(1f)
                )

                FluidMusicWidget(
                    isPlaying = isMusicPlaying,
                    onTogglePlay = onToggleMusic,
                    onNextTrack = onNextTrack,
                    trackTitle = trackTitle,
                    artistName = artistName,
                    accentColor = accentColor,
                    modifier = Modifier.weight(1f)
                )
            }

            // Quick Tools Card
            QuickToolsCard(
                isTorchOn = isTorchOn,
                onToggleTorch = onToggleTorch,
                onOpenCalculator = onOpenCalculator,
                onOpenRecorder = onOpenRecorder,
                onOpenMemo = {},
                accentColor = accentColor
            )

            // Device Status & RAM Booster
            DeviceStatusWidget(
                accentColor = accentColor,
                onBoost = onBoostDevice,
                isBoosting = isBoosting
            )

            // Quick Memo Pad
            FrostedGlassCard(
                shape = RoundedCornerShape(22.dp),
                backgroundColor = Color(0x38192636),
                borderColor = Color(0x40FFFFFF),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Rounded.StickyNote2,
                                contentDescription = null,
                                tint = Color(0xFFFFB300),
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Quick Note",
                                color = Color.White,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = quickMemoText,
                        onValueChange = { quickMemoText = it },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White.copy(alpha = 0.9f),
                            focusedBorderColor = accentColor,
                            unfocusedBorderColor = Color.White.copy(alpha = 0.2f),
                            focusedContainerColor = Color(0x22000000),
                            unfocusedContainerColor = Color(0x15000000)
                        ),
                        textStyle = TextStyle(fontSize = 13.sp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}
