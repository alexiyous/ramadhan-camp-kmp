package com.iqbalwork.ramadhancamp.feature.about.presentation.model

import com.iqbalwork.ramadhancamp.shared.common.ui.UiEvent

sealed interface AboutEvent : UiEvent {
    data object NavigateToOssLicenses : AboutEvent
}
