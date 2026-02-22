package com.iqbalwork.ramadhancamp.feature.main

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.iqbalwork.ramadhancamp.feature.home.presentation.HomeDetailScreen
import com.iqbalwork.ramadhancamp.feature.home.presentation.HomeMainScreen
import com.iqbalwork.ramadhancamp.feature.home.presentation.SampleDialogScreen
import com.iqbalwork.ramadhancamp.shared.common.bottomSheet.BottomSheetSceneStrategy
import com.iqbalwork.ramadhancamp.shared.common.navigation.AppNavigationController
import com.iqbalwork.ramadhancamp.shared.common.navigation.AppTab
import com.iqbalwork.ramadhancamp.shared.common.navigation.DialogDestination
import com.iqbalwork.ramadhancamp.shared.common.navigation.TabDestination

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
        NavDisplay(
            backStack = navController.tabBackStacks[currentTab]!!,
            modifier = Modifier.padding(innerPadding),
            sceneStrategy = bottomSheetStrategy,
            entryDecorators = listOf(
                rememberSaveableStateHolderNavEntryDecorator(),
                rememberViewModelStoreNavEntryDecorator(),
            ),
            transitionSpec = { slideInHorizontally { it } togetherWith slideOutHorizontally { -it } },
            popTransitionSpec = { slideInHorizontally { -it } togetherWith slideOutHorizontally { it } },
            entryProvider = entryProvider {
                entry<TabDestination.HomeMain> { HomeMainScreen() }
                entry<TabDestination.HomeDetail> { HomeDetailScreen() }
                entry<TabDestination.PrayMain> { PlaceholderTabScreen("Pray") }
                entry<TabDestination.QuranMain> { PlaceholderTabScreen("Quran") }
                entry<TabDestination.QiblaMain> { PlaceholderTabScreen("Qibla") }
                entry<TabDestination.BookmarkMain> { PlaceholderTabScreen("Bookmark") }
                entry<DialogDestination.SampleDialog>(
                    metadata = BottomSheetSceneStrategy.bottomSheet(),
                ) {
                    SampleDialogScreen(onDismiss = { navController.hideDialog() })
                }
            },
        )
    }
}

@Composable
private fun PlaceholderTabScreen(tabName: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Text(text = "$tabName — coming soon")
    }
}
