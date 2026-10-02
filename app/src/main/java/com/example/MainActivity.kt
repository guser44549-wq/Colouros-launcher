package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.desktop.HomeScreen
import com.example.ui.theme.AccentThemeOption
import com.example.ui.theme.ColorOSLauncherTheme
import com.example.viewmodel.LauncherViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val viewModel: LauncherViewModel = viewModel()
            val config by viewModel.launcherConfig.collectAsState()

            val accentTheme = remember(config.accentColorName) {
                try {
                    AccentThemeOption.valueOf(config.accentColorName)
                } catch (e: Exception) {
                    AccentThemeOption.OCEAN
                }
            }

            ColorOSLauncherTheme(
                darkTheme = config.isDarkTheme,
                accentTheme = accentTheme
            ) {
                Surface(modifier = Modifier.fillMaxSize()) {
                    HomeScreen(viewModel = viewModel)
                }
            }
        }
    }
}
