package com.iqbalwork.ramadhancamp.feature.qibla.presentation.route

import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.iqbalwork.ramadhancamp.feature.qibla.presentation.QiblaMainScreen
import com.iqbalwork.ramadhancamp.shared.common.navigation.BackStackNode
import com.iqbalwork.ramadhancamp.shared.common.navigation.FeatureTab
import com.iqbalwork.ramadhancamp.shared.common.navigation.TabDestination
import com.iqbalwork.ramadhancamp.shared.common.navigation.rememberTabBackStack
import org.jetbrains.compose.resources.DrawableResource
import ramadhancamp.composeapp.generated.resources.Res
import ramadhancamp.composeapp.generated.resources.ic_filled_compass_tab
import ramadhancamp.composeapp.generated.resources.ic_outlined_compass_tab

object QiblaTab : FeatureTab() {
    override val initialDestination: NavKey = TabDestination.QiblaMain
    override val label: String              = "Kiblat"
    override val selectedIcon: DrawableResource   = Res.drawable.ic_filled_compass_tab
    override val unselectedIcon: DrawableResource = Res.drawable.ic_outlined_compass_tab

    @Composable
    override fun backstack(): BackStackNode = rememberTabBackStack(initialDestination, label)

    override fun EntryProviderScope<NavKey>.registerEntries() {
        entry<TabDestination.QiblaMain> { QiblaMainScreen() }
    }
}
