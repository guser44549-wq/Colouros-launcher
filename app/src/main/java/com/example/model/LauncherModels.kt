package com.example.model

import android.graphics.drawable.Drawable
import androidx.compose.ui.graphics.Color

enum class AppCategory(val title: String) {
    ALL("All"),
    SOCIAL("Social"),
    TOOLS("Tools"),
    MEDIA("Media"),
    GAMES("Games"),
    PRODUCTIVITY("Productivity")
}

enum class IconShape(val label: String, val cornerPercent: Int) {
    SQUIRCLE("Squircle", 28),
    ROUNDED_SQUARE("Rounded", 20),
    CIRCLE("Circle", 50),
    PEBBLE("Pebble", 38)
}

enum class IconDesignStyle(val title: String, val subtitle: String) {
    NORMAL("Normal", "System Standard Style"),
    COLOROS_15("High ColorOS 15", "Official Aquamorphic 3D Depth"),
    COLOROS_16("ColorOS 16", "Next-Gen Neon Fluid & Glow"),
    CLASSIC("Classic", "Heritage Flat Retro Silhouette"),
    GLASSY("Glassy", "Ultra Frosted Glassmorphism"),
    PURE_BLACK("Pure Black", "OLED Stealth Absolute Black"),
    PURE_WHITE("Pure White", "Ceramic Luxury Minimalist")
}

enum class IconBadgeStyle(val title: String, val subtitle: String) {
    NUMBER("Number Count", "Shows unread numbers e.g. 2, 4"),
    DOT("Minimal Dot", "Sleek glowing dot on corner"),
    GLOW("Aquamorphic Glow", "Luminous halo ring around icon"),
    NONE("Off", "Clean icons without badges")
}

enum class SwipeDownGesture(val title: String, val subtitle: String) {
    NOTIFICATIONS("Notifications", "ColorOS 15 Notification & Quick Settings"),
    SEARCH("Global Search", "ColorOS Universal Search & Finder")
}

enum class WallpaperType(val title: String) {
    AQUAMORPHIC_WAVE("Fluid Wave"),
    AURORA("Aurora Silk"),
    MIDNIGHT("Midnight Deep"),
    SUNSET("Sunset Glow")
}

enum class LauncherWidgetType(val title: String, val defaultSpanX: Int, val defaultSpanY: Int) {
    WEATHER("Live Weather", 4, 2),
    HEALTH_STEPS("Health Ring", 2, 2),
    MUSIC("Fluid Music", 2, 2),
    QUICK_TOOLS("Quick Tools", 4, 1),
    BATTERY_STORAGE("Device Status", 4, 1)
}

data class AppModel(
    val id: String,
    val label: String,
    val packageName: String,
    val className: String = "",
    val category: AppCategory = AppCategory.TOOLS,
    val iconKey: String = "",
    val isRealApp: Boolean = false,
    val iconDrawable: Drawable? = null,
    val primaryColor: Color = Color(0xFF00B4D8)
)

data class FolderModel(
    val id: Long,
    val title: String,
    val isLarge: Boolean, // True = 2x2 interactive folder
    val page: Int,
    val cellX: Int,
    val cellY: Int,
    val apps: List<AppModel> = emptyList()
)

sealed class DesktopGridItem {
    data class AppCell(
        val dbId: Long,
        val page: Int,
        val cellX: Int,
        val cellY: Int,
        val app: AppModel
    ) : DesktopGridItem()

    data class FolderCell(
        val dbId: Long,
        val folder: FolderModel
    ) : DesktopGridItem()

    data class WidgetCell(
        val dbId: Long,
        val page: Int,
        val cellX: Int,
        val cellY: Int,
        val spanX: Int,
        val spanY: Int,
        val widgetType: LauncherWidgetType
    ) : DesktopGridItem()
}
