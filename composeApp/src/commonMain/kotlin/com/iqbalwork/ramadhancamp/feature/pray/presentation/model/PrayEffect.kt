package com.iqbalwork.ramadhancamp.feature.pray.presentation.model

import com.iqbalwork.ramadhancamp.shared.common.ui.UiEffect

sealed interface PrayEffect : UiEffect {
    data object ShowPermissionsDeniedDialog : PrayEffect
}
