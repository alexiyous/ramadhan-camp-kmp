package com.iqbalwork.ramadhancamp.feature.qibla.domain.repository

import com.iqbalwork.ramadhancamp.feature.qibla.domain.model.QiblaLocation
import kotlinx.coroutines.flow.Flow

interface QiblaRepository {
    val compassHeading: Flow<Float>
    val isCompassAvailable: Boolean
    suspend fun getQiblaLocation(): Result<QiblaLocation>
    fun startCompass()
    fun stopCompass()
}
