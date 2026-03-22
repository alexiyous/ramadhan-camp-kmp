package com.iqbalwork.ramadhancamp.feature.qibla.presentation.route

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.iqbalwork.ramadhancamp.feature.qibla.presentation.QiblaDetailScreen
import com.iqbalwork.ramadhancamp.feature.qibla.presentation.QiblaMainScreen
import com.iqbalwork.ramadhancamp.feature.qibla.presentation.QiblaSheetScreen
import com.iqbalwork.ramadhancamp.feature.qibla.presentation.QiblaSubDetailScreen
import com.iqbalwork.ramadhancamp.shared.common.bottomSheet.BottomSheetSceneStrategy
import com.iqbalwork.ramadhancamp.shared.common.navigation.BackStackNode
import com.iqbalwork.ramadhancamp.shared.common.navigation.DialogDestination
import com.iqbalwork.ramadhancamp.shared.common.navigation.FeatureTab
import com.iqbalwork.ramadhancamp.shared.common.navigation.LocalBackStackNode
import com.iqbalwork.ramadhancamp.shared.common.navigation.TabDestination
import com.iqbalwork.ramadhancamp.shared.common.navigation.rememberTabBackStack
import org.jetbrains.compose.resources.DrawableResource
import ramadhancamp.composeapp.generated.resources.Res
import ramadhancamp.composeapp.generated.resources.ic_filled_compass_tab
import ramadhancamp.composeapp.generated.resources.ic_outlined_compass_tab

@OptIn(ExperimentalMaterial3Api::class)
object QiblaTab : FeatureTab() {
    override val initialDestination: NavKey = TabDestination.QiblaMain
    override val label: String              = "Kiblat"
    override val selectedIcon: DrawableResource   = Res.drawable.ic_filled_compass_tab
    override val unselectedIcon: DrawableResource = Res.drawable.ic_outlined_compass_tab

    @Composable
    override fun backstack(): BackStackNode = rememberTabBackStack(initialDestination)

    override fun EntryProviderScope<NavKey>.registerEntries() {
        entry<TabDestination.QiblaMain>      { QiblaMainScreen() }
        entry<TabDestination.QiblaDetail>    { QiblaDetailScreen() }
        entry<TabDestination.QiblaSubDetail> { QiblaSubDetailScreen() }
        entry<DialogDestination.QiblaSheet>(metadata = BottomSheetSceneStrategy.bottomSheet()) {
            val backStack = LocalBackStackNode.current.backStack
            QiblaSheetScreen(onDismiss = { backStack.removeLastOrNull() })
        }
    }
}
