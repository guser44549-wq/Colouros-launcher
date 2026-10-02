package com.example.repository

import com.example.data.local.LauncherDao
import com.example.data.model.DesktopItemEntity
import com.example.data.model.FolderEntity
import com.example.data.model.LauncherConfigEntity
import kotlinx.coroutines.flow.Flow

class LauncherRepository(private val launcherDao: LauncherDao) {

    val rootDesktopItems: Flow<List<DesktopItemEntity>> = launcherDao.getRootDesktopItems()
    val folders: Flow<List<FolderEntity>> = launcherDao.getAllFolders()
    val launcherConfig: Flow<LauncherConfigEntity?> = launcherDao.getLauncherConfig()

    fun getItemsInFolder(folderId: Long): Flow<List<DesktopItemEntity>> =
        launcherDao.getItemsInFolder(folderId)

    suspend fun getItemsInFolderSync(folderId: Long): List<DesktopItemEntity> =
        launcherDao.getItemsInFolderSync(folderId)

    suspend fun insertDesktopItem(item: DesktopItemEntity): Long =
        launcherDao.insertDesktopItem(item)

    suspend fun updateDesktopItem(item: DesktopItemEntity) =
        launcherDao.updateDesktopItem(item)

    suspend fun deleteDesktopItem(id: Long) =
        launcherDao.deleteDesktopItem(id)

    suspend fun insertFolder(folder: FolderEntity): Long =
        launcherDao.insertFolder(folder)

    suspend fun updateFolder(folder: FolderEntity) =
        launcherDao.updateFolder(folder)

    suspend fun deleteFolder(id: Long) {
        launcherDao.deleteItemsByFolderId(id)
        launcherDao.deleteFolder(id)
    }

    suspend fun updateLauncherConfig(config: LauncherConfigEntity) =
        launcherDao.setLauncherConfig(config)

    suspend fun ensureDefaultSetupSeeded() {
        val existingConfig = launcherDao.getLauncherConfigSync()
        if (existingConfig == null) {
            val defaultConfig = LauncherConfigEntity(
                id = 1,
                gridCols = 4,
                gridRows = 6,
                iconShape = "SQUIRCLE",
                iconScale = 1.0f,
                showLabels = true,
                wallpaperType = "AQUAMORPHIC_WAVE",
                accentColorName = "OCEAN",
                isLayoutLocked = false,
                isDarkTheme = true,
                smartSidebarEnabled = true,
                shelfSwipeEnabled = true
            )
            launcherDao.setLauncherConfig(defaultConfig)

            // Seed Page 0: Weather widget
            launcherDao.insertDesktopItem(
                DesktopItemEntity(
                    page = 0,
                    cellX = 0,
                    cellY = 0,
                    spanX = 4,
                    spanY = 2,
                    itemType = "WIDGET",
                    title = "Live Weather",
                    widgetType = "WEATHER"
                )
            )

            // Folder 1: "Social & Connect" (Large 2x2 interactive folder)
            val folder1Id = launcherDao.insertFolder(
                FolderEntity(
                    title = "Social & Fun",
                    isLarge = true,
                    page = 0,
                    cellX = 0,
                    cellY = 2
                )
            )
            // Items inside Folder 1
            launcherDao.insertDesktopItems(
                listOf(
                    DesktopItemEntity(page = 0, cellX = 0, cellY = 0, itemType = "APP", title = "Phone", packageName = "com.android.dialer", iconKey = "phone", folderId = folder1Id),
                    DesktopItemEntity(page = 0, cellX = 1, cellY = 0, itemType = "APP", title = "Messages", packageName = "com.google.android.apps.messaging", iconKey = "messages", folderId = folder1Id),
                    DesktopItemEntity(page = 0, cellX = 2, cellY = 0, itemType = "APP", title = "Browser", packageName = "com.android.chrome", iconKey = "browser", folderId = folder1Id),
                    DesktopItemEntity(page = 0, cellX = 0, cellY = 1, itemType = "APP", title = "Theme Store", packageName = "com.heytap.themestore", iconKey = "themes", folderId = folder1Id),
                    DesktopItemEntity(page = 0, cellX = 1, cellY = 1, itemType = "APP", title = "Games", packageName = "com.coloros.gamespace", iconKey = "games", folderId = folder1Id),
                    DesktopItemEntity(page = 0, cellX = 2, cellY = 1, itemType = "APP", title = "O-Health", packageName = "com.heytap.health", iconKey = "health", folderId = folder1Id)
                )
            )

            // Other apps on Page 0
            launcherDao.insertDesktopItem(DesktopItemEntity(page = 0, cellX = 2, cellY = 2, itemType = "APP", title = "Camera", packageName = "com.android.camera", iconKey = "camera"))
            launcherDao.insertDesktopItem(DesktopItemEntity(page = 0, cellX = 3, cellY = 2, itemType = "APP", title = "Photos", packageName = "com.coloros.gallery3d", iconKey = "photos"))
            launcherDao.insertDesktopItem(DesktopItemEntity(page = 0, cellX = 2, cellY = 3, itemType = "APP", title = "Settings", packageName = "com.android.settings", iconKey = "settings"))
            launcherDao.insertDesktopItem(DesktopItemEntity(page = 0, cellX = 3, cellY = 3, itemType = "APP", title = "Clock", packageName = "com.android.deskclock", iconKey = "clock"))

            // Folder 2: "Tools & Work" (Large 2x2 interactive folder)
            val folder2Id = launcherDao.insertFolder(
                FolderEntity(
                    title = "Tools & Office",
                    isLarge = true,
                    page = 0,
                    cellX = 0,
                    cellY = 4
                )
            )
            launcherDao.insertDesktopItems(
                listOf(
                    DesktopItemEntity(page = 0, cellX = 0, cellY = 0, itemType = "APP", title = "Notes", packageName = "com.coloros.note", iconKey = "notes", folderId = folder2Id),
                    DesktopItemEntity(page = 0, cellX = 1, cellY = 0, itemType = "APP", title = "Calculator", packageName = "com.android.calculator2", iconKey = "calculator", folderId = folder2Id),
                    DesktopItemEntity(page = 0, cellX = 2, cellY = 0, itemType = "APP", title = "Calendar", packageName = "com.android.calendar", iconKey = "calendar", folderId = folder2Id),
                    DesktopItemEntity(page = 0, cellX = 0, cellY = 1, itemType = "APP", title = "Files", packageName = "com.coloros.filemanager", iconKey = "files", folderId = folder2Id),
                    DesktopItemEntity(page = 0, cellX = 1, cellY = 1, itemType = "APP", title = "Recorder", packageName = "com.coloros.soundrecorder", iconKey = "recorder", folderId = folder2Id),
                    DesktopItemEntity(page = 0, cellX = 2, cellY = 1, itemType = "APP", title = "Compass", packageName = "com.coloros.compass", iconKey = "compass", folderId = folder2Id)
                )
            )

            launcherDao.insertDesktopItem(DesktopItemEntity(page = 0, cellX = 2, cellY = 4, itemType = "APP", title = "Music", packageName = "com.heytap.music", iconKey = "music"))
            launcherDao.insertDesktopItem(DesktopItemEntity(page = 0, cellX = 3, cellY = 4, itemType = "APP", title = "Recorder", packageName = "com.coloros.soundrecorder", iconKey = "recorder"))
            launcherDao.insertDesktopItem(DesktopItemEntity(page = 0, cellX = 2, cellY = 5, itemType = "APP", title = "Files", packageName = "com.coloros.filemanager", iconKey = "files"))
            launcherDao.insertDesktopItem(DesktopItemEntity(page = 0, cellX = 3, cellY = 5, itemType = "APP", title = "Theme Store", packageName = "com.heytap.themestore", iconKey = "themes"))

            // Seed Page 1: Music widget, Step counter widget, and Quick tools
            launcherDao.insertDesktopItem(DesktopItemEntity(page = 1, cellX = 0, cellY = 0, spanX = 2, spanY = 2, itemType = "WIDGET", title = "Music Player", widgetType = "MUSIC"))
            launcherDao.insertDesktopItem(DesktopItemEntity(page = 1, cellX = 2, cellY = 0, spanX = 2, spanY = 2, itemType = "WIDGET", title = "Health Steps", widgetType = "HEALTH_STEPS"))
            launcherDao.insertDesktopItem(DesktopItemEntity(page = 1, cellX = 0, cellY = 2, spanX = 4, spanY = 1, itemType = "WIDGET", title = "Quick Tools", widgetType = "QUICK_TOOLS"))
            launcherDao.insertDesktopItem(DesktopItemEntity(page = 1, cellX = 0, cellY = 3, spanX = 4, spanY = 1, itemType = "WIDGET", title = "Device Status", widgetType = "BATTERY_STORAGE"))

            launcherDao.insertDesktopItem(DesktopItemEntity(page = 1, cellX = 0, cellY = 4, itemType = "APP", title = "Calendar", packageName = "com.android.calendar", iconKey = "calendar"))
            launcherDao.insertDesktopItem(DesktopItemEntity(page = 1, cellX = 1, cellY = 4, itemType = "APP", title = "Calculator", packageName = "com.android.calculator2", iconKey = "calculator"))
            launcherDao.insertDesktopItem(DesktopItemEntity(page = 1, cellX = 2, cellY = 4, itemType = "APP", title = "Notes", packageName = "com.coloros.note", iconKey = "notes"))
            launcherDao.insertDesktopItem(DesktopItemEntity(page = 1, cellX = 3, cellY = 4, itemType = "APP", title = "Compass", packageName = "com.coloros.compass", iconKey = "compass"))
        }
    }
}
