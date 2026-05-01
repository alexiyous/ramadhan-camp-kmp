package com.iqbalwork.ramadhancamp.shared.common.geo.di

import com.iqbalwork.ramadhancamp.shared.common.geo.createCompassSensor
import org.koin.core.module.Module
import org.koin.dsl.module

expect val platformGeoModule: Module

val geoModule = module {
    includes(platformGeoModule)
    factory { createCompassSensor() }
}
