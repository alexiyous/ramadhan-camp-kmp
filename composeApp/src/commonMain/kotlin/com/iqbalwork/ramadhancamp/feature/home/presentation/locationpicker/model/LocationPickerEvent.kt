package com.iqbalwork.ramadhancamp.feature.home.presentation.locationpicker.model

import com.iqbalwork.ramadhancamp.shared.common.ui.UiEvent

sealed interface LocationPickerEvent : UiEvent {
    data object LoadProvinces : LocationPickerEvent
    data class SelectProvince(val province: String) : LocationPickerEvent
    data class SelectCity(val city: String) : LocationPickerEvent
    data object Confirm : LocationPickerEvent
    data object Cancel : LocationPickerEvent
}
