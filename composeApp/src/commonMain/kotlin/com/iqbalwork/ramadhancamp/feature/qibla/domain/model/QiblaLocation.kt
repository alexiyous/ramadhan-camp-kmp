package com.iqbalwork.ramadhancamp.feature.qibla.domain.model

data class QiblaLocation(
    val cityName: String,
    val bearingToKaaba: Float,
    val distanceToKaabaKm: Float
)
