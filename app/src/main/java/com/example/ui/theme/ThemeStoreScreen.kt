package com.example.ui.theme

import android.widget.Toast
import androidx.compose.animation.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.*
import com.example.ui.components.AppIconView
import com.example.ui.components.FrostedGlassCard
import com.example.util.AppCatalogHelper

enum class ThemeStoreTab(val title: String) {
    FEATURED_THEMES("Curated Themes"),
    ICON_DESIGNS("Icon Styles"),
    WALLPAPERS("Wallpapers"),
    ACCENT_PALETTES("Colors & Style")
}

data class CuratedThemeSuite(
    val id: String,
    val name: String,
    val tag: String,
    val description: String,
    val wallpaper: WallpaperType,
    val iconStyle: IconDesignStyle,
    val iconShape: IconShape,
    val accent: AccentThemeOption,
    val isDark: Boolean,
    val previewGradient: Brush
)

val CURATED_THEMES = listOf(
    CuratedThemeSuite(
        id = "coloros_15_official",
        name = "ColorOS 15 Aquamorphic",
        tag = "OFFICIAL",
        description = "Luminous fluid water ripples, high ColorOS 15 aquamorphic icons with subtle frosted depth.",
        wallpaper = WallpaperType.AQUAMORPHIC_WAVE,
        iconStyle = IconDesignStyle.COLOROS_15,
        iconShape = IconShape.SQUIRCLE,
        accent = AccentThemeOption.OCEAN,
        isDark = true,
        previewGradient = Brush.linearGradient(listOf(Color(0xFF0078FE), Color(0xFF00D2FF), Color(0xFF0A192F)))
    ),
    CuratedThemeSuite(
        id = "coloros_16_ultra",
        name = "ColorOS 16 Next-Gen",
        tag = "NEW",
        description = "Hyper-fluid neon gradients, holographic edge glow and futuristic fluid dynamism.",
        wallpaper = WallpaperType.AURORA,
        iconStyle = IconDesignStyle.COLOROS_16,
        iconShape = IconShape.SQUIRCLE,
        accent = AccentThemeOption.AMETHYST,
        isDark = true,
        previewGradient = Brush.linearGradient(listOf(Color(0xFF7000FF), Color(0xFF00F0FF), Color(0xFF1B0B38)))
    ),
    CuratedThemeSuite(
        id = "stealth_oled",
        name = "Pure Black OLED",
        tag = "BATTERY SAVER",
        description = "Deep absolute #000000 minimalist stealth aesthetic with crisp monochrome glyphs.",
        wallpaper = WallpaperType.MIDNIGHT,
        iconStyle = IconDesignStyle.PURE_BLACK,
        iconShape = IconShape.CIRCLE,
        accent = AccentThemeOption.SUNSET,
        isDark = true,
        previewGradient = Brush.linearGradient(listOf(Color(0xFF000000), Color(0xFF18181B), Color(0xFF000000)))
    ),
    CuratedThemeSuite(
        id = "glassy_hologram",
        name = "Glassy Frosted Horizon",
        tag = "AQUAMORPHIC",
        description = "Ultra-translucent frosted glass cards with holographic light reflections and glowing badges.",
        wallpaper = WallpaperType.AQUAMORPHIC_WAVE,
        iconStyle = IconDesignStyle.GLASSY,
        iconShape = IconShape.PEBBLE,
        accent = AccentThemeOption.OCEAN,
        isDark = true,
        previewGradient = Brush.linearGradient(listOf(Color(0x80FFFFFF), Color(0x3300B4D8), Color(0x800078FE)))
    ),
    CuratedThemeSuite(
        id = "ceramic_white",
        name = "Ceramic Pure White",
        tag = "MINIMAL",
        description = "Bright pearl ceramic surfaces with deep obsidian typography and pure white icons.",
        wallpaper = WallpaperType.AQUAMORPHIC_WAVE,
        iconStyle = IconDesignStyle.PURE_WHITE,
        iconShape = IconShape.ROUNDED_SQUARE,
        accent = AccentThemeOption.EMERALD,
        isDark = false,
        previewGradient = Brush.linearGradient(listOf(Color(0xFFFFFFFF), Color(0xFFE2E8F0), Color(0xFFCBD5E1)))
    ),
    CuratedThemeSuite(
        id = "classic_heritage",
        name = "ColorOS Classic Flat",
        tag = "RETRO",
        description = "Timeless heritage flat icons with bold saturated hues and clean borders.",
        wallpaper = WallpaperType.SUNSET,
        iconStyle = IconDesignStyle.CLASSIC,
        iconShape = IconShape.ROUNDED_SQUARE,
        accent = AccentThemeOption.SUNSET,
        isDark = true,
        previewGradient = Brush.linearGradient(listOf(Color(0xFFFF5E62), Color(0xFFFF8C00), Color(0xFF4A0E4E)))
    )
)

@Composable
fun ThemeStoreScreen(
    currentWallpaper: WallpaperType,
    onSelectWallpaper: (WallpaperType) -> Unit,
    currentIconStyle: IconDesignStyle,
    onSelectIconStyle: (IconDesignStyle) -> Unit,
    currentIconShape: IconShape,
    onSelectIconShape: (IconShape) -> Unit,
    currentIconScale: Float,
    onChangeIconScale: (Float) -> Unit,
    currentAccent: AccentThemeOption,
    onSelectAccent: (AccentThemeOption) -> Unit,
    isDarkTheme: Boolean,
    onToggleDarkTheme: (Boolean) -> Unit,
    onClose: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var selectedTab by remember { mutableStateOf(ThemeStoreTab.FEATURED_THEMES) }

    val sampleApps = remember {
        listOf(
            AppCatalogHelper.SYSTEM_APP_PRESETS[0], // Phone
            AppCatalogHelper.SYSTEM_APP_PRESETS[1], // Messages
            AppCatalogHelper.SYSTEM_APP_PRESETS[3], // Camera
            AppCatalogHelper.SYSTEM_APP_PRESETS[4]  // Photos
        )
    }

    Box(
        modifier = modifier
            .testTag("theme_store_screen")
            .fillMaxSize()
            .background(Color(0xF0080E18))
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // App Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 18.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(38.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(currentAccent.primary),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Palette,
                            contentDescription = null,
                            tint = Color.Black,
                            modifier = Modifier.size(22.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = "Theme Store",
                            color = Color.White,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "ColorOS 15 Personalization",
                            color = Color.White.copy(alpha = 0.6f),
                            fontSize = 11.sp
                        )
                    }
                }

                IconButton(
                    onClick = onClose,
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(Color(0x33FFFFFF))
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Close,
                        contentDescription = "Close",
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            // Tabs Row
            ScrollableTabRow(
                selectedTabIndex = ThemeStoreTab.values().indexOf(selectedTab),
                edgePadding = 16.dp,
                containerColor = Color.Transparent,
                divider = {},
                indicator = {},
                modifier = Modifier.fillMaxWidth()
            ) {
                ThemeStoreTab.values().forEach { tab ->
                    val isSelected = tab == selectedTab
                    Box(
                        modifier = Modifier
                            .padding(end = 8.dp, bottom = 8.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(if (isSelected) currentAccent.primary else Color(0x22FFFFFF))
                            .clickable { selectedTab = tab }
                            .padding(horizontal = 14.dp, vertical = 8.dp)
                    ) {
                        Text(
                            text = tab.title,
                            color = if (isSelected) Color.Black else Color.White,
                            fontSize = 12.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                        )
                    }
                }
            }

            // Tab Content
            when (selectedTab) {
                ThemeStoreTab.FEATURED_THEMES -> {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(14.dp),
                        contentPadding = PaddingValues(vertical = 12.dp)
                    ) {
                        items(CURATED_THEMES) { suite ->
                            CuratedThemeCard(
                                suite = suite,
                                currentWallpaper = currentWallpaper,
                                currentIconStyle = currentIconStyle,
                                onApply = {
                                    onSelectWallpaper(suite.wallpaper)
                                    onSelectIconStyle(suite.iconStyle)
                                    onSelectIconShape(suite.iconShape)
                                    onSelectAccent(suite.accent)
                                    onToggleDarkTheme(suite.isDark)
                                    Toast.makeText(context, "Applied ${suite.name} theme!", Toast.LENGTH_SHORT).show()
                                }
                            )
                        }
                    }
                }

                ThemeStoreTab.ICON_DESIGNS -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        // Live Icon Preview Box
                        FrostedGlassCard(
                            shape = RoundedCornerShape(26.dp),
                            backgroundColor = Color(0x38192636),
                            borderColor = Color(0x6670B6FF),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp)
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "LIVE ICON PREVIEW · ${currentIconStyle.title}",
                                    color = currentAccent.primary,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Spacer(modifier = Modifier.height(14.dp))
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceAround
                                ) {
                                    sampleApps.forEach { app ->
                                        AppIconView(
                                            app = app,
                                            iconShape = currentIconShape,
                                            iconScale = currentIconScale,
                                            showLabel = true,
                                            designStyle = currentIconStyle
                                        )
                                    }
                                }
                            }
                        }

                        // Icon Design Styles List
                        Text(
                            text = "CHOOSE ICON STYLE",
                            color = Color.White.copy(alpha = 0.7f),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )

                        LazyColumn(
                            verticalArrangement = Arrangement.spacedBy(10.dp),
                            modifier = Modifier.weight(1f),
                            contentPadding = PaddingValues(bottom = 16.dp)
                        ) {
                            items(IconDesignStyle.values()) { style ->
                                val isSelected = style == currentIconStyle
                                FrostedGlassCard(
                                    shape = RoundedCornerShape(18.dp),
                                    backgroundColor = if (isSelected) currentAccent.primary.copy(alpha = 0.2f) else Color(0x22FFFFFF),
                                    borderColor = if (isSelected) currentAccent.primary else Color(0x33FFFFFF),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable {
                                            onSelectIconStyle(style)
                                            Toast.makeText(context, "${style.title} icons selected", Toast.LENGTH_SHORT).show()
                                        }
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(horizontal = 14.dp, vertical = 12.dp),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Column(modifier = Modifier.weight(1f)) {
                                            Text(
                                                text = style.title,
                                                color = Color.White,
                                                fontSize = 15.sp,
                                                fontWeight = FontWeight.Bold
                                            )
                                            Text(
                                                text = style.subtitle,
                                                color = Color.White.copy(alpha = 0.6f),
                                                fontSize = 12.sp
                                            )
                                        }

                                        if (isSelected) {
                                            Icon(
                                                imageVector = Icons.Rounded.CheckCircle,
                                                contentDescription = "Selected",
                                                tint = currentAccent.primary,
                                                modifier = Modifier.size(24.dp)
                                            )
                                        } else {
                                            TextButton(onClick = { onSelectIconStyle(style) }) {
                                                Text(text = "Apply", color = Color.White, fontSize = 12.sp)
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                ThemeStoreTab.WALLPAPERS -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Text(
                            text = "COLOROS 15 AQUAMORPHIC WALLPAPERS",
                            color = Color.White.copy(alpha = 0.7f),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )

                        LazyColumn(
                            verticalArrangement = Arrangement.spacedBy(14.dp),
                            modifier = Modifier.fillMaxSize()
                        ) {
                            items(WallpaperType.values()) { wp ->
                                val isSelected = wp == currentWallpaper
                                FrostedGlassCard(
                                    shape = RoundedCornerShape(22.dp),
                                    backgroundColor = Color(0x33192636),
                                    borderColor = if (isSelected) currentAccent.primary else Color(0x33FFFFFF),
                                    borderWidth = if (isSelected) 2.dp else 1.dp,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable {
                                            onSelectWallpaper(wp)
                                            Toast.makeText(context, "Wallpaper set to ${wp.title}", Toast.LENGTH_SHORT).show()
                                        }
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(12.dp),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        val resId = remember(wp) {
                                            when (wp) {
                                                WallpaperType.AQUAMORPHIC_WAVE -> context.resources.getIdentifier("coloros_aquamorphic_wallpaper_1790852018513", "drawable", context.packageName)
                                                WallpaperType.AURORA -> context.resources.getIdentifier("coloros_aurora_wp_1790853780811", "drawable", context.packageName)
                                                WallpaperType.MIDNIGHT -> context.resources.getIdentifier("coloros_midnight_wp_1790853795025", "drawable", context.packageName)
                                                WallpaperType.SUNSET -> context.resources.getIdentifier("coloros_sunset_wp_1790853812088", "drawable", context.packageName)
                                            }
                                        }

                                        Box(
                                            modifier = Modifier
                                                .size(width = 72.dp, height = 96.dp)
                                                .clip(RoundedCornerShape(14.dp))
                                                .background(
                                                    when (wp) {
                                                        WallpaperType.AQUAMORPHIC_WAVE -> Color(0xFF0078FE)
                                                        WallpaperType.AURORA -> Color(0xFF0D3B4C)
                                                        WallpaperType.MIDNIGHT -> Color(0xFF0A0E1A)
                                                        WallpaperType.SUNSET -> Color(0xFF6B117A)
                                                    }
                                                )
                                        ) {
                                            if (resId != 0) {
                                                Image(
                                                    painter = painterResource(id = resId),
                                                    contentDescription = wp.title,
                                                    contentScale = ContentScale.Crop,
                                                    modifier = Modifier.fillMaxSize()
                                                )
                                            }
                                        }

                                        Column(
                                            modifier = Modifier
                                                .weight(1f)
                                                .padding(horizontal = 14.dp)
                                        ) {
                                            Text(
                                                text = wp.title,
                                                color = Color.White,
                                                fontSize = 16.sp,
                                                fontWeight = FontWeight.Bold
                                            )
                                            Text(
                                                text = when (wp) {
                                                    WallpaperType.AQUAMORPHIC_WAVE -> "Signature ColorOS 15 liquid ripples"
                                                    WallpaperType.AURORA -> "Deep cosmic teal with glowing wave"
                                                    WallpaperType.MIDNIGHT -> "Pure OLED black with subtle sheen"
                                                    WallpaperType.SUNSET -> "Warm amber and purple horizon"
                                                },
                                                color = Color.White.copy(alpha = 0.6f),
                                                fontSize = 12.sp
                                            )
                                        }

                                        Button(
                                            onClick = { onSelectWallpaper(wp) },
                                            colors = ButtonDefaults.buttonColors(
                                                containerColor = if (isSelected) currentAccent.primary else Color(0x33FFFFFF),
                                                contentColor = if (isSelected) Color.Black else Color.White
                                            ),
                                            shape = RoundedCornerShape(12.dp),
                                            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                                        ) {
                                            Text(text = if (isSelected) "Active" else "Set", fontSize = 12.sp)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                ThemeStoreTab.ACCENT_PALETTES -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Text(
                            text = "SYSTEM ACCENT PALETTES",
                            color = Color.White.copy(alpha = 0.7f),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )

                        AccentThemeOption.values().forEach { opt ->
                            val isSelected = opt == currentAccent
                            FrostedGlassCard(
                                shape = RoundedCornerShape(18.dp),
                                backgroundColor = if (isSelected) opt.primary.copy(alpha = 0.2f) else Color(0x22FFFFFF),
                                borderColor = if (isSelected) opt.primary else Color(0x33FFFFFF),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { onSelectAccent(opt) }
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(14.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Box(
                                            modifier = Modifier
                                                .size(32.dp)
                                                .clip(CircleShape)
                                                .background(opt.primary)
                                        )
                                        Spacer(modifier = Modifier.width(12.dp))
                                        Text(
                                            text = opt.displayName,
                                            color = Color.White,
                                            fontSize = 15.sp,
                                            fontWeight = FontWeight.SemiBold
                                        )
                                    }

                                    if (isSelected) {
                                        Icon(
                                            imageVector = Icons.Rounded.CheckCircle,
                                            contentDescription = "Selected",
                                            tint = opt.primary,
                                            modifier = Modifier.size(24.dp)
                                        )
                                    }
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        // Dark Theme Switcher
                        FrostedGlassCard(
                            shape = RoundedCornerShape(18.dp),
                            backgroundColor = Color(0x22FFFFFF),
                            borderColor = Color(0x33FFFFFF),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(14.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column {
                                    Text(
                                        text = "Dark Aquamorphic Surface",
                                        color = Color.White,
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                    Text(
                                        text = "Toggle dark/light glass surfaces",
                                        color = Color.White.copy(alpha = 0.6f),
                                        fontSize = 11.sp
                                    )
                                }
                                Switch(
                                    checked = isDarkTheme,
                                    onCheckedChange = onToggleDarkTheme,
                                    colors = SwitchDefaults.colors(checkedThumbColor = currentAccent.primary)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun CuratedThemeCard(
    suite: CuratedThemeSuite,
    currentWallpaper: WallpaperType,
    currentIconStyle: IconDesignStyle,
    onApply: () -> Unit
) {
    val isCurrentlyActive = currentWallpaper == suite.wallpaper && currentIconStyle == suite.iconStyle

    FrostedGlassCard(
        shape = RoundedCornerShape(24.dp),
        backgroundColor = Color(0x3D162334),
        borderColor = if (isCurrentlyActive) suite.accent.primary else Color(0x40FFFFFF),
        borderWidth = if (isCurrentlyActive) 2.dp else 1.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Theme Card Banner
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(90.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(suite.previewGradient)
                    .padding(12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color(0x66000000))
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = suite.tag,
                            color = Color.White,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    if (isCurrentlyActive) {
                        Box(
                            modifier = Modifier
                                .clip(CircleShape)
                                .background(Color.White)
                                .padding(horizontal = 8.dp, vertical = 2.dp)
                        ) {
                            Text(text = "CURRENT", color = Color.Black, fontSize = 9.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = suite.name,
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = suite.description,
                        color = Color.White.copy(alpha = 0.65f),
                        fontSize = 12.sp,
                        maxLines = 2
                    )
                }

                Spacer(modifier = Modifier.width(10.dp))

                Button(
                    onClick = onApply,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = suite.accent.primary,
                        contentColor = Color.Black
                    ),
                    shape = RoundedCornerShape(14.dp),
                    contentPadding = PaddingValues(horizontal = 14.dp, vertical = 6.dp)
                ) {
                    Text(text = if (isCurrentlyActive) "Applied" else "Apply", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}
