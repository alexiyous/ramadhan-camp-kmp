package com.iqbalwork.ramadhancamp.feature.pray.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iqbalwork.ramadhancamp.shared.common.navigation.AppNavigationControllerHolder
import com.iqbalwork.ramadhancamp.shared.common.navigation.AppTab
import com.iqbalwork.ramadhancamp.shared.common.navigation.DialogDestination
import com.iqbalwork.ramadhancamp.shared.common.navigation.NavigationResult
import com.iqbalwork.ramadhancamp.shared.common.navigation.TabDestination
import com.iqbalwork.ramadhancamp.shared.common.navigation.TextResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PrayViewModel(
    private val navHolder: AppNavigationControllerHolder,
) : ViewModel() {

    companion object { const val RESULT_KEY = "pray_result" }

    private val _lastResult = MutableStateFlow<String?>(null)
    val lastResult: StateFlow<String?> = _lastResult.asStateFlow()

    init {
        viewModelScope.launch {
            navHolder.get().subscribeToResult(RESULT_KEY).collect { result ->
                _lastResult.value = when (result) {
                    is NavigationResult.Success -> "✓ ${(result.value as? TextResult)?.text}"
                    is NavigationResult.Cancel  -> "✗ Cancelled"
                }
            }
        }
    }

    fun navigateToDetail()  = navHolder.get().navigateToInsideTab(TabDestination.PrayDetail)
    fun replaceWithDetail() = navHolder.get().navigateToInsideTab(TabDestination.PrayDetail, withReplace = true)
    fun switchToQuran()     = navHolder.get().switchTab(AppTab.Quran)
    fun showPraySheet()     = navHolder.get().showDialog(DialogDestination.PraySheet)

    fun navigateToSubDetail() = navHolder.get().navigateToInsideTab(TabDestination.PraySubDetail)
    fun back()                = navHolder.get().back()
    fun backWithResult()      = navHolder.get().back(
        NavigationResult.Success(RESULT_KEY, TextResult("From PrayDetail"))
    )

    fun backToMain() = navHolder.get().backToScreen(TabDestination.PrayMain)
    fun backToMainWithResult() = navHolder.get().backToScreen(
        key = TabDestination.PrayMain,
        navigationResult = NavigationResult.Success(RESULT_KEY, TextResult("From PraySubDetail")),
    )
}
