package com.example.ui.components

import android.widget.Toast
import androidx.compose.animation.*
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.SimpleDateFormat
import java.util.*

data class ColorOSNotificationItem(
    val id: String,
    val appName: String,
    val icon: ImageVector,
    val iconColor: Color,
    val time: String,
    val title: String,
    val body: String,
    val hasAction: Boolean = false,
    val actionText: String = ""
)

@Composable
fun ColorOSNotificationShade(
    isOpen: Boolean,
    onClose: () -> Unit,
    accentColor: Color,
    isTorchOn: Boolean,
    onToggleTorch: () -> Unit,
    isDarkTheme: Boolean,
    onToggleDarkTheme: (Boolean) -> Unit,
    isPlayingMusic: Boolean,
    trackTitle: String,
    artistName: String,
    onToggleMusic: () -> Unit,
    onNextTrack: () -> Unit,
    onOpenSettings: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var wifiEnabled by remember { mutableStateOf(true) }
    var bluetoothEnabled by remember { mutableStateOf(true) }
    var mobileDataEnabled by remember { mutableStateOf(true) }
    var autoRotateEnabled by remember { mutableStateOf(true) }
    var dndEnabled by remember { mutableStateOf(false) }
    var screenRecordEnabled by remember { mutableStateOf(false) }

    var brightness by remember { mutableFloatStateOf(0.75f) }
    var volume by remember { mutableFloatStateOf(0.65f) }

    var notificationList by remember {
        mutableStateOf(
            listOf(
                ColorOSNotificationItem(
                    id = "notif_msg",
                    appName = "Messages",
                    icon = Icons.Rounded.ChatBubble,
                    iconColor = Color(0xFF10B981),
                    time = "2m ago",
                    title = "Sarah Chen",
                    body = "Did you see the new ColorOS 15 Aquamorphic updates? The fluid animations are super smooth!",
                    hasAction = true,
                    actionText = "Reply"
                ),
                ColorOSNotificationItem(
                    id = "notif_google",
                    appName = "Verification",
                    icon = Icons.Rounded.Security,
                    iconColor = Color(0xFF0078FE),
                    time = "5m ago",
                    title = "Google Security",
                    body = "Your verification code is 849-204. Valid for 10 minutes. Do not share with anyone.",
                    hasAction = true,
                    actionText = "Copy Code"
                ),
                ColorOSNotificationItem(
                    id = "notif_health",
                    appName = "O-Health",
                    icon = Icons.Rounded.Favorite,
                    iconColor = Color(0xFFEF4444),
                    time = "18m ago",
                    title = "Daily Milestone Achieved!",
                    body = "Awesome job! You reached 6,842 steps today (342 kcal burned). Keep up the great pace!"
                ),
                ColorOSNotificationItem(
                    id = "notif_theme",
                    appName = "Theme Store",
                    icon = Icons.Rounded.Palette,
                    iconColor = Color(0xFF8B5CF6),
                    time = "1h ago",
                    title = "New ColorOS 16 Themes Available",
                    body = "Explore Neon Cyberpunk and Frosted Glass icon suites in your Theme Store today."
                ),
                ColorOSNotificationItem(
                    id = "notif_vooc",
                    appName = "SuperVOOC Battery",
                    icon = Icons.Rounded.Bolt,
                    iconColor = Color(0xFF10B981),
                    time = "Just now",
                    title = "SuperVOOC 80W Fast Charging",
                    body = "89% · Approx. 4 minutes until battery is fully charged."
                )
            )
        )
    }

    AnimatedVisibility(
        visible = isOpen,
        enter = slideInVertically(
            initialOffsetY = { -it },
            animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = Spring.StiffnessMedium)
        ) + fadeIn(),
        exit = slideOutVertically(
            targetOffsetY = { -it },
            animationSpec = spring(stiffness = Spring.StiffnessMedium)
        ) + fadeOut(),
        modifier = modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xF2080D16))
                .statusBarsPadding()
                .navigationBarsPadding()
                .testTag("coloros_notification_shade")
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                // Top Header Row: Time, Battery, Settings & Close
                val currentTime = remember {
                    SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())
                }
                val currentDate = remember {
                    SimpleDateFormat("EEEE, MMMM d", Locale.getDefault()).format(Date())
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 4.dp, vertical = 6.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = currentTime,
                            color = Color.White,
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = (-0.5).sp
                        )
                        Text(
                            text = currentDate,
                            color = Color.White.copy(alpha = 0.7f),
                            fontSize = 13.sp
                        )
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        // SuperVOOC Battery Pill
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .clip(RoundedCornerShape(14.dp))
                                .background(Color(0x3310B981))
                                .border(0.5.dp, Color(0x6610B981), RoundedCornerShape(14.dp))
                                .padding(horizontal = 8.dp, vertical = 5.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.Bolt,
                                contentDescription = null,
                                tint = Color(0xFF10B981),
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(3.dp))
                            Text(
                                text = "89% · VOOC",
                                color = Color.White,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        // Settings Button
                        IconButton(
                            onClick = onOpenSettings,
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                                .background(Color(0x2BFFFFFF))
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.Settings,
                                contentDescription = "Settings",
                                tint = Color.White,
                                modifier = Modifier.size(18.dp)
                            )
                        }

                        // Close Button
                        IconButton(
                            onClick = onClose,
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                                .background(Color(0x2BFFFFFF))
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.Close,
                                contentDescription = "Close",
                                tint = Color.White,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Scrollable Content
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Quick Settings Tiles: 2 Rows x 4 Columns
                    item {
                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            // Row 1
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                QuickSettingTile(
                                    icon = Icons.Rounded.Wifi,
                                    label = "Wi-Fi",
                                    subLabel = if (wifiEnabled) "OPPO_5G" else "Off",
                                    isActive = wifiEnabled,
                                    accentColor = accentColor,
                                    onClick = { wifiEnabled = !wifiEnabled },
                                    modifier = Modifier.weight(1f)
                                )
                                QuickSettingTile(
                                    icon = Icons.Rounded.Bluetooth,
                                    label = "Bluetooth",
                                    subLabel = if (bluetoothEnabled) "Enco X3" else "Off",
                                    isActive = bluetoothEnabled,
                                    accentColor = accentColor,
                                    onClick = { bluetoothEnabled = !bluetoothEnabled },
                                    modifier = Modifier.weight(1f)
                                )
                                QuickSettingTile(
                                    icon = Icons.Rounded.SignalCellularAlt,
                                    label = "Mobile Data",
                                    subLabel = if (mobileDataEnabled) "5G Ultra" else "Off",
                                    isActive = mobileDataEnabled,
                                    accentColor = accentColor,
                                    onClick = { mobileDataEnabled = !mobileDataEnabled },
                                    modifier = Modifier.weight(1f)
                                )
                                QuickSettingTile(
                                    icon = Icons.Rounded.FlashlightOn,
                                    label = "Torch",
                                    subLabel = if (isTorchOn) "On" else "Off",
                                    isActive = isTorchOn,
                                    accentColor = accentColor,
                                    onClick = onToggleTorch,
                                    modifier = Modifier.weight(1f)
                                )
                            }

                            // Row 2
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                QuickSettingTile(
                                    icon = Icons.Rounded.DarkMode,
                                    label = "Dark Mode",
                                    subLabel = if (isDarkTheme) "On" else "Off",
                                    isActive = isDarkTheme,
                                    accentColor = accentColor,
                                    onClick = { onToggleDarkTheme(!isDarkTheme) },
                                    modifier = Modifier.weight(1f)
                                )
                                QuickSettingTile(
                                    icon = Icons.Rounded.ScreenRotation,
                                    label = "Auto-Rotate",
                                    subLabel = if (autoRotateEnabled) "On" else "Locked",
                                    isActive = autoRotateEnabled,
                                    accentColor = accentColor,
                                    onClick = { autoRotateEnabled = !autoRotateEnabled },
                                    modifier = Modifier.weight(1f)
                                )
                                QuickSettingTile(
                                    icon = Icons.Rounded.DoNotDisturbOn,
                                    label = "DND",
                                    subLabel = if (dndEnabled) "Muted" else "Off",
                                    isActive = dndEnabled,
                                    accentColor = accentColor,
                                    onClick = { dndEnabled = !dndEnabled },
                                    modifier = Modifier.weight(1f)
                                )
                                QuickSettingTile(
                                    icon = Icons.Rounded.Videocam,
                                    label = "Record",
                                    subLabel = if (screenRecordEnabled) "Recording" else "Ready",
                                    isActive = screenRecordEnabled,
                                    accentColor = accentColor,
                                    onClick = {
                                        screenRecordEnabled = !screenRecordEnabled
                                        Toast.makeText(context, if (screenRecordEnabled) "Screen Recorder started" else "Recording saved", Toast.LENGTH_SHORT).show()
                                    },
                                    modifier = Modifier.weight(1f)
                                )
                            }
                        }
                    }

                    // Dual Fluid Capsule Sliders: Brightness & Volume
                    item {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            FluidCapsuleSlider(
                                icon = Icons.Rounded.WbSunny,
                                value = brightness,
                                onValueChange = { brightness = it },
                                accentColor = accentColor,
                                label = "Brightness",
                                modifier = Modifier.weight(1f)
                            )
                            FluidCapsuleSlider(
                                icon = Icons.Rounded.VolumeUp,
                                value = volume,
                                onValueChange = { volume = it },
                                accentColor = accentColor,
                                label = "Volume",
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }

                    // ColorOS Fluid Cloud Media Player Widget
                    item {
                        FrostedGlassCard(
                            shape = RoundedCornerShape(20.dp),
                            backgroundColor = Color(0x381E293B),
                            borderColor = Color(0x40FFFFFF),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(42.dp)
                                            .clip(RoundedCornerShape(12.dp))
                                            .background(
                                                Brush.linearGradient(
                                                    listOf(Color(0xFFE040FB), Color(0xFF0078FE))
                                                )
                                            ),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Icon(
                                            imageVector = Icons.Rounded.MusicNote,
                                            contentDescription = null,
                                            tint = Color.White,
                                            modifier = Modifier.size(22.dp)
                                        )
                                    }

                                    Spacer(modifier = Modifier.width(10.dp))

                                    Column {
                                        Text(
                                            text = trackTitle,
                                            color = Color.White,
                                            fontSize = 13.sp,
                                            fontWeight = FontWeight.Bold,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                        Text(
                                            text = "$artistName · ColorOS Sound",
                                            color = Color.White.copy(alpha = 0.6f),
                                            fontSize = 11.sp,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                    }
                                }

                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    IconButton(
                                        onClick = onToggleMusic,
                                        modifier = Modifier
                                            .size(34.dp)
                                            .clip(CircleShape)
                                            .background(accentColor)
                                    ) {
                                        Icon(
                                            imageVector = if (isPlayingMusic) Icons.Rounded.Pause else Icons.Rounded.PlayArrow,
                                            contentDescription = "Play/Pause",
                                            tint = Color.Black,
                                            modifier = Modifier.size(18.dp)
                                        )
                                    }

                                    IconButton(
                                        onClick = onNextTrack,
                                        modifier = Modifier
                                            .size(34.dp)
                                            .clip(CircleShape)
                                            .background(Color(0x22FFFFFF))
                                    ) {
                                        Icon(
                                            imageVector = Icons.Rounded.SkipNext,
                                            contentDescription = "Next",
                                            tint = Color.White,
                                            modifier = Modifier.size(18.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }

                    // Notification Header
                    item {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 4.dp, vertical = 4.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "NOTIFICATIONS (${notificationList.size})",
                                color = Color.White.copy(alpha = 0.6f),
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 0.5.sp
                            )

                            if (notificationList.isNotEmpty()) {
                                Text(
                                    text = "Clear All",
                                    color = accentColor,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(8.dp))
                                        .clickable {
                                            notificationList = emptyList()
                                            Toast.makeText(context, "Notifications cleared", Toast.LENGTH_SHORT).show()
                                        }
                                        .padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }
                    }

                    // Notifications Stream
                    if (notificationList.isEmpty()) {
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 24.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Icon(
                                        imageVector = Icons.Rounded.NotificationsNone,
                                        contentDescription = null,
                                        tint = Color.White.copy(alpha = 0.4f),
                                        modifier = Modifier.size(36.dp)
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text(
                                        text = "No new notifications",
                                        color = Color.White.copy(alpha = 0.5f),
                                        fontSize = 13.sp
                                    )
                                }
                            }
                        }
                    } else {
                        items(notificationList, key = { it.id }) { item ->
                            NotificationCard(
                                item = item,
                                onDismiss = {
                                    notificationList = notificationList.filter { it.id != item.id }
                                },
                                onAction = {
                                    Toast.makeText(context, "${item.actionText} clicked", Toast.LENGTH_SHORT).show()
                                },
                                accentColor = accentColor
                            )
                        }
                    }
                }

                // Bottom Dismiss Pull Handle
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp, bottom = 4.dp)
                        .clickable { onClose() },
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .width(48.dp)
                            .height(5.dp)
                            .clip(RoundedCornerShape(3.dp))
                            .background(Color.White.copy(alpha = 0.4f))
                    )
                }
            }
        }
    }
}

@Composable
private fun QuickSettingTile(
    icon: ImageVector,
    label: String,
    subLabel: String,
    isActive: Boolean,
    accentColor: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(if (isActive) accentColor.copy(alpha = 0.25f) else Color(0x24FFFFFF))
            .border(
                width = 1.dp,
                color = if (isActive) accentColor else Color(0x26FFFFFF),
                shape = RoundedCornerShape(16.dp)
            )
            .clickable { onClick() }
            .padding(vertical = 10.dp, horizontal = 4.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .background(if (isActive) accentColor else Color(0x33FFFFFF)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = label,
                    tint = if (isActive) Color.Black else Color.White,
                    modifier = Modifier.size(18.dp)
                )
            }
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = label,
                color = Color.White,
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = subLabel,
                color = if (isActive) accentColor else Color.White.copy(alpha = 0.5f),
                fontSize = 9.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
private fun FluidCapsuleSlider(
    icon: ImageVector,
    value: Float,
    onValueChange: (Float) -> Unit,
    accentColor: Color,
    label: String,
    modifier: Modifier = Modifier
) {
    BoxWithConstraints(
        modifier = modifier
            .height(48.dp)
            .clip(RoundedCornerShape(24.dp))
            .background(Color(0x381E293B))
            .border(1.dp, Color(0x33FFFFFF), RoundedCornerShape(24.dp))
            .draggable(
                orientation = Orientation.Horizontal,
                state = rememberDraggableState { delta ->
                    val change = delta / 250f
                    val newValue = (value + change).coerceIn(0.1f, 1.0f)
                    onValueChange(newValue)
                }
            )
    ) {
        val totalWidth = maxWidth
        val fillWidth = totalWidth * value

        // Active fill bar
        Box(
            modifier = Modifier
                .width(fillWidth)
                .fillMaxHeight()
                .background(
                    Brush.horizontalGradient(
                        listOf(accentColor.copy(alpha = 0.4f), accentColor.copy(alpha = 0.85f))
                    )
                )
        )

        // Contents
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = icon,
                    contentDescription = label,
                    tint = Color.White,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = label,
                    color = Color.White,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            Text(
                text = "${(value * 100).toInt()}%",
                color = Color.White,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
private fun NotificationCard(
    item: ColorOSNotificationItem,
    onDismiss: () -> Unit,
    onAction: () -> Unit,
    accentColor: Color
) {
    FrostedGlassCard(
        shape = RoundedCornerShape(18.dp),
        backgroundColor = Color(0x38192636),
        borderColor = Color(0x33FFFFFF),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp)
        ) {
            // Header: Icon, App Name, Time, Dismiss
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(20.dp)
                            .clip(CircleShape)
                            .background(item.iconColor),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(12.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = item.appName,
                        color = Color.White.copy(alpha = 0.8f),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "· ${item.time}",
                        color = Color.White.copy(alpha = 0.5f),
                        fontSize = 10.sp
                    )
                }

                IconButton(
                    onClick = onDismiss,
                    modifier = Modifier.size(20.dp)
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Close,
                        contentDescription = "Dismiss",
                        tint = Color.White.copy(alpha = 0.6f),
                        modifier = Modifier.size(14.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = item.title,
                color = Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(2.dp))

            Text(
                text = item.body,
                color = Color.White.copy(alpha = 0.75f),
                fontSize = 12.sp,
                lineHeight = 17.sp
            )

            if (item.hasAction) {
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Button(
                        onClick = onAction,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = accentColor,
                            contentColor = Color.Black
                        ),
                        shape = RoundedCornerShape(12.dp),
                        contentPadding = PaddingValues(horizontal = 14.dp, vertical = 2.dp),
                        modifier = Modifier.height(28.dp)
                    ) {
                        Text(
                            text = item.actionText,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}
