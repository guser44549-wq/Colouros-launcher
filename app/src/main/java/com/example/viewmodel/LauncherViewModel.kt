package com.example.viewmodel

import android.app.Application
import android.content.Context
import android.hardware.camera2.CameraManager
import android.os.Build
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.local.LauncherDatabase
import com.example.data.model.DesktopItemEntity
import com.example.data.model.FolderEntity
import com.example.data.model.LauncherConfigEntity
import com.example.model.*
import com.example.repository.LauncherRepository
import com.example.ui.theme.AccentThemeOption
import com.example.util.AppCatalogHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class LauncherViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: LauncherRepository

    // Apps catalog
    private val _allApps = MutableStateFlow<List<AppModel>>(emptyList())
    val allApps: StateFlow<List<AppModel>> = _allApps.asStateFlow()

    // UI overlays state
    private val _isShelfOpen = MutableStateFlow(false)
    val isShelfOpen: StateFlow<Boolean> = _isShelfOpen.asStateFlow()

    private val _isDrawerOpen = MutableStateFlow(false)
    val isDrawerOpen: StateFlow<Boolean> = _isDrawerOpen.asStateFlow()

    private val _isEditModeOpen = MutableStateFlow(false)
    val isEditModeOpen: StateFlow<Boolean> = _isEditModeOpen.asStateFlow()

    private val _isGlobalSearchOpen = MutableStateFlow(false)
    val isGlobalSearchOpen: StateFlow<Boolean> = _isGlobalSearchOpen.asStateFlow()

    private val _isLockedSleep = MutableStateFlow(false)
    val isLockedSleep: StateFlow<Boolean> = _isLockedSleep.asStateFlow()

    private val _expandedFolderId = MutableStateFlow<Long?>(null)
    val expandedFolderId: StateFlow<Long?> = _expandedFolderId.asStateFlow()

    private val _isSidebarOpen = MutableStateFlow(false)
    val isSidebarOpen: StateFlow<Boolean> = _isSidebarOpen.asStateFlow()

    private val _isThemeStoreOpen = MutableStateFlow(false)
    val isThemeStoreOpen: StateFlow<Boolean> = _isThemeStoreOpen.asStateFlow()

    private val _isNotificationsOpen = MutableStateFlow(false)
    val isNotificationsOpen: StateFlow<Boolean> = _isNotificationsOpen.asStateFlow()

    private val _currentPage = MutableStateFlow(0)
    val currentPage: StateFlow<Int> = _currentPage.asStateFlow()

    // Interactive Widget States
    private val _isTorchOn = MutableStateFlow(false)
    val isTorchOn: StateFlow<Boolean> = _isTorchOn.asStateFlow()

    private val _currentSteps = MutableStateFlow(6842)
    val currentSteps: StateFlow<Int> = _currentSteps.asStateFlow()

    private val _isMusicPlaying = MutableStateFlow(true)
    val isMusicPlaying: StateFlow<Boolean> = _isMusicPlaying.asStateFlow()

    private val musicTracks = listOf(
        "Aquamorphic Horizon" to "OPPO Acoustic Lab",
        "Fluid Waves" to "ColorOS Ambient",
        "Aurora Silk Echoes" to "Cyber Acoustic"
    )
    private var currentTrackIndex = 0

    private val _trackTitle = MutableStateFlow(musicTracks[0].first)
    val trackTitle: StateFlow<String> = _trackTitle.asStateFlow()

    private val _artistName = MutableStateFlow(musicTracks[0].second)
    val artistName: StateFlow<String> = _artistName.asStateFlow()

    private val _isBoosting = MutableStateFlow(false)
    val isBoosting: StateFlow<Boolean> = _isBoosting.asStateFlow()

    init {
        val db = LauncherDatabase.getDatabase(application)
        repository = LauncherRepository(db.launcherDao())

        viewModelScope.launch(Dispatchers.IO) {
            _allApps.value = AppCatalogHelper.loadInstalledApps(application)
            repository.ensureDefaultSetupSeeded()
        }
    }

    val rootDesktopItems: StateFlow<List<DesktopItemEntity>> = repository.rootDesktopItems
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val folders: StateFlow<List<FolderEntity>> = repository.folders
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val launcherConfig: StateFlow<LauncherConfigEntity> = repository.launcherConfig
        .map { it ?: LauncherConfigEntity() }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), LauncherConfigEntity())

    fun setCurrentPage(page: Int) {
        _currentPage.value = page
    }

    fun openShelf() { _isShelfOpen.value = true }
    fun closeShelf() { _isShelfOpen.value = false }

    fun openDrawer() { _isDrawerOpen.value = true }
    fun closeDrawer() { _isDrawerOpen.value = false }

    fun openEditMode() {
        if (!launcherConfig.value.isLayoutLocked) {
            _isEditModeOpen.value = true
        } else {
            Toast.makeText(getApplication(), "Desktop layout is locked. Unlock in settings.", Toast.LENGTH_SHORT).show()
        }
    }
    fun closeEditMode() { _isEditModeOpen.value = false }

    fun openGlobalSearch() { _isGlobalSearchOpen.value = true }
    fun closeGlobalSearch() { _isGlobalSearchOpen.value = false }

    fun lockSleep() { _isLockedSleep.value = true }
    fun wakeUp() { _isLockedSleep.value = false }

    fun openFolder(folderId: Long) { _expandedFolderId.value = folderId }
    fun closeFolder() { _expandedFolderId.value = null }

    fun setSidebarOpen(isOpen: Boolean) { _isSidebarOpen.value = isOpen }

    fun toggleTorch() {
        _isTorchOn.value = !_isTorchOn.value
        try {
            val cameraManager = getApplication<Application>().getSystemService(Context.CAMERA_SERVICE) as? CameraManager
            if (cameraManager != null) {
                val cameraId = cameraManager.cameraIdList.firstOrNull()
                if (cameraId != null) {
                    cameraManager.setTorchMode(cameraId, _isTorchOn.value)
                }
            }
        } catch (e: Exception) {
            // Flashlight might not be available in emulator
        }
    }

    fun addSteps(amount: Int = 500) {
        _currentSteps.value += amount
    }

    fun toggleMusic() {
        _isMusicPlaying.value = !_isMusicPlaying.value
    }

    fun nextMusicTrack() {
        currentTrackIndex = (currentTrackIndex + 1) % musicTracks.size
        _trackTitle.value = musicTracks[currentTrackIndex].first
        _artistName.value = musicTracks[currentTrackIndex].second
        _isMusicPlaying.value = true
    }

    fun boostDevice() {
        viewModelScope.launch {
            _isBoosting.value = true
            delay(1200)
            _isBoosting.value = false
            Toast.makeText(getApplication(), "Memory boosted! +1.4 GB RAM released.", Toast.LENGTH_SHORT).show()
        }
    }

    fun openThemeStore() { _isThemeStoreOpen.value = true }
    fun closeThemeStore() { _isThemeStoreOpen.value = false }

    fun openNotifications() { _isNotificationsOpen.value = true }
    fun closeNotifications() { _isNotificationsOpen.value = false }

    fun updateSwipeDownAction(action: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateLauncherConfig(launcherConfig.value.copy(swipeDownAction = action))
        }
    }

    fun updateIconBadgeStyle(style: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateLauncherConfig(launcherConfig.value.copy(iconBadgeStyle = style))
        }
    }

    fun updateIconStyle(style: IconDesignStyle) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateLauncherConfig(launcherConfig.value.copy(iconDesignStyle = style.name))
        }
    }

    fun updateWallpaper(wallpaper: WallpaperType) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateLauncherConfig(launcherConfig.value.copy(wallpaperType = wallpaper.name))
        }
    }

    fun updateIconShape(shape: IconShape) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateLauncherConfig(launcherConfig.value.copy(iconShape = shape.name))
        }
    }

    fun updateIconScale(scale: Float) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateLauncherConfig(launcherConfig.value.copy(iconScale = scale))
        }
    }

    fun toggleShowLabels(show: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateLauncherConfig(launcherConfig.value.copy(showLabels = show))
        }
    }

    fun updateGrid(cols: Int, rows: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateLauncherConfig(launcherConfig.value.copy(gridCols = cols, gridRows = rows))
        }
    }

    fun updateAccent(accent: AccentThemeOption) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateLauncherConfig(launcherConfig.value.copy(accentColorName = accent.name))
        }
    }

    fun toggleLayoutLocked(locked: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateLauncherConfig(launcherConfig.value.copy(isLayoutLocked = locked))
        }
    }

    fun toggleDarkTheme(isDark: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateLauncherConfig(launcherConfig.value.copy(isDarkTheme = isDark))
        }
    }

    fun toggleFolderSize(folderId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            val folder = folders.value.find { it.id == folderId } ?: return@launch
            repository.updateFolder(folder.copy(isLarge = !folder.isLarge))
        }
    }

    fun renameFolder(folderId: Long, newTitle: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val folder = folders.value.find { it.id == folderId } ?: return@launch
            repository.updateFolder(folder.copy(title = newTitle))
        }
    }

    fun addWidgetToDesktop(widgetType: LauncherWidgetType, page: Int = _currentPage.value) {
        viewModelScope.launch(Dispatchers.IO) {
            val item = DesktopItemEntity(
                page = page,
                cellX = 0,
                cellY = 1,
                spanX = widgetType.defaultSpanX,
                spanY = widgetType.defaultSpanY,
                itemType = "WIDGET",
                title = widgetType.title,
                widgetType = widgetType.name
            )
            repository.insertDesktopItem(item)
            Toast.makeText(getApplication(), "Added ${widgetType.title} to desktop", Toast.LENGTH_SHORT).show()
        }
    }

    fun launchApp(app: AppModel) {
        if (app.iconKey == "themes" || app.packageName == "com.heytap.themestore" ||
            app.label.equals("Theme Store", ignoreCase = true) || app.label.equals("Themes", ignoreCase = true)
        ) {
            openThemeStore()
            return
        }
        AppCatalogHelper.launchApp(getApplication(), app)
    }

    fun getItemsInFolder(folderId: Long): Flow<List<DesktopItemEntity>> =
        repository.getItemsInFolder(folderId)
}
