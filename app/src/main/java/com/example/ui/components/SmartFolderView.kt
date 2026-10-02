package com.example.ui.components

import androidx.compose.animation.*
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.OpenInFull
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.AppModel
import com.example.model.FolderModel
import com.example.model.IconDesignStyle
import com.example.model.IconShape

@Composable
fun LargeSmartFolderCard(
    folder: FolderModel,
    iconShape: IconShape,
    accentColor: Color,
    modifier: Modifier = Modifier,
    designStyle: IconDesignStyle = IconDesignStyle.COLOROS_15,
    onAppClick: (AppModel) -> Unit,
    onExpandFolder: () -> Unit,
    onFolderLongClick: (() -> Unit)? = null
) {
    FrostedGlassCard(
        shape = RoundedCornerShape(26.dp),
        backgroundColor = Color(0x3D1B2838),
        borderColor = Color(0x40FFFFFF),
        borderWidth = 1.dp,
        modifier = modifier
            .testTag("large_folder_${folder.id}")
            .fillMaxSize()
            .pointerInput(folder.id) {
                detectTapGestures(
                    onTap = { onExpandFolder() },
                    onLongPress = { onFolderLongClick?.invoke() }
                )
            }
            .padding(4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Folder title and expand button
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp, vertical = 2.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = folder.title,
                    style = TextStyle(
                        color = Color.White,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                        shadow = Shadow(
                            color = Color.Black.copy(alpha = 0.8f),
                            offset = Offset(0f, 1f),
                            blurRadius = 2f
                        )
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f)
                )

                Box(
                    modifier = Modifier
                        .size(18.dp)
                        .clip(CircleShape)
                        .background(Color(0x33FFFFFF))
                        .clickable { onExpandFolder() },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Rounded.OpenInFull,
                        contentDescription = "Expand Folder",
                        tint = Color.White.copy(alpha = 0.85f),
                        modifier = Modifier.size(10.dp)
                    )
                }
            }

            // 3x3 Mini Interactive Apps Grid
            val displayApps = folder.apps.take(9)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                for (row in 0 until 3) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        for (col in 0 until 3) {
                            val index = row * 3 + col
                            if (index < displayApps.size) {
                                val app = displayApps[index]
                                InteractiveMiniAppIcon(
                                    app = app,
                                    iconShape = iconShape,
                                    designStyle = designStyle,
                                    onClick = { onAppClick(app) }
                                )
                            } else {
                                Spacer(modifier = Modifier.size(34.dp))
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun InteractiveMiniAppIcon(
    app: AppModel,
    iconShape: IconShape,
    designStyle: IconDesignStyle = IconDesignStyle.COLOROS_15,
    onClick: () -> Unit
) {
    val shape = when (iconShape) {
        IconShape.SQUIRCLE -> RoundedCornerShape(10.dp)
        IconShape.ROUNDED_SQUARE -> RoundedCornerShape(8.dp)
        IconShape.CIRCLE -> CircleShape
        IconShape.PEBBLE -> RoundedCornerShape(12.dp)
    }

    val backgroundBrush = when (designStyle) {
        IconDesignStyle.PURE_BLACK -> Brush.verticalGradient(listOf(Color(0xFF0A0A0A), Color(0xFF000000)))
        IconDesignStyle.PURE_WHITE -> Brush.verticalGradient(listOf(Color(0xFFFFFFFF), Color(0xFFF1F5F9)))
        IconDesignStyle.GLASSY -> Brush.linearGradient(listOf(Color(0x55FFFFFF), Color(0x22FFFFFF)))
        IconDesignStyle.COLOROS_16 -> Brush.sweepGradient(listOf(app.primaryColor, Color(0xFF00F0FF), Color(0xFF7000FF), app.primaryColor))
        else -> Brush.linearGradient(listOf(app.primaryColor.copy(alpha = 0.95f), app.primaryColor))
    }

    val glyphTint = when (designStyle) {
        IconDesignStyle.PURE_BLACK -> Color.White
        IconDesignStyle.PURE_WHITE -> Color(0xFF0F172A)
        IconDesignStyle.GLASSY -> app.primaryColor
        else -> Color.White
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(34.dp)
            .shadow(elevation = 2.dp, shape = shape)
            .clip(shape)
            .background(backgroundBrush)
            .border(0.5.dp, Color(0x40FFFFFF), shape)
            .clickable { onClick() }
    ) {
        ColorOSVectorIcon(
            iconKey = app.iconKey,
            size = 20.dp,
            tint = glyphTint
        )
    }
}

@Composable
fun ExpandedFolderModal(
    folder: FolderModel,
    iconShape: IconShape,
    accentColor: Color,
    designStyle: IconDesignStyle = IconDesignStyle.COLOROS_15,
    onDismiss: () -> Unit,
    onAppClick: (AppModel) -> Unit,
    onRenameFolder: (String) -> Unit,
    onToggleSize: () -> Unit
) {
    var isEditingTitle by remember { mutableStateOf(false) }
    var currentTitle by remember { mutableStateOf(folder.title) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xB3000000))
            .clickable { onDismiss() },
        contentAlignment = Alignment.Center
    ) {
        FrostedGlassCard(
            shape = RoundedCornerShape(32.dp),
            backgroundColor = Color(0xD9101828),
            borderColor = Color(0x66FFFFFF),
            borderWidth = 1.dp,
            modifier = Modifier
                .fillMaxWidth(0.90f)
                .fillMaxHeight(0.68f)
                .clickable(enabled = false) {}
                .padding(4.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp)
            ) {
                // Folder Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (isEditingTitle) {
                        OutlinedTextField(
                            value = currentTitle,
                            onValueChange = { currentTitle = it },
                            singleLine = true,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = Color.White,
                                unfocusedTextColor = Color.White,
                                focusedBorderColor = accentColor,
                                unfocusedBorderColor = Color.White.copy(alpha = 0.5f)
                            ),
                            textStyle = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold),
                            modifier = Modifier.weight(1f)
                        )
                        IconButton(onClick = {
                            if (currentTitle.isNotBlank()) onRenameFolder(currentTitle)
                            isEditingTitle = false
                        }) {
                            Icon(Icons.Rounded.Close, contentDescription = "Done", tint = accentColor)
                        }
                    } else {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .weight(1f)
                                .clickable { isEditingTitle = true }
                        ) {
                            Text(
                                text = folder.title,
                                style = TextStyle(
                                    color = Color.White,
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Icon(
                                imageVector = Icons.Rounded.Edit,
                                contentDescription = "Rename",
                                tint = Color.White.copy(alpha = 0.6f),
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        TextButton(onClick = onToggleSize) {
                            Text(
                                text = if (folder.isLarge) "2×2 Large" else "1×1 Standard",
                                color = accentColor,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }

                        IconButton(onClick = onDismiss) {
                            Icon(Icons.Rounded.Close, contentDescription = "Close", tint = Color.White)
                        }
                    }
                }

                Text(
                    text = "${folder.apps.size} applications",
                    color = Color.White.copy(alpha = 0.6f),
                    fontSize = 12.sp,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                // Apps Grid
                LazyVerticalGrid(
                    columns = GridCells.Fixed(4),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(folder.apps) { app ->
                        AppIconView(
                            app = app,
                            iconShape = iconShape,
                            iconScale = 1.0f,
                            showLabel = true,
                            designStyle = designStyle,
                            onClick = {
                                onAppClick(app)
                                onDismiss()
                            }
                        )
                    }
                }
            }
        }
    }
}
