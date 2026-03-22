package com.iqbalwork.ramadhancamp.feature.quran.presentation.route

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.iqbalwork.ramadhancamp.feature.quran.presentation.QuranDetailScreen
import com.iqbalwork.ramadhancamp.feature.quran.presentation.QuranMainScreen
import com.iqbalwork.ramadhancamp.feature.quran.presentation.QuranSheetScreen
import com.iqbalwork.ramadhancamp.feature.quran.presentation.QuranSubDetailScreen
import com.iqbalwork.ramadhancamp.shared.common.bottomSheet.BottomSheetSceneStrategy
import com.iqbalwork.ramadhancamp.shared.common.navigation.BackStackNode
import com.iqbalwork.ramadhancamp.shared.common.navigation.DialogDestination
import com.iqbalwork.ramadhancamp.shared.common.navigation.FeatureTab
import com.iqbalwork.ramadhancamp.shared.common.navigation.LocalBackStackNode
import com.iqbalwork.ramadhancamp.shared.common.navigation.TabDestination
import com.iqbalwork.ramadhancamp.shared.common.navigation.rememberTabBackStack
import org.jetbrains.compose.resources.DrawableResource
import ramadhancamp.composeapp.generated.resources.Res
import ramadhancamp.composeapp.generated.resources.ic_filled_book_tab
import ramadhancamp.composeapp.generated.resources.ic_outlined_book_tab

@OptIn(ExperimentalMaterial3Api::class)
object QuranTab : FeatureTab() {
    override val initialDestination: NavKey = TabDestination.QuranMain
    override val label: String              = "Quran"
    override val selectedIcon: DrawableResource   = Res.drawable.ic_filled_book_tab
    override val unselectedIcon: DrawableResource = Res.drawable.ic_outlined_book_tab

    @Composable
    override fun backstack(): BackStackNode = rememberTabBackStack(initialDestination)

    override fun EntryProviderScope<NavKey>.registerEntries() {
        entry<TabDestination.QuranMain>      { QuranMainScreen() }
        entry<TabDestination.QuranDetail>    { QuranDetailScreen() }
        entry<TabDestination.QuranSubDetail> { QuranSubDetailScreen() }
        entry<DialogDestination.QuranSheet>(metadata = BottomSheetSceneStrategy.bottomSheet()) {
            val backStack = LocalBackStackNode.current.backStack
            QuranSheetScreen(onDismiss = { backStack.removeLastOrNull() })
        }
    }
}
