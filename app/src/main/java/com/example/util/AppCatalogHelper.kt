package com.example.util

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.compose.ui.graphics.Color
import com.example.model.AppCategory
import com.example.model.AppModel

object AppCatalogHelper {

    val SYSTEM_APP_PRESETS = listOf(
        AppModel(
            id = "sys_phone",
            label = "Phone",
            packageName = "com.android.dialer",
            className = "",
            category = AppCategory.SOCIAL,
            iconKey = "phone",
            isRealApp = false,
            primaryColor = Color(0xFF00C9A7)
        ),
        AppModel(
            id = "sys_messages",
            label = "Messages",
            packageName = "com.google.android.apps.messaging",
            className = "",
            category = AppCategory.SOCIAL,
            iconKey = "messages",
            isRealApp = false,
            primaryColor = Color(0xFF0078FE)
        ),
        AppModel(
            id = "sys_browser",
            label = "Browser",
            packageName = "com.android.chrome",
            className = "",
            category = AppCategory.PRODUCTIVITY,
            iconKey = "browser",
            isRealApp = false,
            primaryColor = Color(0xFF00B4D8)
        ),
        AppModel(
            id = "sys_camera",
            label = "Camera",
            packageName = "com.android.camera",
            className = "",
            category = AppCategory.MEDIA,
            iconKey = "camera",
            isRealApp = false,
            primaryColor = Color(0xFF263238)
        ),
        AppModel(
            id = "sys_gallery",
            label = "Photos",
            packageName = "com.coloros.gallery3d",
            className = "",
            category = AppCategory.MEDIA,
            iconKey = "photos",
            isRealApp = false,
            primaryColor = Color(0xFFFF6B8B)
        ),
        AppModel(
            id = "sys_settings",
            label = "Settings",
            packageName = "com.android.settings",
            className = "",
            category = AppCategory.TOOLS,
            iconKey = "settings",
            isRealApp = false,
            primaryColor = Color(0xFF546E7A)
        ),
        AppModel(
            id = "sys_weather",
            label = "Weather",
            packageName = "com.coloros.weather",
            className = "",
            category = AppCategory.TOOLS,
            iconKey = "weather",
            isRealApp = false,
            primaryColor = Color(0xFF0288D1)
        ),
        AppModel(
            id = "sys_clock",
            label = "Clock",
            packageName = "com.android.deskclock",
            className = "",
            category = AppCategory.TOOLS,
            iconKey = "clock",
            isRealApp = false,
            primaryColor = Color(0xFFE53935)
        ),
        AppModel(
            id = "sys_music",
            label = "Music",
            packageName = "com.heytap.music",
            className = "",
            category = AppCategory.MEDIA,
            iconKey = "music",
            isRealApp = false,
            primaryColor = Color(0xFFFF8C00)
        ),
        AppModel(
            id = "sys_notes",
            label = "Notes",
            packageName = "com.coloros.note",
            className = "",
            category = AppCategory.PRODUCTIVITY,
            iconKey = "notes",
            isRealApp = false,
            primaryColor = Color(0xFFFFB300)
        ),
        AppModel(
            id = "sys_calculator",
            label = "Calculator",
            packageName = "com.android.calculator2",
            className = "",
            category = AppCategory.TOOLS,
            iconKey = "calculator",
            isRealApp = false,
            primaryColor = Color(0xFF00BFA5)
        ),
        AppModel(
            id = "sys_calendar",
            label = "Calendar",
            packageName = "com.android.calendar",
            className = "",
            category = AppCategory.PRODUCTIVITY,
            iconKey = "calendar",
            isRealApp = false,
            primaryColor = Color(0xFF1E88E5)
        ),
        AppModel(
            id = "sys_files",
            label = "Files",
            packageName = "com.coloros.filemanager",
            className = "",
            category = AppCategory.TOOLS,
            iconKey = "files",
            isRealApp = false,
            primaryColor = Color(0xFF3949AB)
        ),
        AppModel(
            id = "sys_recorder",
            label = "Recorder",
            packageName = "com.coloros.soundrecorder",
            className = "",
            category = AppCategory.TOOLS,
            iconKey = "recorder",
            isRealApp = false,
            primaryColor = Color(0xFFE91E63)
        ),
        AppModel(
            id = "sys_compass",
            label = "Compass",
            packageName = "com.coloros.compass",
            className = "",
            category = AppCategory.TOOLS,
            iconKey = "compass",
            isRealApp = false,
            primaryColor = Color(0xFF00ACC1)
        ),
        AppModel(
            id = "sys_games",
            label = "Games",
            packageName = "com.coloros.gamespace",
            className = "",
            category = AppCategory.GAMES,
            iconKey = "games",
            isRealApp = false,
            primaryColor = Color(0xFF7E57C2)
        ),
        AppModel(
            id = "sys_health",
            label = "O-Health",
            packageName = "com.heytap.health",
            className = "",
            category = AppCategory.PRODUCTIVITY,
            iconKey = "health",
            isRealApp = false,
            primaryColor = Color(0xFF43A047)
        ),
        AppModel(
            id = "sys_themes",
            label = "Theme Store",
            packageName = "com.heytap.themestore",
            className = "",
            category = AppCategory.TOOLS,
            iconKey = "themes",
            isRealApp = false,
            primaryColor = Color(0xFF8E24AA)
        )
    )

    fun loadInstalledApps(context: Context): List<AppModel> {
        val pm = context.packageManager
        val intent = Intent(Intent.ACTION_MAIN, null).apply {
            addCategory(Intent.CATEGORY_LAUNCHER)
        }
        val resolveInfoList = pm.queryIntentActivities(intent, 0)
        val myPackage = context.packageName

        val installedList = resolveInfoList
            .filter { it.activityInfo.packageName != myPackage }
            .map { resolveInfo ->
                val pkgName = resolveInfo.activityInfo.packageName
                val className = resolveInfo.activityInfo.name
                val label = resolveInfo.loadLabel(pm).toString()
                val icon = resolveInfo.loadIcon(pm)
                val cat = guessCategory(pkgName, resolveInfo.activityInfo.applicationInfo)

                AppModel(
                    id = "$pkgName/$className",
                    label = label,
                    packageName = pkgName,
                    className = className,
                    category = cat,
                    iconKey = pkgName,
                    isRealApp = true,
                    iconDrawable = icon,
                    primaryColor = getPrimaryColorForPackage(pkgName)
                )
            }
            .sortedBy { it.label.lowercase() }

        // Merge with preset apps to guarantee rich ColorOS 15 desktop experience
        val result = mutableListOf<AppModel>()
        result.addAll(installedList)

        // Add any system presets that aren't already represented in installed apps
        for (preset in SYSTEM_APP_PRESETS) {
            val alreadyPresent = result.any { it.label.equals(preset.label, ignoreCase = true) || it.packageName == preset.packageName }
            if (!alreadyPresent) {
                result.add(preset)
            }
        }

        return result
    }

    private fun guessCategory(packageName: String, appInfo: ApplicationInfo): AppCategory {
        val pkg = packageName.lowercase()
        return when {
            pkg.contains("dialer") || pkg.contains("phone") || pkg.contains("message") ||
            pkg.contains("sms") || pkg.contains("whatsapp") || pkg.contains("telegram") ||
            pkg.contains("facebook") || pkg.contains("twitter") || pkg.contains("instagram") ||
            pkg.contains("contact") || pkg.contains("chat") -> AppCategory.SOCIAL

            pkg.contains("gallery") || pkg.contains("photo") || pkg.contains("camera") ||
            pkg.contains("music") || pkg.contains("video") || pkg.contains("media") ||
            pkg.contains("youtube") || pkg.contains("spotify") || pkg.contains("sound") -> AppCategory.MEDIA

            pkg.contains("game") || pkg.contains("arcade") || pkg.contains("rpg") ||
            pkg.contains("play.games") -> AppCategory.GAMES

            pkg.contains("calendar") || pkg.contains("note") || pkg.contains("mail") ||
            pkg.contains("gmail") || pkg.contains("doc") || pkg.contains("sheet") ||
            pkg.contains("office") || pkg.contains("chrome") || pkg.contains("browser") ||
            pkg.contains("drive") || pkg.contains("health") -> AppCategory.PRODUCTIVITY

            else -> AppCategory.TOOLS
        }
    }

    private fun getPrimaryColorForPackage(packageName: String): Color {
        val hash = packageName.hashCode()
        val palette = listOf(
            Color(0xFF00B4D8),
            Color(0xFF00C9A7),
            Color(0xFFFF8C00),
            Color(0xFF9B51E0),
            Color(0xFFFF6B8B),
            Color(0xFF1E88E5),
            Color(0xFF43A047),
            Color(0xFFE53935)
        )
        return palette[Math.abs(hash) % palette.size]
    }

    fun launchApp(context: Context, app: AppModel) {
        try {
            val pm = context.packageManager
            var launchIntent: Intent? = null

            if (app.className.isNotBlank()) {
                launchIntent = Intent(Intent.ACTION_MAIN).apply {
                    addCategory(Intent.CATEGORY_LAUNCHER)
                    component = ComponentName(app.packageName, app.className)
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED
                }
            }

            if (launchIntent == null || launchIntent.resolveActivity(pm) == null) {
                launchIntent = pm.getLaunchIntentForPackage(app.packageName)
            }

            if (launchIntent != null) {
                context.startActivity(launchIntent)
            } else {
                Toast.makeText(context, "Opening ${app.label}...", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            Toast.makeText(context, "Opening ${app.label}...", Toast.LENGTH_SHORT).show()
        }
    }
}
