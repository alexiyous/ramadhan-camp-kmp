package com.iqbalwork.ramadhancamp.feature.home.presentation.locationpicker.model

import com.iqbalwork.ramadhancamp.shared.common.navigation.NavigationResultData

data class LocationResult(
    val province: String,
    val city: String,
) : NavigationResultData
