package com.iqbalwork.ramadhancamp.feature.pray

import androidx.navigation3.runtime.EntryProviderBuilder
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entry
import com.iqbalwork.ramadhancamp.feature.pray.presentation.PrayDetailScreen
import com.iqbalwork.ramadhancamp.feature.pray.presentation.PrayMainScreen
import com.iqbalwork.ramadhancamp.feature.pray.presentation.PraySheetScreen
import com.iqbalwork.ramadhancamp.feature.pray.presentation.PraySubDetailScreen
import com.iqbalwork.ramadhancamp.shared.common.bottomSheet.BottomSheetSceneStrategy
import com.iqbalwork.ramadhancamp.shared.common.navigation.AppNavigationController
import com.iqbalwork.ramadhancamp.shared.common.navigation.DialogDestination
import com.iqbalwork.ramadhancamp.shared.common.navigation.TabDestination

fun EntryProviderBuilder<NavKey>.prayTabEntries(nav: AppNavigationController) {
    entry<TabDestination.PrayMain>      { PrayMainScreen() }
    entry<TabDestination.PrayDetail>    { PrayDetailScreen() }
    entry<TabDestination.PraySubDetail> { PraySubDetailScreen() }
    entry<DialogDestination.PraySheet>(metadata = BottomSheetSceneStrategy.bottomSheet()) {
        PraySheetScreen(onDismiss = { nav.hideDialog() })
    }
}
