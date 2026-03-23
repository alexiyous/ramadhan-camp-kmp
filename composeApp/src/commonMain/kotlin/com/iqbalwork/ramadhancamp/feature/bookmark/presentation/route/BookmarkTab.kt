package com.iqbalwork.ramadhancamp.feature.bookmark.presentation.route

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.iqbalwork.ramadhancamp.feature.bookmark.presentation.BookmarkDetailScreen
import com.iqbalwork.ramadhancamp.feature.bookmark.presentation.BookmarkMainScreen
import com.iqbalwork.ramadhancamp.feature.bookmark.presentation.BookmarkSheetScreen
import com.iqbalwork.ramadhancamp.feature.bookmark.presentation.BookmarkSubDetailScreen
import com.iqbalwork.ramadhancamp.shared.common.bottomSheet.BottomSheetSceneStrategy
import com.iqbalwork.ramadhancamp.shared.common.navigation.BackStackNode
import com.iqbalwork.ramadhancamp.shared.common.navigation.DialogDestination
import com.iqbalwork.ramadhancamp.shared.common.navigation.FeatureTab
import com.iqbalwork.ramadhancamp.shared.common.navigation.LocalBackStackNode
import com.iqbalwork.ramadhancamp.shared.common.navigation.TabDestination
import com.iqbalwork.ramadhancamp.shared.common.navigation.rememberTabBackStack
import org.jetbrains.compose.resources.DrawableResource
import ramadhancamp.composeapp.generated.resources.Res
import ramadhancamp.composeapp.generated.resources.ic_filled_bookmark_tab
import ramadhancamp.composeapp.generated.resources.ic_outlined_bookmark_tab

@OptIn(ExperimentalMaterial3Api::class)
object BookmarkTab : FeatureTab() {
    override val initialDestination: NavKey = TabDestination.BookmarkMain
    override val label: String              = "Bookmark"
    override val selectedIcon: DrawableResource   = Res.drawable.ic_filled_bookmark_tab
    override val unselectedIcon: DrawableResource = Res.drawable.ic_outlined_bookmark_tab

    @Composable
    override fun backstack(): BackStackNode = rememberTabBackStack(initialDestination, label)

    override fun EntryProviderScope<NavKey>.registerEntries() {
        entry<TabDestination.BookmarkMain>      { BookmarkMainScreen() }
        entry<TabDestination.BookmarkDetail>    { BookmarkDetailScreen() }
        entry<TabDestination.BookmarkSubDetail> { BookmarkSubDetailScreen() }
        entry<DialogDestination.BookmarkSheet>(metadata = BottomSheetSceneStrategy.bottomSheet()) {
            val backStack = LocalBackStackNode.current.backStack
            BookmarkSheetScreen(onDismiss = { backStack.removeLastOrNull() })
        }
    }
}
