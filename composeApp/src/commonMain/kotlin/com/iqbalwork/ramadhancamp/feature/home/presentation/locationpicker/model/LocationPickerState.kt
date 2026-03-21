package com.iqbalwork.ramadhancamp.feature.home.presentation.locationpicker.model

import com.iqbalwork.ramadhancamp.shared.common.utils.AppError

data class LocationPickerState(
    val isLoading: Boolean = false,
    val provinces: List<String> = emptyList(),
    val cities: List<String> = emptyList(),
    val selectedProvince: String? = null,
    val selectedCity: String? = null,
    val error: AppError? = null,
)

val LocationPickerState.canConfirm get() = selectedProvince != null && selectedCity != null
