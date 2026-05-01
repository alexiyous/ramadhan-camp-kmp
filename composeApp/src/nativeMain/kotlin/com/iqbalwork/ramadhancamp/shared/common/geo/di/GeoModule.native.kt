package com.iqbalwork.ramadhancamp.shared.common.geo.di
import dev.jordond.compass.geocoder.Geocoder
import dev.jordond.compass.geocoder.mobile.MobilePlatformGeocoder
import dev.jordond.compass.geolocation.Geolocator
import dev.jordond.compass.geolocation.mobile
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformGeoModule: Module = module {
    factory<Geocoder> { Geocoder(MobilePlatformGeocoder()) }
    factory<Geolocator> { Geolocator.mobile() }
}
