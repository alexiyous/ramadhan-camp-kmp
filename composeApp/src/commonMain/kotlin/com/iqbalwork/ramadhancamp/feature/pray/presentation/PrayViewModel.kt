package com.iqbalwork.ramadhancamp.feature.pray.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iqbalwork.ramadhancamp.feature.quran.presentation.route.QuranTab
import com.iqbalwork.ramadhancamp.shared.common.navigation.NavigationManager
import com.iqbalwork.ramadhancamp.shared.common.navigation.DialogDestination
import com.iqbalwork.ramadhancamp.shared.common.navigation.NavigationResult
import com.iqbalwork.ramadhancamp.shared.common.navigation.TabDestination
import com.iqbalwork.ramadhancamp.shared.common.navigation.TextResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PrayViewModel(
    private val navController: NavigationManager,
) : ViewModel() {

    companion object { const val RESULT_KEY = "pray_result" }

    private val _lastResult = MutableStateFlow<String?>(null)
    val lastResult: StateFlow<String?> = _lastResult.asStateFlow()

    init {
        viewModelScope.launch {
            navController.subscribeToResult(RESULT_KEY).collect { result ->
                _lastResult.value = when (result) {
                    is NavigationResult.Success -> "✓ ${(result.value as? TextResult)?.text}"
                    is NavigationResult.Cancel  -> "✗ Cancelled"
                }
            }
        }
    }

    fun navigateToDetail()  = navController.navigateToInsideTab(TabDestination.PrayDetail)
    fun replaceWithDetail() = navController.navigateToInsideTab(TabDestination.PrayDetail, withReplace = true)
    fun switchToQuran()     = navController.switchTab(QuranTab)
    fun showPraySheet()     = navController.showDialog(DialogDestination.PraySheet)

    fun navigateToSubDetail() = navController.navigateToInsideTab(TabDestination.PraySubDetail)
    fun back()                = navController.back()
    fun backWithResult()      = navController.back(
        NavigationResult.Success(RESULT_KEY, TextResult("From PrayDetail"))
    )

    fun backToMain() = navController.backToScreen(TabDestination.PrayMain)
    fun backToMainWithResult() = navController.backToScreen(
        key = TabDestination.PrayMain,
        navigationResult = NavigationResult.Success(RESULT_KEY, TextResult("From PraySubDetail")),
    )
}
