package com.iqbalwork.ramadhancamp.feature.main.presentation

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.iqbalwork.ramadhancamp.feature.bookmark.presentation.route.BookmarkTab
import com.iqbalwork.ramadhancamp.feature.home.presentation.route.HomeTab
import com.iqbalwork.ramadhancamp.feature.main.presentation.components.TabNavigationBar
import com.iqbalwork.ramadhancamp.feature.main.presentation.utils.requireTabInList
import com.iqbalwork.ramadhancamp.feature.pray.presentation.route.PrayTab
import com.iqbalwork.ramadhancamp.feature.qibla.presentation.route.QiblaTab
import com.iqbalwork.ramadhancamp.feature.quran.presentation.route.QuranTab
import com.iqbalwork.ramadhancamp.shared.common.bottomSheet.BottomSheetSceneStrategy
import com.iqbalwork.ramadhancamp.shared.common.navigation.FeatureTab
import com.iqbalwork.ramadhancamp.shared.common.navigation.LocalBackStackNode
import com.iqbalwork.ramadhancamp.shared.common.navigation.LocalCurrentTab
import com.iqbalwork.ramadhancamp.shared.common.navigation.rememberTabState

private val mainTabs: List<FeatureTab> = listOf(
    HomeTab, PrayTab, QuranTab, QiblaTab, BookmarkTab,
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(initialTab: FeatureTab? = null) {
    val resolvedInitial = initialTab
        ?.also { requireTabInList(mainTabs, it) }
        ?: mainTabs.first()

    val tabState = rememberTabState(mainTabs, resolvedInitial)
    val tabNodes = buildMap { mainTabs.forEach { tab -> put(tab, tab.backstack()) } }

    CompositionLocalProvider(LocalCurrentTab provides tabState) {
        Scaffold(
            bottomBar = {
                TabNavigationBar(
                    tabs          = mainTabs,
                    currentTab    = tabState.current,
                    onTabSelected = tabState::select,
                )
            },
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .windowInsetsPadding(WindowInsets(bottom = innerPadding.calculateBottomPadding())),
            ) {
                mainTabs.forEach { tab ->
                    val tabNode = tabNodes[tab]!!
                    CompositionLocalProvider(LocalBackStackNode provides tabNode) {
                        NavDisplay(
                            backStack       = tabNode.backStack,
                            modifier        = Modifier
                                .fillMaxSize()
                                .then(if (tabState.current != tab) Modifier.requiredSize(0.dp) else Modifier),
                            sceneStrategy   = BottomSheetSceneStrategy(
                                onBack = { tabNode.backStack.removeLastOrNull() },
                            ),
                            entryDecorators = listOf(
                                rememberSaveableStateHolderNavEntryDecorator(),
                                rememberViewModelStoreNavEntryDecorator(),
                            ),
                            transitionSpec    = { slideInHorizontally { it }  togetherWith slideOutHorizontally { -it } },
                            popTransitionSpec = { slideInHorizontally { -it } togetherWith slideOutHorizontally { it } },
                            entryProvider     = entryProvider { with(tab) { registerEntries() } },
                        )
                    }
                }
            }
        }
    }
}

