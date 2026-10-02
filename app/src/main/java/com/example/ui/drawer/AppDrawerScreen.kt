package com.example.ui.drawer

import androidx.compose.animation.*
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material.icons.rounded.Search
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
import com.example.model.AppCategory
import com.example.model.AppModel
import com.example.model.IconDesignStyle
import com.example.model.IconShape
import com.example.ui.components.AppIconView
import com.example.ui.components.FrostedGlassCard
import kotlinx.coroutines.launch

@Composable
fun AppDrawerScreen(
    apps: List<AppModel>,
    iconShape: IconShape,
    iconScale: Float,
    accentColor: Color,
    designStyle: IconDesignStyle = IconDesignStyle.COLOROS_15,
    onAppClick: (AppModel) -> Unit,
    onCloseDrawer: () -> Unit,
    modifier: Modifier = Modifier
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf(AppCategory.ALL) }
    val gridState = rememberLazyGridState()
    val coroutineScope = rememberCoroutineScope()
    var activeScrollLetter by remember { mutableStateOf<Char?>(null) }

    // Filter apps based on search and category
    val filteredApps = remember(apps, searchQuery, selectedCategory) {
        apps.filter { app ->
            val matchesCategory = selectedCategory == AppCategory.ALL || app.category == selectedCategory
            val matchesSearch = searchQuery.isBlank() ||
                    app.label.contains(searchQuery, ignoreCase = true) ||
                    app.packageName.contains(searchQuery, ignoreCase = true)
            matchesCategory && matchesSearch
        }.sortedBy { it.label.lowercase() }
    }

    val alphabet = ('A'..'Z').toList()

    Box(
        modifier = modifier
            .testTag("app_drawer_screen")
            .fillMaxSize()
            .background(Color(0xE60A101C))
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 8.dp)
        ) {
            // Drag Down Close Handle
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onCloseDrawer() }
                    .padding(vertical = 4.dp),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .width(42.dp)
                        .height(4.dp)
                        .clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.4f))
                )
            }

            // Real-Time Search Bar
            FrostedGlassCard(
                shape = RoundedCornerShape(22.dp),
                backgroundColor = Color(0x3D1E2A38),
                borderColor = Color(0x33FFFFFF),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 6.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp, vertical = 2.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Search,
                        contentDescription = "Search",
                        tint = accentColor,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    TextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        placeholder = {
                            Text(
                                text = "Search apps, tools…",
                                color = Color.White.copy(alpha = 0.5f),
                                fontSize = 14.sp
                            )
                        },
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White
                        ),
                        singleLine = true,
                        modifier = Modifier
                            .weight(1f)
                            .testTag("drawer_search_input")
                    )
                    if (searchQuery.isNotEmpty()) {
                        IconButton(onClick = { searchQuery = "" }) {
                            Icon(
                                imageVector = Icons.Rounded.Clear,
                                contentDescription = "Clear",
                                tint = Color.White.copy(alpha = 0.7f),
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                }
            }

            // Category Filter Tabs
            ScrollableTabRow(
                selectedTabIndex = AppCategory.values().indexOf(selectedCategory),
                edgePadding = 16.dp,
                containerColor = Color.Transparent,
                divider = {},
                indicator = {},
                modifier = Modifier.fillMaxWidth()
            ) {
                AppCategory.values().forEach { category ->
                    val isSelected = category == selectedCategory
                    val categoryCount = remember(apps, category) {
                        if (category == AppCategory.ALL) apps.size
                        else apps.count { it.category == category }
                    }

                    Box(
                        modifier = Modifier
                            .padding(end = 8.dp, top = 4.dp, bottom = 4.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(if (isSelected) accentColor else Color(0x2BFFFFFF))
                            .clickable { selectedCategory = category }
                            .padding(horizontal = 14.dp, vertical = 6.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = category.title,
                                color = if (isSelected) Color.Black else Color.White,
                                fontSize = 12.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "$categoryCount",
                                color = if (isSelected) Color.Black.copy(alpha = 0.7f) else Color.White.copy(alpha = 0.5f),
                                fontSize = 10.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Main Apps Grid + Fast Scroll Alphabet Bar
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                if (filteredApps.isEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "No apps found matching \"$searchQuery\"",
                            color = Color.White.copy(alpha = 0.6f),
                            fontSize = 14.sp
                        )
                    }
                } else {
                    LazyVerticalGrid(
                        state = gridState,
                        columns = GridCells.Fixed(4),
                        contentPadding = PaddingValues(start = 12.dp, end = 36.dp, top = 8.dp, bottom = 24.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(filteredApps, key = { it.id }) { app ->
                            AppIconView(
                                app = app,
                                iconShape = iconShape,
                                iconScale = iconScale,
                                showLabel = true,
                                designStyle = designStyle,
                                onClick = {
                                    onAppClick(app)
                                    onCloseDrawer()
                                }
                            )
                        }
                    }
                }

                // Alphabet Fast-Scroller on the Right
                Column(
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .padding(end = 4.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(0x33000000))
                        .padding(vertical = 4.dp, horizontal = 2.dp)
                        .pointerInput(filteredApps) {
                            detectTapGestures { offset ->
                                val totalLetters = alphabet.size
                                val letterIndex = ((offset.y / size.height) * totalLetters)
                                    .toInt()
                                    .coerceIn(0, totalLetters - 1)
                                val letter = alphabet[letterIndex]
                                activeScrollLetter = letter

                                // Jump to item starting with this letter
                                val targetIndex = filteredApps.indexOfFirst {
                                    it.label.startsWith(letter, ignoreCase = true)
                                }
                                if (targetIndex >= 0) {
                                    coroutineScope.launch {
                                        gridState.scrollToItem(targetIndex)
                                    }
                                }
                            }
                        },
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    alphabet.forEach { letter ->
                        Text(
                            text = letter.toString(),
                            color = if (activeScrollLetter == letter) accentColor else Color.White.copy(alpha = 0.65f),
                            fontSize = 9.sp,
                            fontWeight = if (activeScrollLetter == letter) FontWeight.Bold else FontWeight.Normal,
                            modifier = Modifier.padding(vertical = 1.dp)
                        )
                    }
                }

                // Floating Magnified Letter Indicator
                activeScrollLetter?.let { letter ->
                    Box(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .size(64.dp)
                            .clip(CircleShape)
                            .background(accentColor)
                            .border(2.dp, Color.White, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = letter.toString(),
                            color = Color.Black,
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    // Auto hide letter bubble after brief delay
                    LaunchedEffect(letter) {
                        kotlinx.coroutines.delay(1200)
                        activeScrollLetter = null
                    }
                }
            }
        }
    }
}
