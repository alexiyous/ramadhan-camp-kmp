package com.iqbalwork.ramadhancamp.feature.qibla.presentation.model

import com.iqbalwork.ramadhancamp.shared.common.ui.UiEffect
import com.iqbalwork.ramadhancamp.shared.common.ui.UiEvent

data class QiblaState(
    val isLoading: Boolean = false,
    val cityName: String = "",
    val bearingToKaaba: Float? = null,
    val currentHeading: Float = 0f,
    val distanceKm: Float = 0f,
    val hasLocationPermission: Boolean = true,
    val isCompassAvailable: Boolean = true
)

sealed interface QiblaEvent : UiEvent {
    data object RequestLocation : QiblaEvent
}

sealed interface QiblaEffect : UiEffect
