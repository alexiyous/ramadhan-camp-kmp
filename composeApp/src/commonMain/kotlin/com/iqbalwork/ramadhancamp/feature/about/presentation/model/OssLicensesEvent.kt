package com.iqbalwork.ramadhancamp.feature.about.presentation.model

import com.iqbalwork.ramadhancamp.shared.common.ui.UiEvent

sealed interface OssLicensesEvent : UiEvent {
    data object LoadLicenses : OssLicensesEvent
    data object NavigateBack : OssLicensesEvent
}
