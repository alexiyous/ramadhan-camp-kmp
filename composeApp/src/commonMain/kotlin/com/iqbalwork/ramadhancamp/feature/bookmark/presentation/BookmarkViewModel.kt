package com.iqbalwork.ramadhancamp.feature.bookmark.presentation

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

class BookmarkViewModel(
    private val navHolder: AppNavigationControllerHolder,
) : ViewModel() {

    companion object { const val RESULT_KEY = "bookmark_result" }

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

    fun navigateToDetail()  = navHolder.get().navigateToInsideTab(TabDestination.BookmarkDetail)
    fun replaceWithDetail() = navHolder.get().navigateToInsideTab(TabDestination.BookmarkDetail, withReplace = true)
    fun switchToHome()      = navHolder.get().switchTab(AppTab.Home)
    fun showBookmarkSheet() = navHolder.get().showDialog(DialogDestination.BookmarkSheet)

    fun navigateToSubDetail() = navHolder.get().navigateToInsideTab(TabDestination.BookmarkSubDetail)
    fun back()                = navHolder.get().back()
    fun backWithResult()      = navHolder.get().back(
        NavigationResult.Success(RESULT_KEY, TextResult("From BookmarkDetail"))
    )

    fun backToMain() = navHolder.get().backToScreen(TabDestination.BookmarkMain)
    fun backToMainWithResult() = navHolder.get().backToScreen(
        key = TabDestination.BookmarkMain,
        navigationResult = NavigationResult.Success(RESULT_KEY, TextResult("From BookmarkSubDetail")),
    )
}
