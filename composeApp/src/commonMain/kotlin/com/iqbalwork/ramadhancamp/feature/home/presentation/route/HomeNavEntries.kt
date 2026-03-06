package com.iqbalwork.ramadhancamp.feature.home.presentation.route

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.iqbalwork.ramadhancamp.feature.home.presentation.HomeMainScreen
import com.iqbalwork.ramadhancamp.feature.home.presentation.locationpicker.LocationPickerScreen
import com.iqbalwork.ramadhancamp.shared.common.navigation.AppNavigationController
import com.iqbalwork.ramadhancamp.shared.common.navigation.TabDestination

@OptIn(ExperimentalMaterial3Api::class)
fun EntryProviderScope<NavKey>.homeTabEntries(nav: AppNavigationController) {
    entry<TabDestination.HomeMain> { HomeMainScreen() }
    entry<TabDestination.HomeLocationPicker> { LocationPickerScreen() }
}
