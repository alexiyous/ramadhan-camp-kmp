package com.iqbalwork.ramadhancamp.feature.home.presentation

import androidx.lifecycle.ViewModel
import com.iqbalwork.ramadhancamp.shared.common.navigation.AppNavigationControllerHolder
import com.iqbalwork.ramadhancamp.shared.common.navigation.AppTab
import com.iqbalwork.ramadhancamp.shared.common.navigation.DialogDestination
import com.iqbalwork.ramadhancamp.shared.common.navigation.TabDestination

class HomeViewModel(
    private val navHolder: AppNavigationControllerHolder,
) : ViewModel() {

    fun navigateToDetail() {
        navHolder.get().navigateToInsideTab(TabDestination.HomeDetail)
    }

    fun navigateToQuran() {
        navHolder.get().switchTab(AppTab.Quran)
    }

    fun showSampleDialog() {
        navHolder.get().showDialog(DialogDestination.SampleDialog)
    }

    fun back() {
        navHolder.get().back()
    }
}
