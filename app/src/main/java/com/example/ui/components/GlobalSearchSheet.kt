package com.example.ui.components

import androidx.compose.animation.*
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.AppModel
import com.example.model.IconDesignStyle
import com.example.model.IconShape

@Composable
fun GlobalSearchSheet(
    isOpen: Boolean,
    onClose: () -> Unit,
    apps: List<AppModel>,
    iconShape: IconShape,
    accentColor: Color,
    designStyle: IconDesignStyle = IconDesignStyle.COLOROS_15,
    onAppClick: (AppModel) -> Unit,
    modifier: Modifier = Modifier
) {
    var searchQuery by remember { mutableStateOf("") }

    val matchedApps = remember(searchQuery, apps) {
        if (searchQuery.isBlank()) {
            apps.take(8)
        } else {
            apps.filter {
                it.label.contains(searchQuery, ignoreCase = true) ||
                        it.packageName.contains(searchQuery, ignoreCase = true)
            }.take(10)
        }
    }

    AnimatedVisibility(
        visible = isOpen,
        enter = slideInVertically(initialOffsetY = { -it }) + fadeIn(),
        exit = slideOutVertically(targetOffsetY = { -it }) + fadeOut(),
        modifier = modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xD90A111C))
                .clickable { onClose() }
                .statusBarsPadding()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(enabled = false) {}
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Search Input Card
                FrostedGlassCard(
                    shape = RoundedCornerShape(22.dp),
                    backgroundColor = Color(0x551E293B),
                    borderColor = Color(0x66FFFFFF),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 14.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Search,
                            contentDescription = "Search",
                            tint = accentColor,
                            modifier = Modifier.size(22.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        TextField(
                            value = searchQuery,
                            onValueChange = { searchQuery = it },
                            placeholder = {
                                Text(
                                    text = "ColorOS Global Search…",
                                    color = Color.White.copy(alpha = 0.5f),
                                    fontSize = 15.sp
                                )
                            },
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = Color.Transparent,
                                unfocusedContainerColor = Color.Transparent,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                focusedTextColor = Color.White,
                                unfocusedTextColor = Color.White
                            ),
                            singleLine = true,
                            modifier = Modifier
                                .weight(1f)
                                .testTag("global_search_input")
                        )
                        IconButton(onClick = onClose) {
                            Icon(
                                imageVector = Icons.Rounded.Close,
                                contentDescription = "Close",
                                tint = Color.White.copy(alpha = 0.8f)
                            )
                        }
                    }
                }

                // Quick Suggestions Header
                Text(
                    text = if (searchQuery.isBlank()) "FREQUENT APPS" else "SEARCH RESULTS",
                    color = Color.White.copy(alpha = 0.6f),
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 4.dp)
                )

                // Apps Row / Grid
                FrostedGlassCard(
                    shape = RoundedCornerShape(24.dp),
                    backgroundColor = Color(0x38192636),
                    borderColor = Color(0x33FFFFFF),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        matchedApps.chunked(4).forEach { rowApps ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 6.dp),
                                horizontalArrangement = Arrangement.SpaceAround
                            ) {
                                rowApps.forEach { app ->
                                    AppIconView(
                                        app = app,
                                        iconShape = iconShape,
                                        iconScale = 0.95f,
                                        showLabel = true,
                                        designStyle = designStyle,
                                        onClick = {
                                            onAppClick(app)
                                            onClose()
                                        }
                                    )
                                }
                            }
                        }
                    }
                }

                // Quick Tools in Global Search
                FrostedGlassCard(
                    shape = RoundedCornerShape(20.dp),
                    backgroundColor = Color(0x2B1E293B),
                    borderColor = Color(0x33FFFFFF),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        SearchQuickAction(icon = Icons.Rounded.Wifi, label = "Wi-Fi", accent = accentColor)
                        SearchQuickAction(icon = Icons.Rounded.Bluetooth, label = "Bluetooth", accent = accentColor)
                        SearchQuickAction(icon = Icons.Rounded.VolumeUp, label = "Sound", accent = accentColor)
                        SearchQuickAction(icon = Icons.Rounded.FlashlightOn, label = "Torch", accent = accentColor)
                    }
                }
            }
        }
    }
}

@Composable
private fun SearchQuickAction(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    accent: Color
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(Color(0x33FFFFFF)),
            contentAlignment = Alignment.Center
        ) {
            Icon(imageVector = icon, contentDescription = label, tint = Color.White, modifier = Modifier.size(20.dp))
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = label, color = Color.White.copy(alpha = 0.8f), fontSize = 10.sp)
    }
}
