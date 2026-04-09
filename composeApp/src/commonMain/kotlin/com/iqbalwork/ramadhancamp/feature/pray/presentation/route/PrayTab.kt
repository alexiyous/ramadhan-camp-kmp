package com.iqbalwork.ramadhancamp.feature.pray.presentation.route

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.iqbalwork.ramadhancamp.feature.pray.presentation.PrayMainScreen
import com.iqbalwork.ramadhancamp.feature.pray.presentation.PrayMainScreenParameters
import com.iqbalwork.ramadhancamp.shared.common.navigation.BackStackNode
import com.iqbalwork.ramadhancamp.shared.common.navigation.FeatureTab
import com.iqbalwork.ramadhancamp.shared.common.navigation.TabDestination
import com.iqbalwork.ramadhancamp.shared.common.navigation.rememberTabBackStack
import org.jetbrains.compose.resources.DrawableResource
import ramadhancamp.composeapp.generated.resources.Res
import ramadhancamp.composeapp.generated.resources.ic_prayer_tab

@OptIn(ExperimentalMaterial3Api::class)
object PrayTab : FeatureTab() {
    override val initialDestination: NavKey = TabDestination.PrayMain(PrayMainScreenParameters())
    override val label: String              = "Doa"
    override val selectedIcon: DrawableResource   = Res.drawable.ic_prayer_tab
    override val unselectedIcon: DrawableResource = Res.drawable.ic_prayer_tab

    @Composable
    override fun backstack(): BackStackNode = rememberTabBackStack(initialDestination, label)

    override fun EntryProviderScope<NavKey>.registerEntries() {
        entry<TabDestination.PrayMain>      { PrayMainScreen(parameters = it.param) }
    }
}
