package com.iqbalwork.ramadhancamp.feature.home.presentation.route

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.iqbalwork.ramadhancamp.feature.about.presentation.AboutScreen
import com.iqbalwork.ramadhancamp.feature.about.presentation.OssLicensesScreen
import com.iqbalwork.ramadhancamp.feature.home.presentation.HomeMainScreen
import com.iqbalwork.ramadhancamp.feature.home.presentation.HomeMainScreenParameters
import com.iqbalwork.ramadhancamp.feature.home.presentation.locationpicker.LocationPickerScreen
import com.iqbalwork.ramadhancamp.shared.common.navigation.BackStackNode
import com.iqbalwork.ramadhancamp.shared.common.navigation.FeatureTab
import com.iqbalwork.ramadhancamp.shared.common.navigation.TabDestination
import com.iqbalwork.ramadhancamp.shared.common.navigation.rememberTabBackStack
import org.jetbrains.compose.resources.DrawableResource
import ramadhancamp.composeapp.generated.resources.Res
import ramadhancamp.composeapp.generated.resources.ic_filled_home_tab
import ramadhancamp.composeapp.generated.resources.ic_outlined_home_tab

@OptIn(ExperimentalMaterial3Api::class)
object HomeTab : FeatureTab() {
    override val initialDestination: NavKey = TabDestination.HomeMain(HomeMainScreenParameters())
    override val label: String              = "Beranda"
    override val selectedIcon: DrawableResource   = Res.drawable.ic_filled_home_tab
    override val unselectedIcon: DrawableResource = Res.drawable.ic_outlined_home_tab

    @Composable
    override fun backstack(): BackStackNode = rememberTabBackStack(initialDestination, label)

    override fun EntryProviderScope<NavKey>.registerEntries() {
        entry<TabDestination.HomeMain>           { HomeMainScreen(parameters = it.param) }
        entry<TabDestination.HomeLocationPicker> { LocationPickerScreen() }
        entry<TabDestination.About>              { AboutScreen() }
        entry<TabDestination.OssLicenses>        { OssLicensesScreen() }
    }
}
