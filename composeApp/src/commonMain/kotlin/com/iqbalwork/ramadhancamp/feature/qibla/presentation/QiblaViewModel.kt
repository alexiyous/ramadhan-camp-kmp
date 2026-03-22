package com.iqbalwork.ramadhancamp.feature.qibla.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iqbalwork.ramadhancamp.feature.bookmark.presentation.route.BookmarkTab
import com.iqbalwork.ramadhancamp.shared.common.navigation.NavigationManager
import com.iqbalwork.ramadhancamp.shared.common.navigation.DialogDestination
import com.iqbalwork.ramadhancamp.shared.common.navigation.NavigationResult
import com.iqbalwork.ramadhancamp.shared.common.navigation.TabDestination
import com.iqbalwork.ramadhancamp.shared.common.navigation.TextResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class QiblaViewModel(
    private val navController: NavigationManager,
) : ViewModel() {

    companion object { const val RESULT_KEY = "qibla_result" }

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

    fun navigateToDetail()  = navController.navigateToInsideTab(TabDestination.QiblaDetail)
    fun replaceWithDetail() = navController.navigateToInsideTab(TabDestination.QiblaDetail, withReplace = true)
    fun switchToBookmark()  = navController.switchTab(BookmarkTab)
    fun showQiblaSheet()    = navController.showDialog(DialogDestination.QiblaSheet)

    fun navigateToSubDetail() = navController.navigateToInsideTab(TabDestination.QiblaSubDetail)
    fun back()                = navController.back()
    fun backWithResult()      = navController.back(
        NavigationResult.Success(RESULT_KEY, TextResult("From QiblaDetail"))
    )

    fun backToMain() = navController.backToScreen(TabDestination.QiblaMain)
    fun backToMainWithResult() = navController.backToScreen(
        key = TabDestination.QiblaMain,
        navigationResult = NavigationResult.Success(RESULT_KEY, TextResult("From QiblaSubDetail")),
    )
}
