package com.iqbalwork.ramadhancamp.feature.quran.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iqbalwork.ramadhancamp.feature.qibla.presentation.route.QiblaTab
import com.iqbalwork.ramadhancamp.shared.common.navigation.NavigationManager
import com.iqbalwork.ramadhancamp.shared.common.navigation.DialogDestination
import com.iqbalwork.ramadhancamp.shared.common.navigation.NavigationResult
import com.iqbalwork.ramadhancamp.shared.common.navigation.TabDestination
import com.iqbalwork.ramadhancamp.shared.common.navigation.TextResult
import io.github.aakira.napier.log
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class QuranViewModel(
    private val navController: NavigationManager,
) : ViewModel() {

    companion object { const val RESULT_KEY = "quran_result" }

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

    fun navigateToDetail()  {
        log { "${navController.hashCode()}" }
        navController.navigateToInsideTab(TabDestination.QuranDetail)
    }
    fun replaceWithDetail() = navController.navigateToInsideTab(TabDestination.QuranDetail, withReplace = true)
    fun switchToQibla()     = navController.switchTab(QiblaTab)
    fun showQuranSheet()    = navController.showDialog(DialogDestination.QuranSheet)

    fun navigateToSubDetail() = navController.navigateToInsideTab(TabDestination.QuranSubDetail)
    fun back()                = navController.back()
    fun backWithResult()      = navController.back(
        NavigationResult.Success(RESULT_KEY, TextResult("From QuranDetail"))
    )

    fun backToMain() = navController.backToScreen(TabDestination.QuranMain)
    fun backToMainWithResult() = navController.backToScreen(
        key = TabDestination.QuranMain,
        navigationResult = NavigationResult.Success(RESULT_KEY, TextResult("From QuranSubDetail")),
    )
}
