package com.example.ui.desktop

import androidx.activity.compose.BackHandler
import androidx.compose.animation.*
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
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
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.DesktopItemEntity
import com.example.data.model.FolderEntity
import com.example.data.model.LauncherConfigEntity
import com.example.model.*
import com.example.ui.components.*
import com.example.ui.drawer.AppDrawerScreen
import com.example.ui.editmode.DesktopEditModal
import com.example.ui.shelf.*
import com.example.ui.theme.AccentThemeOption
import com.example.ui.theme.ThemeStoreScreen
import com.example.util.AppCatalogHelper
import com.example.viewmodel.LauncherViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    viewModel: LauncherViewModel,
    modifier: Modifier = Modifier
) {
    val coroutineScope = rememberCoroutineScope()
    val allApps by viewModel.allApps.collectAsState()
    val rootItems by viewModel.rootDesktopItems.collectAsState()
    val folders by viewModel.folders.collectAsState()
    val config by viewModel.launcherConfig.collectAsState()

    val isShelfOpen by viewModel.isShelfOpen.collectAsState()
    val isDrawerOpen by viewModel.isDrawerOpen.collectAsState()
    val isEditModeOpen by viewModel.isEditModeOpen.collectAsState()
    val isGlobalSearchOpen by viewModel.isGlobalSearchOpen.collectAsState()
    val isLockedSleep by viewModel.isLockedSleep.collectAsState()
    val expandedFolderId by viewModel.expandedFolderId.collectAsState()
    val isSidebarOpen by viewModel.isSidebarOpen.collectAsState()
    val isThemeStoreOpen by viewModel.isThemeStoreOpen.collectAsState()

    val isTorchOn by viewModel.isTorchOn.collectAsState()
    val currentSteps by viewModel.currentSteps.collectAsState()
    val isMusicPlaying by viewModel.isMusicPlaying.collectAsState()
    val trackTitle by viewModel.trackTitle.collectAsState()
    val artistName by viewModel.artistName.collectAsState()
    val isBoosting by viewModel.isBoosting.collectAsState()

    val accentTheme = remember(config.accentColorName) {
        try {
            AccentThemeOption.valueOf(config.accentColorName)
        } catch (e: Exception) {
            AccentThemeOption.OCEAN
        }
    }
    val iconShape = remember(config.iconShape) {
        try {
            IconShape.valueOf(config.iconShape)
        } catch (e: Exception) {
            IconShape.SQUIRCLE
        }
    }
    val iconDesignStyle = remember(config.iconDesignStyle) {
        try {
            IconDesignStyle.valueOf(config.iconDesignStyle)
        } catch (e: Exception) {
            IconDesignStyle.COLOROS_15
        }
    }
    val wallpaperType = remember(config.wallpaperType) {
        try {
            WallpaperType.valueOf(config.wallpaperType)
        } catch (e: Exception) {
            WallpaperType.AQUAMORPHIC_WAVE
        }
    }

    val pagerState = rememberPagerState(pageCount = { 2 })

    // BackHandler hierarchy
    BackHandler(enabled = isLockedSleep || isThemeStoreOpen || isDrawerOpen || isShelfOpen || isEditModeOpen || isGlobalSearchOpen || expandedFolderId != null || isSidebarOpen || pagerState.currentPage > 0) {
        when {
            isLockedSleep -> viewModel.wakeUp()
            isThemeStoreOpen -> viewModel.closeThemeStore()
            expandedFolderId != null -> viewModel.closeFolder()
            isDrawerOpen -> viewModel.closeDrawer()
            isGlobalSearchOpen -> viewModel.closeGlobalSearch()
            isSidebarOpen -> viewModel.setSidebarOpen(false)
            isShelfOpen -> viewModel.closeShelf()
            isEditModeOpen -> viewModel.closeEditMode()
            pagerState.currentPage > 0 -> {
                coroutineScope.launch { pagerState.animateScrollToPage(0) }
            }
        }
    }

    Box(
        modifier = modifier
            .testTag("home_screen_root")
            .fillMaxSize()
    ) {
        // Dynamic Aquamorphic Wallpaper Background
        AquamorphicWallpaperBackground(
            wallpaperType = wallpaperType,
            dimLevel = if (config.isDarkTheme) 0.18f else 0.05f
        )

        // Main Desktop UI
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
                .pointerInput(Unit) {
                    var totalDragY = 0f
                    detectDragGestures(
                        onDragStart = { totalDragY = 0f },
                        onDrag = { change, dragAmount ->
                            change.consume()
                            totalDragY += dragAmount.y
                        },
                        onDragEnd = {
                            if (totalDragY > 80f) {
                                // Swipe Down: Global Search
                                viewModel.openGlobalSearch()
                            } else if (totalDragY < -80f) {
                                // Swipe Up: App Drawer
                                viewModel.openDrawer()
                            }
                        }
                    )
                }
                .pointerInput(Unit) {
                    detectTapGestures(
                        onDoubleTap = { viewModel.lockSleep() },
                        onLongPress = { viewModel.openEditMode() }
                    )
                }
        ) {
            // Top Bar: Glance Widget, ColorOS 15 Fluid Cloud Capsule & Shelf Button
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Glance Pill (Clock + Date)
                val glanceTime = remember {
                    SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())
                }
                val glanceDate = remember {
                    SimpleDateFormat("EEE, MMM d", Locale.getDefault()).format(Date())
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color(0x33000000))
                        .clickable { viewModel.openShelf() }
                        .padding(horizontal = 10.dp, vertical = 5.dp)
                ) {
                    Text(
                        text = glanceTime,
                        color = Color.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "· $glanceDate",
                        color = Color.White.copy(alpha = 0.8f),
                        fontSize = 11.sp
                    )
                }

                // Official ColorOS 15 Fluid Cloud Dynamic Capsule (as in Screenshot 2 & 3)
                ColorOS15FluidCloudPill(
                    isPlaying = isMusicPlaying,
                    trackTitle = trackTitle,
                    onTogglePlay = { viewModel.toggleMusic() }
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    // Theme App Quick Button
                    Box(
                        modifier = Modifier
                            .size(34.dp)
                            .clip(CircleShape)
                            .background(Color(0x33000000))
                            .clickable { viewModel.openThemeStore() }
                            .testTag("theme_app_quick_button"),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Palette,
                            contentDescription = "Theme App",
                            tint = accentTheme.primary,
                            modifier = Modifier.size(18.dp)
                        )
                    }

                    // Launcher Settings / Customization Button
                    Box(
                        modifier = Modifier
                            .size(34.dp)
                            .clip(CircleShape)
                            .background(Color(0x33000000))
                            .clickable { viewModel.openEditMode() }
                            .testTag("launcher_settings_quick_button"),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Tune,
                            contentDescription = "Launcher Settings",
                            tint = Color.White,
                            modifier = Modifier.size(18.dp)
                        )
                    }

                    // Shelf Toggle Button
                    Box(
                        modifier = Modifier
                            .size(34.dp)
                            .clip(CircleShape)
                            .background(Color(0x33000000))
                            .clickable { viewModel.openShelf() }
                            .testTag("shelf_toggle_button"),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Dashboard,
                            contentDescription = "ColorOS Shelf",
                            tint = accentTheme.primary,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }

            // Desktop Horizontal Pager
            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) { page ->
                DesktopPageView(
                    page = page,
                    gridCols = config.gridCols,
                    gridRows = config.gridRows,
                    rootItems = rootItems.filter { it.page == page },
                    folders = folders.filter { it.page == page },
                    allApps = allApps,
                    iconShape = iconShape,
                    iconScale = config.iconScale,
                    showLabels = config.showLabels,
                    accentColor = accentTheme.primary,
                    designStyle = iconDesignStyle,
                    viewModel = viewModel
                )
            }

            // Page Indicator
            ColorOSPageIndicator(
                pageCount = pagerState.pageCount,
                currentPage = pagerState.currentPage,
                accentColor = accentTheme.primary,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 2.dp)
            )

            // Official ColorOS 15 Floating "Q Search" Pill directly above the dock (as in Screenshot 1, 2, 3)
            ColorOS15SearchPill(
                onClick = { viewModel.openGlobalSearch() },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(vertical = 4.dp)
            )

            // Bottom Dock Bar
            BottomDockBar(
                allApps = allApps,
                iconShape = iconShape,
                iconScale = config.iconScale,
                accentColor = accentTheme.primary,
                designStyle = iconDesignStyle,
                onAppClick = { viewModel.launchApp(it) },
                onOpenDrawer = { viewModel.openDrawer() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 4.dp)
            )
        }

        // Floating Smart Sidebar Overlay
        SmartSidebarOverlay(
            isOpen = isSidebarOpen,
            onOpenChange = { viewModel.setSidebarOpen(it) },
            quickApps = allApps,
            accentColor = accentTheme.primary,
            onAppClick = { viewModel.launchApp(it) },
            onOpenMiniWindow = { viewModel.launchApp(it) }
        )

        // Expanded Smart Folder Full-Screen Modal
        expandedFolderId?.let { fId ->
            val folderEntity = folders.find { it.id == fId }
            if (folderEntity != null) {
                val folderItems by viewModel.getItemsInFolder(fId).collectAsState(initial = emptyList())
                val folderAppModels = folderItems.mapNotNull { item ->
                    allApps.find { it.packageName == item.packageName || it.label.equals(item.title, ignoreCase = true) }
                        ?: AppCatalogHelper.SYSTEM_APP_PRESETS.find { it.iconKey == item.iconKey || it.label.equals(item.title, ignoreCase = true) }
                }

                val folderModel = FolderModel(
                    id = folderEntity.id,
                    title = folderEntity.title,
                    isLarge = folderEntity.isLarge,
                    page = folderEntity.page,
                    cellX = folderEntity.cellX,
                    cellY = folderEntity.cellY,
                    apps = folderAppModels
                )

                ExpandedFolderModal(
                    folder = folderModel,
                    iconShape = iconShape,
                    accentColor = accentTheme.primary,
                    designStyle = iconDesignStyle,
                    onDismiss = { viewModel.closeFolder() },
                    onAppClick = { viewModel.launchApp(it) },
                    onRenameFolder = { newTitle -> viewModel.renameFolder(fId, newTitle) },
                    onToggleSize = { viewModel.toggleFolderSize(fId) }
                )
            }
        }

        // ColorOS 15 Shelf Screen
        AnimatedVisibility(
            visible = isShelfOpen,
            enter = slideInHorizontally(initialOffsetX = { -it }) + fadeIn(),
            exit = slideOutHorizontally(targetOffsetX = { -it }) + fadeOut(),
            modifier = Modifier.fillMaxSize()
        ) {
            ShelfScreen(
                accentColor = accentTheme.primary,
                onCloseShelf = { viewModel.closeShelf() },
                currentSteps = currentSteps,
                onAddSteps = { viewModel.addSteps(500) },
                isMusicPlaying = isMusicPlaying,
                onToggleMusic = { viewModel.toggleMusic() },
                onNextTrack = { viewModel.nextMusicTrack() },
                trackTitle = trackTitle,
                artistName = artistName,
                isTorchOn = isTorchOn,
                onToggleTorch = { viewModel.toggleTorch() },
                onOpenCalculator = {
                    val calc = allApps.find { it.category == AppCategory.TOOLS && it.label.contains("Calc", true) }
                    if (calc != null) viewModel.launchApp(calc)
                },
                onOpenRecorder = {
                    val rec = allApps.find { it.iconKey == "recorder" }
                    if (rec != null) viewModel.launchApp(rec)
                },
                onBoostDevice = { viewModel.boostDevice() },
                isBoosting = isBoosting
            )
        }

        // ColorOS 15 App Drawer
        AnimatedVisibility(
            visible = isDrawerOpen,
            enter = slideInVertically(
                initialOffsetY = { it },
                animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = Spring.StiffnessMedium)
            ) + fadeIn(),
            exit = slideOutVertically(
                targetOffsetY = { it },
                animationSpec = spring(stiffness = Spring.StiffnessMedium)
            ) + fadeOut(),
            modifier = Modifier.fillMaxSize()
        ) {
            AppDrawerScreen(
                apps = allApps,
                iconShape = iconShape,
                iconScale = config.iconScale,
                accentColor = accentTheme.primary,
                designStyle = iconDesignStyle,
                onAppClick = { viewModel.launchApp(it) },
                onCloseDrawer = { viewModel.closeDrawer() }
            )
        }

        // ColorOS 15 Global Search Sheet
        GlobalSearchSheet(
            isOpen = isGlobalSearchOpen,
            onClose = { viewModel.closeGlobalSearch() },
            apps = allApps,
            iconShape = iconShape,
            accentColor = accentTheme.primary,
            designStyle = iconDesignStyle,
            onAppClick = { viewModel.launchApp(it) }
        )

        // Desktop Edit Mode
        AnimatedVisibility(
            visible = isEditModeOpen,
            enter = slideInVertically(initialOffsetY = { it }) + fadeIn(),
            exit = slideOutVertically(targetOffsetY = { it }) + fadeOut(),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
        ) {
            DesktopEditModal(
                selectedWallpaper = wallpaperType,
                onSelectWallpaper = { viewModel.updateWallpaper(it) },
                selectedIconStyle = iconDesignStyle,
                onSelectIconStyle = { viewModel.updateIconStyle(it) },
                selectedIconShape = iconShape,
                onSelectIconShape = { viewModel.updateIconShape(it) },
                iconScale = config.iconScale,
                onChangeIconScale = { viewModel.updateIconScale(it) },
                showLabels = config.showLabels,
                onToggleShowLabels = { viewModel.toggleShowLabels(it) },
                gridCols = config.gridCols,
                gridRows = config.gridRows,
                onChangeGrid = { c, r -> viewModel.updateGrid(c, r) },
                accentOption = accentTheme,
                onSelectAccent = { viewModel.updateAccent(it) },
                isLayoutLocked = config.isLayoutLocked,
                onToggleLayoutLocked = { viewModel.toggleLayoutLocked(it) },
                isDarkTheme = config.isDarkTheme,
                onToggleDarkTheme = { viewModel.toggleDarkTheme(it) },
                onAddWidget = { viewModel.addWidgetToDesktop(it) },
                onOpenThemeStore = { viewModel.openThemeStore() },
                onDone = { viewModel.closeEditMode() }
            )
        }

        // ColorOS 15 Theme Store Screen
        AnimatedVisibility(
            visible = isThemeStoreOpen,
            enter = slideInVertically(initialOffsetY = { it }) + fadeIn(),
            exit = slideOutVertically(targetOffsetY = { it }) + fadeOut(),
            modifier = Modifier.fillMaxSize()
        ) {
            ThemeStoreScreen(
                currentWallpaper = wallpaperType,
                onSelectWallpaper = { viewModel.updateWallpaper(it) },
                currentIconStyle = iconDesignStyle,
                onSelectIconStyle = { viewModel.updateIconStyle(it) },
                currentIconShape = iconShape,
                onSelectIconShape = { viewModel.updateIconShape(it) },
                currentIconScale = config.iconScale,
                onChangeIconScale = { viewModel.updateIconScale(it) },
                currentAccent = accentTheme,
                onSelectAccent = { viewModel.updateAccent(it) },
                isDarkTheme = config.isDarkTheme,
                onToggleDarkTheme = { viewModel.toggleDarkTheme(it) },
                onClose = { viewModel.closeThemeStore() }
            )
        }

        // Ambient Lock / Sleep Screen
        LockSleepOverlay(
            isLocked = isLockedSleep,
            onUnlock = { viewModel.wakeUp() },
            accentColor = accentTheme.primary
        )
    }
}

@Composable
fun DesktopPageView(
    page: Int,
    gridCols: Int,
    gridRows: Int,
    rootItems: List<DesktopItemEntity>,
    folders: List<FolderEntity>,
    allApps: List<AppModel>,
    iconShape: IconShape,
    iconScale: Float,
    showLabels: Boolean,
    accentColor: Color,
    designStyle: IconDesignStyle = IconDesignStyle.COLOROS_15,
    viewModel: LauncherViewModel
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 14.dp, vertical = 6.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        if (page == 0) {
            // Official ColorOS 15 Screenshot 1 Widget Layout
            val currentSteps by viewModel.currentSteps.collectAsState()
            val isBoosting by viewModel.isBoosting.collectAsState()

            // 1. Top Capsules Row: Steps Capsule & Recorder Capsule
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                ColorOS15StepsCapsule(
                    steps = currentSteps,
                    onClick = { viewModel.addSteps(500) }
                )
                ColorOS15RecorderCapsule(
                    isRecording = false,
                    durationText = "00:27",
                    onToggle = { viewModel.openShelf() }
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            // 2. Weather Card & Battery Availability Wave Card
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(125.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                ColorOS15WeatherCard(
                    temp = "27°",
                    condition = "Mostly clear",
                    location = "NanShan District",
                    highLow = "23° / 30°",
                    onClick = { viewModel.openShelf() },
                    modifier = Modifier.weight(1f)
                )

                ColorOS15BatteryChartCard(
                    batteryPercent = 53,
                    hoursRemaining = 12,
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            // 3. Analog Clock Widget & Storage Cleaner Widget
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(115.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                ColorOS15AnalogClockCard(
                    temp = "26°",
                    modifier = Modifier.weight(1f)
                )

                ColorOS15StorageCleanerCard(
                    usagePercent = 75,
                    onCleanUp = { viewModel.boostDevice() },
                    isCleaning = isBoosting,
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            // 4. Authentic ColorOS 15 App Icons (matching Screenshots 1 & 2)
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    val cameraApp = allApps.find { it.iconKey == "camera" } ?: AppCatalogHelper.SYSTEM_APP_PRESETS[3]
                    val photosApp = allApps.find { it.iconKey == "photos" } ?: AppCatalogHelper.SYSTEM_APP_PRESETS[4]
                    val musicApp = allApps.find { it.iconKey == "music" } ?: AppCatalogHelper.SYSTEM_APP_PRESETS[8]
                    val settingsApp = allApps.find { it.iconKey == "settings" } ?: AppCatalogHelper.SYSTEM_APP_PRESETS[5]

                    AppIconView(app = cameraApp, iconShape = iconShape, iconScale = iconScale, showLabel = showLabels, designStyle = designStyle, onClick = { viewModel.launchApp(cameraApp) })
                    AppIconView(app = photosApp, iconShape = iconShape, iconScale = iconScale, showLabel = showLabels, designStyle = designStyle, onClick = { viewModel.launchApp(photosApp) })
                    AppIconView(app = musicApp, iconShape = iconShape, iconScale = iconScale, showLabel = showLabels, designStyle = designStyle, onClick = { viewModel.launchApp(musicApp) })
                    AppIconView(app = settingsApp, iconShape = iconShape, iconScale = iconScale, showLabel = showLabels, designStyle = designStyle, onClick = { viewModel.launchApp(settingsApp) })
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    val themesApp = allApps.find { it.iconKey == "themes" } ?: AppCatalogHelper.SYSTEM_APP_PRESETS[17]
                    val notesApp = allApps.find { it.iconKey == "notes" } ?: AppCatalogHelper.SYSTEM_APP_PRESETS[9]
                    val filesApp = allApps.find { it.iconKey == "files" } ?: AppCatalogHelper.SYSTEM_APP_PRESETS[12]
                    val healthApp = allApps.find { it.iconKey == "health" } ?: AppCatalogHelper.SYSTEM_APP_PRESETS[16]

                    AppIconView(app = themesApp, iconShape = iconShape, iconScale = iconScale, showLabel = showLabels, designStyle = designStyle, onClick = { viewModel.launchApp(themesApp) })
                    AppIconView(app = notesApp, iconShape = iconShape, iconScale = iconScale, showLabel = showLabels, designStyle = designStyle, onClick = { viewModel.launchApp(notesApp) })
                    AppIconView(app = filesApp, iconShape = iconShape, iconScale = iconScale, showLabel = showLabels, designStyle = designStyle, onClick = { viewModel.launchApp(filesApp) })
                    AppIconView(app = healthApp, iconShape = iconShape, iconScale = iconScale, showLabel = showLabels, designStyle = designStyle, onClick = { viewModel.launchApp(healthApp) })
                }
            }
        } else {
            // Page 1 Layout: Widgets and productivity apps
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                val isMusicPlaying by viewModel.isMusicPlaying.collectAsState()
                val trackTitle by viewModel.trackTitle.collectAsState()
                val artistName by viewModel.artistName.collectAsState()
                val currentSteps by viewModel.currentSteps.collectAsState()

                FluidMusicWidget(
                    isPlaying = isMusicPlaying,
                    onTogglePlay = { viewModel.toggleMusic() },
                    onNextTrack = { viewModel.nextMusicTrack() },
                    trackTitle = trackTitle,
                    artistName = artistName,
                    accentColor = accentColor,
                    modifier = Modifier.weight(1f)
                )

                HealthStepsWidget(
                    currentSteps = currentSteps,
                    accentColor = accentColor,
                    onAddSteps = { viewModel.addSteps(500) },
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Quick Tools Widget
            val isTorchOn by viewModel.isTorchOn.collectAsState()
            QuickToolsCard(
                isTorchOn = isTorchOn,
                onToggleTorch = { viewModel.toggleTorch() },
                onOpenCalculator = {
                    val calc = allApps.find { it.category == AppCategory.TOOLS && it.label.contains("Calc", true) }
                    if (calc != null) viewModel.launchApp(calc)
                },
                onOpenRecorder = {
                    val rec = allApps.find { it.iconKey == "recorder" }
                    if (rec != null) viewModel.launchApp(rec)
                },
                onOpenMemo = { viewModel.openShelf() },
                accentColor = accentColor
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Device Status
            val isBoosting by viewModel.isBoosting.collectAsState()
            DeviceStatusWidget(
                accentColor = accentColor,
                onBoost = { viewModel.boostDevice() },
                isBoosting = isBoosting
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Apps Grid row on Page 1
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                val calendarApp = allApps.find { it.iconKey == "calendar" } ?: AppCatalogHelper.SYSTEM_APP_PRESETS[11]
                val notesApp = allApps.find { it.iconKey == "notes" } ?: AppCatalogHelper.SYSTEM_APP_PRESETS[9]
                val filesApp = allApps.find { it.iconKey == "files" } ?: AppCatalogHelper.SYSTEM_APP_PRESETS[12]
                val compassApp = allApps.find { it.iconKey == "compass" } ?: AppCatalogHelper.SYSTEM_APP_PRESETS[14]

                AppIconView(app = calendarApp, iconShape = iconShape, iconScale = iconScale, showLabel = showLabels, designStyle = designStyle, onClick = { viewModel.launchApp(calendarApp) })
                AppIconView(app = notesApp, iconShape = iconShape, iconScale = iconScale, showLabel = showLabels, designStyle = designStyle, onClick = { viewModel.launchApp(notesApp) })
                AppIconView(app = filesApp, iconShape = iconShape, iconScale = iconScale, showLabel = showLabels, designStyle = designStyle, onClick = { viewModel.launchApp(filesApp) })
                AppIconView(app = compassApp, iconShape = iconShape, iconScale = iconScale, showLabel = showLabels, designStyle = designStyle, onClick = { viewModel.launchApp(compassApp) })
            }
        }
    }
}

@Composable
fun BottomDockBar(
    allApps: List<AppModel>,
    iconShape: IconShape,
    iconScale: Float,
    accentColor: Color,
    designStyle: IconDesignStyle = IconDesignStyle.COLOROS_15,
    onAppClick: (AppModel) -> Unit,
    onOpenDrawer: () -> Unit,
    modifier: Modifier = Modifier
) {
    FrostedGlassCard(
        shape = RoundedCornerShape(32.dp),
        backgroundColor = Color(0x40131E2C),
        borderColor = Color(0x40FFFFFF),
        borderWidth = 1.dp,
        modifier = modifier
            .testTag("bottom_dock_bar")
            .height(84.dp)
            .padding(horizontal = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            val phoneApp = allApps.find { it.iconKey == "phone" } ?: AppCatalogHelper.SYSTEM_APP_PRESETS[0]
            val msgApp = allApps.find { it.iconKey == "messages" } ?: AppCatalogHelper.SYSTEM_APP_PRESETS[1]
            val browserApp = allApps.find { it.iconKey == "browser" } ?: AppCatalogHelper.SYSTEM_APP_PRESETS[2]
            val cameraApp = allApps.find { it.iconKey == "camera" } ?: AppCatalogHelper.SYSTEM_APP_PRESETS[3]

            AppIconView(app = phoneApp, iconShape = iconShape, iconScale = iconScale * 0.95f, showLabel = false, designStyle = designStyle, onClick = { onAppClick(phoneApp) })
            AppIconView(app = msgApp, iconShape = iconShape, iconScale = iconScale * 0.95f, showLabel = false, designStyle = designStyle, onClick = { onAppClick(msgApp) })

            // Center App Drawer Trigger Pill
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .background(Color(0x33FFFFFF))
                    .clickable { onOpenDrawer() }
                    .testTag("app_drawer_button"),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Rounded.Apps,
                    contentDescription = "App Drawer",
                    tint = accentColor,
                    modifier = Modifier.size(28.dp)
                )
            }

            AppIconView(app = browserApp, iconShape = iconShape, iconScale = iconScale * 0.95f, showLabel = false, designStyle = designStyle, onClick = { onAppClick(browserApp) })
            AppIconView(app = cameraApp, iconShape = iconShape, iconScale = iconScale * 0.95f, showLabel = false, designStyle = designStyle, onClick = { onAppClick(cameraApp) })
        }
    }
}
