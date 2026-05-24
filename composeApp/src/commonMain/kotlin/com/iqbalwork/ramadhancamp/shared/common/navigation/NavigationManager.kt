package com.iqbalwork.ramadhancamp.shared.common.navigation

import androidx.compose.runtime.State
import androidx.navigation3.runtime.NavKey
import kotlinx.coroutines.flow.SharedFlow

interface NavigationManager {
    fun navigateTo(dest: NavKey, withReplace: Boolean = false)
    fun navigateToInsideTab(dest: NavKey, withReplace: Boolean = false)
    fun startNewFlow(dest: NavKey)
    fun back(navigationResult: NavigationResult? = null)
    fun backToScreen(key: NavKey, navigationResult: NavigationResult? = null)
    fun switchTab(tab: FeatureTab)
    fun showDialog(dialog: DialogDestination)
    fun hideDialog()
    fun sendResult(value: NavigationResult)
    fun subscribeToResult(key: String): SharedFlow<NavigationResult>
    fun releaseKey(key: String)
}
