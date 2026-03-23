package com.iqbalwork.ramadhancamp.feature.home.presentation.locationpicker.model

import com.iqbalwork.ramadhancamp.shared.common.navigation.NavigationResultData
import kotlinx.serialization.Serializable

@Serializable
data class LocationResult(
    val country: String,
    val province: String,
    val city: String,
)
