package com.iqbalwork.ramadhancamp.feature.quran.presentation.model

import com.iqbalwork.ramadhancamp.shared.common.ui.UiEffect
import com.iqbalwork.ramadhancamp.shared.common.ui.UiEvent

data class QuranSheetState(
    val isBookmarked: Boolean = false,
    val isBookmarking: Boolean = false,
    val bookmarkMessage: String? = null
)

sealed interface QuranSheetEvent : UiEvent {
    data object PlayAudio : QuranSheetEvent
    data object Bookmark : QuranSheetEvent
    data object Share : QuranSheetEvent
    data object Copy : QuranSheetEvent
    data object Dismiss : QuranSheetEvent
    data object BookmarkSuccessHandled : QuranSheetEvent
}

sealed interface QuranSheetEffect : UiEffect {
    data class CopyToClipboard(val text: String) : QuranSheetEffect
}
