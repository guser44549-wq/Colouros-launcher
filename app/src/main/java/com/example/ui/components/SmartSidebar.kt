package com.example.ui.components

import android.widget.Toast
import androidx.compose.animation.*
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.AppModel
import kotlin.math.roundToInt

@Composable
fun SmartSidebarOverlay(
    isOpen: Boolean,
    onOpenChange: (Boolean) -> Unit,
    quickApps: List<AppModel>,
    accentColor: Color,
    onAppClick: (AppModel) -> Unit,
    onOpenMiniWindow: (AppModel) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var offsetY by remember { mutableStateOf(260f) }

    Box(modifier = modifier.fillMaxSize()) {
        // Floating Edge Handle (ColorOS 15 signature translucent pill at screen edge)
        if (!isOpen) {
            Box(
                modifier = Modifier
                    .offset { IntOffset(0, offsetY.roundToInt()) }
                    .align(Alignment.TopEnd)
                    .width(18.dp)
                    .height(68.dp)
                    .shadow(elevation = 8.dp, shape = RoundedCornerShape(topStart = 10.dp, bottomStart = 10.dp))
                    .clip(RoundedCornerShape(topStart = 10.dp, bottomStart = 10.dp))
                    .background(Color(0xB32B3B4E))
                    .border(
                        1.dp,
                        Color(0x80FFFFFF),
                        RoundedCornerShape(topStart = 10.dp, bottomStart = 10.dp)
                    )
                    .pointerInput(Unit) {
                        detectDragGestures { change, dragAmount ->
                            change.consume()
                            offsetY = (offsetY + dragAmount.y).coerceIn(100f, 1200f)
                        }
                    }
                    .clickable { onOpenChange(true) }
                    .testTag("smart_sidebar_handle"),
                contentAlignment = Alignment.Center
            ) {
                // Vertical accent line
                Box(
                    modifier = Modifier
                        .width(3.dp)
                        .height(28.dp)
                        .clip(CircleShape)
                        .background(accentColor)
                )
            }
        }

        // Expanded Sidebar Panel
        AnimatedVisibility(
            visible = isOpen,
            enter = slideInHorizontally(
                initialOffsetX = { it },
                animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = Spring.StiffnessMedium)
            ) + fadeIn(),
            exit = slideOutHorizontally(
                targetOffsetX = { it },
                animationSpec = spring(stiffness = Spring.StiffnessMedium)
            ) + fadeOut(),
            modifier = Modifier.fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0x66000000))
                    .clickable { onOpenChange(false) }
            ) {
                FrostedGlassCard(
                    shape = RoundedCornerShape(topStart = 28.dp, bottomStart = 28.dp),
                    backgroundColor = Color(0xF0121C28),
                    borderColor = Color(0x6670B6FF),
                    borderWidth = 1.dp,
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .width(220.dp)
                        .fillMaxHeight(0.75f)
                        .clickable(enabled = false) {}
                        .padding(vertical = 12.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        // Header
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Smart Sidebar",
                                color = Color.White,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold
                            )
                            IconButton(
                                onClick = { onOpenChange(false) },
                                modifier = Modifier.size(24.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Rounded.ChevronRight,
                                    contentDescription = "Close",
                                    tint = Color.White
                                )
                            }
                        }

                        // Smart System Tools
                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            Text(
                                text = "TOOLS",
                                color = Color.White.copy(alpha = 0.5f),
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold
                            )

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                SidebarToolItem(
                                    icon = Icons.Rounded.Crop,
                                    label = "Screenshot",
                                    onClick = {
                                        Toast.makeText(context, "Screenshot captured!", Toast.LENGTH_SHORT).show()
                                        onOpenChange(false)
                                    }
                                )
                                SidebarToolItem(
                                    icon = Icons.Rounded.PictureInPictureAlt,
                                    label = "Floating",
                                    onClick = {
                                        if (quickApps.isNotEmpty()) {
                                            onOpenMiniWindow(quickApps.first())
                                        } else {
                                            Toast.makeText(context, "Mini-Window launched", Toast.LENGTH_SHORT).show()
                                        }
                                        onOpenChange(false)
                                    }
                                )
                                SidebarToolItem(
                                    icon = Icons.Rounded.Translate,
                                    label = "Translate",
                                    onClick = {
                                        Toast.makeText(context, "Screen Translate activated", Toast.LENGTH_SHORT).show()
                                        onOpenChange(false)
                                    }
                                )
                            }
                        }

                        // Quick Apps List
                        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                            Text(
                                text = "QUICK APPS",
                                color = Color.White.copy(alpha = 0.5f),
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold
                            )

                            val sidebarApps = quickApps.take(5)
                            for (app in sidebarApps) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clip(RoundedCornerShape(12.dp))
                                        .background(Color(0x22FFFFFF))
                                        .clickable {
                                            onAppClick(app)
                                            onOpenChange(false)
                                        }
                                        .padding(horizontal = 10.dp, vertical = 8.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(28.dp)
                                            .clip(RoundedCornerShape(8.dp))
                                            .background(app.primaryColor),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        ColorOSVectorIcon(iconKey = app.iconKey, size = 16.dp)
                                    }
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Text(
                                        text = app.label,
                                        color = Color.White,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Medium,
                                        maxLines = 1
                                    )
                                }
                            }
                        }

                        // Mini-window tip
                        Text(
                            text = "ColorOS 15 Fluid Flow",
                            color = accentColor.copy(alpha = 0.8f),
                            fontSize = 10.sp,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun SidebarToolItem(
    icon: ImageVector,
    label: String,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .clickable { onClick() }
            .padding(4.dp)
    ) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(Color(0x33FFFFFF)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = Color.White,
                modifier = Modifier.size(18.dp)
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = label,
            color = Color.White.copy(alpha = 0.85f),
            fontSize = 9.sp
        )
    }
}
