package com.iqbalwork.ramadhancamp.feature.home.presentation.model

import com.iqbalwork.ramadhancamp.shared.common.ui.UiEvent

sealed interface HomeEvent: UiEvent {
    data object LoadInitialData: HomeEvent
}