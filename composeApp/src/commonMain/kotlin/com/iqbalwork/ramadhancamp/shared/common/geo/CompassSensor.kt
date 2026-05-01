package com.iqbalwork.ramadhancamp.shared.common.geo

import kotlinx.coroutines.flow.Flow

interface CompassSensor {
    val heading: Flow<Float>
    val isAvailable: Boolean
    fun start()
    fun stop()
}

expect fun createCompassSensor(): CompassSensor
