package com.example.ui.editmode

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.IconDesignStyle
import com.example.model.IconShape
import com.example.model.LauncherWidgetType
import com.example.model.WallpaperType
import com.example.ui.components.AppIconView
import com.example.ui.components.FrostedGlassCard
import com.example.ui.theme.AccentThemeOption
import com.example.util.AppCatalogHelper

enum class EditModeTab(val title: String, val icon: ImageVector) {
    WALLPAPERS("Wallpaper", Icons.Rounded.Wallpaper),
    ICONS("Icons", Icons.Rounded.AppShortcut),
    GRID("Grid", Icons.Rounded.GridView),
    WIDGETS("Widgets", Icons.Rounded.Widgets),
    SETTINGS("Settings", Icons.Rounded.Tune)
}

@Composable
fun DesktopEditModal(
    selectedWallpaper: WallpaperType,
    onSelectWallpaper: (WallpaperType) -> Unit,
    selectedIconStyle: IconDesignStyle,
    onSelectIconStyle: (IconDesignStyle) -> Unit,
    selectedIconShape: IconShape,
    onSelectIconShape: (IconShape) -> Unit,
    iconScale: Float,
    onChangeIconScale: (Float) -> Unit,
    showLabels: Boolean,
    onToggleShowLabels: (Boolean) -> Unit,
    gridCols: Int,
    gridRows: Int,
    onChangeGrid: (cols: Int, rows: Int) -> Unit,
    accentOption: AccentThemeOption,
    onSelectAccent: (AccentThemeOption) -> Unit,
    isLayoutLocked: Boolean,
    onToggleLayoutLocked: (Boolean) -> Unit,
    isDarkTheme: Boolean,
    onToggleDarkTheme: (Boolean) -> Unit,
    onAddWidget: (LauncherWidgetType) -> Unit,
    onOpenThemeStore: () -> Unit,
    onDone: () -> Unit,
    modifier: Modifier = Modifier
) {
    var activeTab by remember { mutableStateOf(EditModeTab.ICONS) }

    Box(
        modifier = modifier
            .testTag("desktop_edit_modal")
            .fillMaxWidth()
            .navigationBarsPadding()
            .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
        FrostedGlassCard(
            shape = RoundedCornerShape(30.dp),
            backgroundColor = Color(0xF20F172A),
            borderColor = Color(0x6670B6FF),
            borderWidth = 1.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                // Header with "Done" button and "Theme Store" shortcut
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "Launcher Settings",
                            color = Color.White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(Color(0x33FFFFFF))
                                .clickable { onOpenThemeStore() }
                                .padding(horizontal = 8.dp, vertical = 3.dp)
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Rounded.Palette,
                                    contentDescription = null,
                                    tint = accentOption.primary,
                                    modifier = Modifier.size(12.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "Theme App",
                                    color = accentOption.primary,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }

                    Button(
                        onClick = onDone,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = accentOption.primary,
                            contentColor = Color.Black
                        ),
                        shape = RoundedCornerShape(14.dp),
                        contentPadding = PaddingValues(horizontal = 14.dp, vertical = 4.dp),
                        modifier = Modifier.height(30.dp)
                    ) {
                        Text(text = "Done", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Content Area by Tab
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(235.dp)
                ) {
                    when (activeTab) {
                        EditModeTab.ICONS -> {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .verticalScroll(rememberScrollState()),
                                verticalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                // Live Interactive Preview of Currently Selected Icon Style & Shape
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clip(RoundedCornerShape(14.dp))
                                        .background(Color(0x38000000))
                                        .border(1.dp, Color(0x26FFFFFF), RoundedCornerShape(14.dp))
                                        .padding(horizontal = 8.dp, vertical = 6.dp),
                                    horizontalArrangement = Arrangement.SpaceAround,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    AppCatalogHelper.SYSTEM_APP_PRESETS.take(4).forEach { sampleApp ->
                                        AppIconView(
                                            app = sampleApp,
                                            iconShape = selectedIconShape,
                                            iconScale = 0.82f,
                                            showLabel = false,
                                            designStyle = selectedIconStyle
                                        )
                                    }
                                }

                                // Icon Design Style Selector (Normal, High ColorOS 15, ColorOS 16, Classic, Glassy, Pure Black, Pure White)
                                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = "Icon Design Style",
                                            color = Color.White,
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.SemiBold
                                        )
                                        Text(
                                            text = selectedIconStyle.title,
                                            color = accentOption.primary,
                                            fontSize = 11.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }

                                    // First row of icon styles
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                                    ) {
                                        IconDesignStyle.values().take(4).forEach { style ->
                                            val isSelected = style == selectedIconStyle
                                            Box(
                                                modifier = Modifier
                                                    .weight(1f)
                                                    .clip(RoundedCornerShape(8.dp))
                                                    .background(if (isSelected) accentOption.primary else Color(0x33FFFFFF))
                                                    .border(
                                                        width = if (isSelected) 1.dp else 0.dp,
                                                        color = if (isSelected) Color.White.copy(alpha = 0.6f) else Color.Transparent,
                                                        shape = RoundedCornerShape(8.dp)
                                                    )
                                                    .clickable { onSelectIconStyle(style) }
                                                    .padding(vertical = 6.dp),
                                                contentAlignment = Alignment.Center
                                            ) {
                                                Text(
                                                    text = style.title,
                                                    color = if (isSelected) Color.Black else Color.White,
                                                    fontSize = 10.sp,
                                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                                    maxLines = 1
                                                )
                                            }
                                        }
                                    }

                                    // Second row of icon styles
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                                    ) {
                                        IconDesignStyle.values().drop(4).forEach { style ->
                                            val isSelected = style == selectedIconStyle
                                            Box(
                                                modifier = Modifier
                                                    .weight(1f)
                                                    .clip(RoundedCornerShape(8.dp))
                                                    .background(if (isSelected) accentOption.primary else Color(0x33FFFFFF))
                                                    .border(
                                                        width = if (isSelected) 1.dp else 0.dp,
                                                        color = if (isSelected) Color.White.copy(alpha = 0.6f) else Color.Transparent,
                                                        shape = RoundedCornerShape(8.dp)
                                                    )
                                                    .clickable { onSelectIconStyle(style) }
                                                    .padding(vertical = 6.dp),
                                                contentAlignment = Alignment.Center
                                            ) {
                                                Text(
                                                    text = style.title,
                                                    color = if (isSelected) Color.Black else Color.White,
                                                    fontSize = 10.sp,
                                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                                    maxLines = 1
                                                )
                                            }
                                        }
                                    }

                                    Text(
                                        text = selectedIconStyle.subtitle,
                                        color = Color.White.copy(alpha = 0.55f),
                                        fontSize = 10.sp,
                                        modifier = Modifier.padding(start = 2.dp, top = 2.dp)
                                    )
                                }

                                // Shape row
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(text = "Icon Shape", color = Color.White, fontSize = 12.sp)
                                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                        IconShape.values().forEach { shape ->
                                            val isSelected = shape == selectedIconShape
                                            Box(
                                                modifier = Modifier
                                                    .clip(RoundedCornerShape(8.dp))
                                                    .background(if (isSelected) accentOption.primary else Color(0x33FFFFFF))
                                                    .clickable { onSelectIconShape(shape) }
                                                    .padding(horizontal = 8.dp, vertical = 4.dp)
                                            ) {
                                                Text(
                                                    text = shape.label,
                                                    color = if (isSelected) Color.Black else Color.White,
                                                    fontSize = 11.sp,
                                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                                )
                                            }
                                        }
                                    }
                                }

                                // Scale row
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(text = "Icon Size", color = Color.White, fontSize = 12.sp)
                                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                        listOf(0.85f to "Small", 1.0f to "Medium", 1.15f to "Large").forEach { (scale, label) ->
                                            val isSelected = (iconScale - scale) in -0.05f..0.05f
                                            Box(
                                                modifier = Modifier
                                                    .clip(RoundedCornerShape(8.dp))
                                                    .background(if (isSelected) accentOption.primary else Color(0x33FFFFFF))
                                                    .clickable { onChangeIconScale(scale) }
                                                    .padding(horizontal = 10.dp, vertical = 4.dp)
                                            ) {
                                                Text(
                                                    text = label,
                                                    color = if (isSelected) Color.Black else Color.White,
                                                    fontSize = 11.sp,
                                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                                )
                                            }
                                        }
                                    }
                                }

                                // Labels toggle
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(text = "App Names", color = Color.White, fontSize = 12.sp)
                                    Switch(
                                        checked = showLabels,
                                        onCheckedChange = onToggleShowLabels,
                                        colors = SwitchDefaults.colors(checkedThumbColor = accentOption.primary)
                                    )
                                }
                            }
                        }

                        EditModeTab.WALLPAPERS -> {
                            Column(
                                modifier = Modifier.fillMaxSize(),
                                verticalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .weight(1f),
                                    horizontalArrangement = Arrangement.SpaceEvenly,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    WallpaperType.values().forEach { wp ->
                                        val isSelected = wp == selectedWallpaper
                                        Column(
                                            horizontalAlignment = Alignment.CenterHorizontally,
                                            modifier = Modifier
                                                .clip(RoundedCornerShape(12.dp))
                                                .clickable { onSelectWallpaper(wp) }
                                                .padding(2.dp)
                                        ) {
                                            Box(
                                                modifier = Modifier
                                                    .width(58.dp)
                                                    .height(80.dp)
                                                .clip(RoundedCornerShape(12.dp))
                                                .border(
                                                    if (isSelected) 3.dp else 1.dp,
                                                    if (isSelected) accentOption.primary else Color.White.copy(alpha = 0.4f),
                                                    RoundedCornerShape(12.dp)
                                                )
                                                .background(
                                                    when (wp) {
                                                        WallpaperType.AQUAMORPHIC_WAVE -> Color(0xFF0078FE)
                                                        WallpaperType.AURORA -> Color(0xFF0D3B4C)
                                                        WallpaperType.MIDNIGHT -> Color(0xFF0F172A)
                                                        WallpaperType.SUNSET -> Color(0xFF6B117A)
                                                    }
                                                )
                                        )
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Text(
                                            text = wp.title,
                                            color = if (isSelected) accentOption.primary else Color.White.copy(alpha = 0.8f),
                                            fontSize = 11.sp,
                                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                        )
                                    }
                                }
                            }

                            Button(
                                onClick = onOpenThemeStore,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(36.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(0x33FFFFFF),
                                    contentColor = Color.White
                                ),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Icon(Icons.Rounded.Palette, contentDescription = null, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(text = "Open Theme Store for More Wallpapers", fontSize = 11.sp)
                            }
                        }
                    }

                        EditModeTab.GRID -> {
                            Row(
                                modifier = Modifier.fillMaxSize(),
                                horizontalArrangement = Arrangement.SpaceEvenly,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                listOf(
                                    Triple(4, 6, "4×6 Standard"),
                                    Triple(5, 6, "5×6 Compact"),
                                    Triple(4, 5, "4×5 Relaxed")
                                ).forEach { (cols, rows, label) ->
                                    val isSelected = gridCols == cols && gridRows == rows
                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(14.dp))
                                            .background(if (isSelected) accentOption.primary.copy(alpha = 0.2f) else Color(0x22FFFFFF))
                                            .border(
                                                if (isSelected) 2.dp else 1.dp,
                                                if (isSelected) accentOption.primary else Color.Transparent,
                                                RoundedCornerShape(14.dp)
                                            )
                                            .clickable { onChangeGrid(cols, rows) }
                                            .padding(horizontal = 16.dp, vertical = 14.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Rounded.Apps,
                                            contentDescription = null,
                                            tint = if (isSelected) accentOption.primary else Color.White,
                                            modifier = Modifier.size(28.dp)
                                        )
                                        Spacer(modifier = Modifier.height(6.dp))
                                        Text(
                                            text = label,
                                            color = Color.White,
                                            fontSize = 12.sp,
                                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                        )
                                    }
                                }
                            }
                        }

                        EditModeTab.WIDGETS -> {
                            Row(
                                modifier = Modifier.fillMaxSize(),
                                horizontalArrangement = Arrangement.SpaceEvenly,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                LauncherWidgetType.values().forEach { widgetType ->
                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(12.dp))
                                            .background(Color(0x2BFFFFFF))
                                            .clickable { onAddWidget(widgetType) }
                                            .padding(horizontal = 8.dp, vertical = 8.dp)
                                    ) {
                                        Icon(
                                            imageVector = when (widgetType) {
                                                LauncherWidgetType.WEATHER -> Icons.Rounded.WbSunny
                                                LauncherWidgetType.HEALTH_STEPS -> Icons.Rounded.DirectionsRun
                                                LauncherWidgetType.MUSIC -> Icons.Rounded.MusicNote
                                                LauncherWidgetType.QUICK_TOOLS -> Icons.Rounded.Build
                                                LauncherWidgetType.BATTERY_STORAGE -> Icons.Rounded.Speed
                                            },
                                            contentDescription = null,
                                            tint = accentOption.primary,
                                            modifier = Modifier.size(26.dp)
                                        )
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Text(
                                            text = widgetType.title,
                                            color = Color.White,
                                            fontSize = 10.sp,
                                            maxLines = 1
                                        )
                                        Text(
                                            text = "+ Add",
                                            color = accentOption.accent,
                                            fontSize = 9.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                }
                            }
                        }

                        EditModeTab.SETTINGS -> {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .verticalScroll(rememberScrollState()),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                // Accent Theme Row
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(text = "Accent Color", color = Color.White, fontSize = 12.sp)
                                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                        AccentThemeOption.values().forEach { opt ->
                                            val isSelected = opt == accentOption
                                            Box(
                                                modifier = Modifier
                                                    .size(22.dp)
                                                    .clip(CircleShape)
                                                    .background(opt.primary)
                                                    .border(
                                                        if (isSelected) 2.dp else 0.dp,
                                                        Color.White,
                                                        CircleShape
                                                    )
                                                    .clickable { onSelectAccent(opt) }
                                            )
                                        }
                                    }
                                }

                                // Lock desktop layout
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(text = "Lock Desktop Layout", color = Color.White, fontSize = 12.sp)
                                    Switch(
                                        checked = isLayoutLocked,
                                        onCheckedChange = onToggleLayoutLocked,
                                        colors = SwitchDefaults.colors(checkedThumbColor = accentOption.primary)
                                    )
                                }

                                // Dark Mode toggle
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(text = "Dark Aquamorphic Theme", color = Color.White, fontSize = 12.sp)
                                    Switch(
                                        checked = isDarkTheme,
                                        onCheckedChange = onToggleDarkTheme,
                                        colors = SwitchDefaults.colors(checkedThumbColor = accentOption.primary)
                                    )
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Bottom Tab Navigation Bar
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    EditModeTab.values().forEach { tab ->
                        val isSelected = tab == activeTab
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .clip(RoundedCornerShape(10.dp))
                                .clickable { activeTab = tab }
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Icon(
                                imageVector = tab.icon,
                                contentDescription = tab.title,
                                tint = if (isSelected) accentOption.primary else Color.White.copy(alpha = 0.6f),
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = tab.title,
                                color = if (isSelected) accentOption.primary else Color.White.copy(alpha = 0.6f),
                                fontSize = 10.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                            )
                        }
                    }
                }
            }
        }
    }
}
