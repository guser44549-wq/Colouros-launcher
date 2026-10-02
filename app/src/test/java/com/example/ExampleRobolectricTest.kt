package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.model.AppCategory
import com.example.model.IconDesignStyle
import com.example.ui.theme.CURATED_THEMES
import com.example.util.AppCatalogHelper
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

  @Test
  fun `read string from context`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val appName = context.getString(R.string.app_name)
    assertEquals("ColorOS Launcher", appName)
  }

  @Test
  fun `verify system app presets loaded`() {
    val presets = AppCatalogHelper.SYSTEM_APP_PRESETS
    assertTrue(presets.isNotEmpty())
    assertTrue(presets.any { it.label == "Phone" && it.category == AppCategory.SOCIAL })
    assertTrue(presets.any { it.label == "Camera" && it.category == AppCategory.MEDIA })
    assertTrue(presets.any { it.label == "Settings" && it.category == AppCategory.TOOLS })
  }

  @Test
  fun `verify icon design styles availability`() {
    val styles = IconDesignStyle.values().map { it.name }
    assertTrue(styles.contains("NORMAL"))
    assertTrue(styles.contains("COLOROS_15"))
    assertTrue(styles.contains("COLOROS_16"))
    assertTrue(styles.contains("CLASSIC"))
    assertTrue(styles.contains("GLASSY"))
    assertTrue(styles.contains("PURE_BLACK"))
    assertTrue(styles.contains("PURE_WHITE"))
    assertTrue(CURATED_THEMES.isNotEmpty())
  }
}
