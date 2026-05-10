package com.iqbalwork.ramadhancamp.feature.quran.presentation.model

import com.iqbalwork.ramadhancamp.feature.quran.domain.model.SearchResult
import com.iqbalwork.ramadhancamp.feature.quran.domain.model.Surah
import com.iqbalwork.ramadhancamp.shared.common.ui.UiEffect
import com.iqbalwork.ramadhancamp.shared.common.ui.UiEvent
import com.iqbalwork.ramadhancamp.shared.common.utils.AppError

data class QuranMainState(
    val isLoading: Boolean = false,
    val appError: AppError? = null,
    val surahs: List<Surah> = emptyList(),
    val searchResults: List<SearchResult>? = null,
    val searchQuery: String = "",
    val focusSearch: Boolean = false
)

sealed interface QuranMainEvent : UiEvent {
    data class Search(val query: String) : QuranMainEvent
    data class SurahClicked(val surahId: Int) : QuranMainEvent
    data class AyatClicked(val surahNumber: Int, val ayatNumber: Int) : QuranMainEvent
    data object FocusSearchConsumed : QuranMainEvent
    data object Retry : QuranMainEvent
}

sealed interface QuranMainEffect : UiEffect
