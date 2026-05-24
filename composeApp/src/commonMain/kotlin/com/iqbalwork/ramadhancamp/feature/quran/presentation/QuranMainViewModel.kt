package com.iqbalwork.ramadhancamp.feature.quran.presentation

import androidx.lifecycle.viewModelScope
import com.iqbalwork.ramadhancamp.feature.quran.domain.repository.QuranRepository
import com.iqbalwork.ramadhancamp.feature.quran.presentation.model.QuranMainEffect
import com.iqbalwork.ramadhancamp.feature.quran.presentation.model.QuranMainEvent
import com.iqbalwork.ramadhancamp.feature.quran.presentation.model.QuranMainState
import com.iqbalwork.ramadhancamp.shared.common.navigation.NavigationManager
import com.iqbalwork.ramadhancamp.shared.common.navigation.NavigationResult
import com.iqbalwork.ramadhancamp.shared.common.navigation.NavigationResultData
import com.iqbalwork.ramadhancamp.shared.common.navigation.LastSurahNavigationData
import com.iqbalwork.ramadhancamp.shared.common.navigation.SurahNavigationData
import com.iqbalwork.ramadhancamp.shared.common.navigation.TabDestination
import com.iqbalwork.ramadhancamp.shared.common.ui.BaseViewModel
import com.iqbalwork.ramadhancamp.shared.common.utils.AppError
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class QuranMainViewModel(
    navigationManager: NavigationManager,
    private val quranRepository: QuranRepository
) : BaseViewModel<Unit, QuranMainState, QuranMainEvent, QuranMainEffect>(
    Unit, QuranMainState(), navigationManager,
    resultKeys = arrayOf("focus_search", "navigate_to_last_ayah", "navigate_to_surah", "navigate_to_bookmark_ayah")
) {

    private var searchJob: Job? = null

    init {
        loadSurahs()
    }

    override fun navigationResultSuccess(key: String, data: NavigationResultData?) {
        super.navigationResultSuccess(key, data)
        when (key) {
            "focus_search" -> {
                navigationManager.backToScreen(TabDestination.QuranMain)
                updateState { copy(focusSearch = true) }
            }
            "navigate_to_last_ayah" -> {
                val navData = data as? LastSurahNavigationData ?: return
                navigationManager.backToScreen(TabDestination.QuranMain)
                navigationManager.navigateToInsideTab(
                    TabDestination.QuranDetail(
                        QuranDetailScreenParameters(
                            surahId = navData.surahId,
                            scrollToAyat = navData.ayatNumber
                        )
                    )
                )
            }
            "navigate_to_surah" -> {
                val navData = data as? SurahNavigationData ?: return
                navigationManager.backToScreen(TabDestination.QuranMain)
                navigationManager.navigateToInsideTab(
                    TabDestination.QuranDetail(
                        QuranDetailScreenParameters(surahId = navData.surahId)
                    )
                )
            }
            "navigate_to_bookmark_ayah" -> {
                val navData = data as? LastSurahNavigationData ?: return
                navigationManager.backToScreen(TabDestination.QuranMain)
                navigationManager.navigateToInsideTab(
                    TabDestination.QuranDetail(
                        QuranDetailScreenParameters(
                            surahId = navData.surahId,
                            scrollToAyat = navData.ayatNumber
                        )
                    )
                )
            }
        }
    }

    private fun loadSurahs() {
        updateState { copy(isLoading = true, appError = null) }
        viewModelScope.launch {
            quranRepository.getSurahs()
                .onSuccess { surahs ->
                    updateState { copy(isLoading = false, surahs = surahs) }
                }
                .onFailure {
                    updateState { copy(isLoading = false, appError = it as? AppError ?: AppError.UnexpectedError("Unknown error", it)) }
                }
        }
    }

    override fun handleEvent(event: QuranMainEvent) {
        when (event) {
            is QuranMainEvent.Search -> {
                updateState { copy(searchQuery = event.query) }
                searchJob?.cancel()
                searchJob = viewModelScope.launch {
                    delay(500) // debounce
                    if (event.query.isBlank()) {
                        updateState { copy(searchResults = null, isLoading = false) }
                        loadSurahs()
                    } else {
                        updateState { copy(isLoading = true, appError = null) }
                        quranRepository.search(event.query)
                            .onSuccess { results ->
                                updateState { copy(isLoading = false, searchResults = results) }
                            }
                            .onFailure {
                                updateState { copy(isLoading = false, appError = it as? AppError ?: AppError.UnexpectedError("Unknown error", it)) }
                            }
                    }
                }
            }
            is QuranMainEvent.SurahClicked -> {
                navigationManager.navigateToInsideTab(
                    TabDestination.QuranDetail(QuranDetailScreenParameters(event.surahId))
                )
            }
            is QuranMainEvent.AyatClicked -> {
                navigationManager.navigateToInsideTab(
                    TabDestination.QuranDetail(
                        QuranDetailScreenParameters(
                            surahId = event.surahNumber,
                            scrollToAyat = event.ayatNumber
                        )
                    )
                )
            }
            is QuranMainEvent.FocusSearchConsumed -> {
                updateState { copy(focusSearch = false) }
            }
            is QuranMainEvent.Retry -> {
                if (state.value.searchQuery.isNotBlank()) {
                    handleEvent(QuranMainEvent.Search(state.value.searchQuery))
                } else {
                    loadSurahs()
                }
            }
        }
    }
}