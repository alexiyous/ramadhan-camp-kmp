package com.iqbalwork.ramadhancamp.feature.home.presentation.model

import com.iqbalwork.ramadhancamp.shared.common.ui.UiEvent

sealed interface HomeEvent: UiEvent {
    data object LoadInitialData: HomeEvent
    data object GoToSetting: HomeEvent
    data object NavigateToLocationPicker: HomeEvent
    data object OnSearchBoxClicked: HomeEvent
    data object OnLastSurahClicked : HomeEvent
    data class OnPopularSurahClicked(val surahId: Int) : HomeEvent
    data object NavigateToAbout: HomeEvent
}
