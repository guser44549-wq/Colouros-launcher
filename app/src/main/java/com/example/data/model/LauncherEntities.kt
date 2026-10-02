package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "desktop_items")
data class DesktopItemEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val page: Int = 0,
    val cellX: Int = 0,
    val cellY: Int = 0,
    val spanX: Int = 1,
    val spanY: Int = 1,
    val itemType: String = "APP", // "APP", "LARGE_FOLDER", "FOLDER", "WIDGET"
    val title: String = "",
    val packageName: String = "",
    val className: String = "",
    val iconKey: String = "",
    val folderId: Long? = null,
    val widgetType: String? = null
)

@Entity(tableName = "folders")
data class FolderEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String = "Folder",
    val isLarge: Boolean = true, // 2x2 interactive folder
    val page: Int = 0,
    val cellX: Int = 0,
    val cellY: Int = 0
)

@Entity(tableName = "launcher_config")
data class LauncherConfigEntity(
    @PrimaryKey val id: Int = 1,
    val gridCols: Int = 4,
    val gridRows: Int = 6,
    val iconShape: String = "SQUIRCLE",
    val iconScale: Float = 1.0f,
    val showLabels: Boolean = true,
    val wallpaperType: String = "AQUAMORPHIC_WAVE",
    val accentColorName: String = "OCEAN",
    val isLayoutLocked: Boolean = false,
    val isDarkTheme: Boolean = true,
    val smartSidebarEnabled: Boolean = true,
    val shelfSwipeEnabled: Boolean = true,
    val iconDesignStyle: String = "COLOROS_15",
    val iconBadgeStyle: String = "NUMBER",
    val swipeDownAction: String = "NOTIFICATIONS"
)
