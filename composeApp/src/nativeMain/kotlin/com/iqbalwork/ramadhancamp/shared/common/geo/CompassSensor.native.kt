package com.iqbalwork.ramadhancamp.shared.common.geo

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import platform.CoreLocation.CLHeading
import platform.CoreLocation.CLLocationManager
import platform.CoreLocation.CLLocationManagerDelegateProtocol
import platform.Foundation.NSError
import platform.darwin.NSObject

class IOSCompassSensor : CompassSensor {

    private val locationManager = CLLocationManager()
    
    private val delegate = object : NSObject(), CLLocationManagerDelegateProtocol {
        override fun locationManager(manager: CLLocationManager, didUpdateHeading: CLHeading) {
            _heading.value = didUpdateHeading.trueHeading.toFloat()
        }
    }

    private val _heading = MutableStateFlow(0f)
    override val heading: Flow<Float> = _heading.asStateFlow()
    override val isAvailable: Boolean = CLLocationManager.headingAvailable()

    init {
        locationManager.delegate = delegate
    }

    override fun start() {
        if (CLLocationManager.headingAvailable()) {
            locationManager.startUpdatingHeading()
        }
    }

    override fun stop() {
        if (CLLocationManager.headingAvailable()) {
            locationManager.stopUpdatingHeading()
        }
    }
}

actual fun createCompassSensor(): CompassSensor = IOSCompassSensor()
