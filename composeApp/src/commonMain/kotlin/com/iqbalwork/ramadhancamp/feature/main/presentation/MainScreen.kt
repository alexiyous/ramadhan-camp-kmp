package com.iqbalwork.ramadhancamp.feature.main.presentation

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.iqbalwork.ramadhancamp.feature.bookmark.bookmarkTabEntries
import com.iqbalwork.ramadhancamp.feature.home.presentation.route.homeTabEntries
import com.iqbalwork.ramadhancamp.feature.main.presentation.mapper.toAppTabInfo
import com.iqbalwork.ramadhancamp.feature.pray.prayTabEntries
import com.iqbalwork.ramadhancamp.feature.qibla.qiblaTabEntries
import com.iqbalwork.ramadhancamp.feature.quran.quranTabEntries
import com.iqbalwork.ramadhancamp.shared.common.bottomSheet.BottomSheetSceneStrategy
import com.iqbalwork.ramadhancamp.shared.common.navigation.AppTab
import com.iqbalwork.ramadhancamp.shared.common.navigation.LocalAppNavController
import com.iqbalwork.ramadhancamp.shared.common.ui.theme.RamadhanTheme
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val navController = LocalAppNavController.current
    val currentTab by navController.currentTab
    val bottomSheetStrategy = remember(navController) { BottomSheetSceneStrategy<NavKey>(navController) }

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = RamadhanTheme.colors.bgSecondary,
                contentColor = RamadhanTheme.colors.textMuted,
            ) {
                AppTab.entries.forEach { tab ->
                    val appTabInfo = tab.toAppTabInfo(currentTab == tab)
                    val scale by animateFloatAsState(
                        targetValue = if (appTabInfo.selected) 1.2f else 1.0f,
                        animationSpec = spring(dampingRatio = 0.5f, stiffness = 300f),
                    )
                    NavigationBarItem(
                        selected = appTabInfo.selected,
                        onClick = { navController.switchTab(tab) },
                        icon = {
                            Icon(
                                painter = painterResource(appTabInfo.icon),
                                contentDescription = appTabInfo.title,
                                modifier = Modifier.size(24.dp).scale(scale),
                            )
                        },
                        label = {
                            Text(
                                text = appTabInfo.title,
                                style = RamadhanTheme.typography.labelSmall,
                            )
                        },
                        alwaysShowLabel = true,
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = RamadhanTheme.colors.accentPrimary,
                            unselectedIconColor = RamadhanTheme.colors.textMuted,
                            selectedTextColor = RamadhanTheme.colors.accentPrimary,
                            unselectedTextColor = RamadhanTheme.colors.textMuted,
                            indicatorColor = Color.Transparent,
                        ),
                    )
                }
            }
        },
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding).fillMaxSize()) {
            AppTab.entries.forEach { tab ->
                key(tab) {
                    val isActive = currentTab == tab
                    NavDisplay(
                        backStack = navController.tabBackStacks[tab]!!,
                        modifier = Modifier
                            .fillMaxSize()
                            .then(if (!isActive) Modifier.requiredSize(0.dp) else Modifier),
                        sceneStrategy = bottomSheetStrategy,
                        entryDecorators = listOf(
                            rememberSaveableStateHolderNavEntryDecorator(),
                            rememberViewModelStoreNavEntryDecorator(),
                        ),
                        transitionSpec = { slideInHorizontally { it } togetherWith slideOutHorizontally { -it } },
                        popTransitionSpec = { slideInHorizontally { -it } togetherWith slideOutHorizontally { it } },
                        entryProvider = entryProvider {
                            when (tab) {
                                AppTab.Home     -> homeTabEntries(navController)
                                AppTab.Pray     -> prayTabEntries(navController)
                                AppTab.Quran    -> quranTabEntries(navController)
                                AppTab.Qibla    -> qiblaTabEntries(navController)
                                AppTab.Bookmark -> bookmarkTabEntries(navController)
                            }
                        },
                    )
                }
            }
        }
    }
}
