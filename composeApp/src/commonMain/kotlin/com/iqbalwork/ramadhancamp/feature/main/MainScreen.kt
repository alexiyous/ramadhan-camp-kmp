package com.iqbalwork.ramadhancamp.feature.main

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.iqbalwork.ramadhancamp.feature.bookmark.bookmarkTabEntries
import com.iqbalwork.ramadhancamp.feature.home.homeTabEntries
import com.iqbalwork.ramadhancamp.feature.pray.prayTabEntries
import com.iqbalwork.ramadhancamp.feature.qibla.qiblaTabEntries
import com.iqbalwork.ramadhancamp.feature.quran.quranTabEntries
import com.iqbalwork.ramadhancamp.shared.common.bottomSheet.BottomSheetSceneStrategy
import com.iqbalwork.ramadhancamp.shared.common.navigation.AppNavigationController
import com.iqbalwork.ramadhancamp.shared.common.navigation.AppTab

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: AppNavigationController) {
    val currentTab by navController.currentTab
    val bottomSheetStrategy = remember(navController) { BottomSheetSceneStrategy<NavKey>(navController) }

    Scaffold(
        bottomBar = {
            NavigationBar {
                AppTab.entries.forEach { tab ->
                    NavigationBarItem(
                        selected = currentTab == tab,
                        onClick = { navController.switchTab(tab) },
                        icon = {},
                        label = { Text(text = tab.name) },
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
