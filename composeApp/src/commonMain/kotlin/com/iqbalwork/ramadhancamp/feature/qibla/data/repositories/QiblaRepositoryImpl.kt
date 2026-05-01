package com.iqbalwork.ramadhancamp.feature.qibla.data.repositories

import com.iqbalwork.ramadhancamp.feature.qibla.data.datasource.QiblaPreferences
import com.iqbalwork.ramadhancamp.feature.qibla.domain.model.QiblaLocation
import com.iqbalwork.ramadhancamp.feature.qibla.domain.repository.QiblaRepository
import com.iqbalwork.ramadhancamp.shared.common.geo.CompassSensor
import com.iqbalwork.ramadhancamp.shared.common.utils.math.haversineDistanceKm
import com.iqbalwork.ramadhancamp.shared.common.utils.math.toDegrees
import com.iqbalwork.ramadhancamp.shared.common.utils.math.toRadians
import dev.jordond.compass.geocoder.Geocoder
import dev.jordond.compass.geolocation.Geolocator
import dev.jordond.compass.geolocation.GeolocatorResult
import dev.jordond.compass.Priority
import kotlinx.coroutines.flow.Flow
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin

private const val MIN_DISTANCE_KM_FOR_CACHE = 50.0

class QiblaRepositoryImpl(
    private val compassSensor: CompassSensor,
    private val geolocator: Geolocator,
    private val geocoder: Geocoder,
    private val pref: QiblaPreferences
) : QiblaRepository {

    private val kaabaLat = 21.422487
    private val kaabaLng = 39.826206

    override val compassHeading: Flow<Float>
        get() = compassSensor.heading

    override val isCompassAvailable: Boolean
        get() = compassSensor.isAvailable

    override suspend fun getQiblaLocation(): Result<QiblaLocation> = runCatching {
        val locationResult = geolocator.current(Priority.HighAccuracy)
        require(locationResult is GeolocatorResult.Success) { "Failed to get location" }

        val coordinates = locationResult.data.coordinates
        val lastLat = pref.lastLatitude
        val lastLng = pref.lastLongitude
        val distance = haversineDistanceKm(lastLat, lastLng, coordinates.latitude, coordinates.longitude)

        if (distance < MIN_DISTANCE_KM_FOR_CACHE && pref.lastCity != null) {
            return@runCatching QiblaLocation(
                cityName = pref.lastCity!!,
                bearingToKaaba = pref.lastBearing.toFloat(),
                distanceToKaabaKm = pref.lastDistance.toFloat()
            )
        }

        val place = geocoder.reverse(coordinates.latitude, coordinates.longitude).getOrNull()?.firstOrNull()
        val cityName = place?.let {
            it.locality ?: it.subAdministrativeArea ?: it.administrativeArea ?: "Unknown Location"
        } ?: "Unknown Location"

        val lat1 = toRadians(coordinates.latitude)
        val lon1 = toRadians(coordinates.longitude)
        val lat2 = toRadians(kaabaLat)
        val lon2 = toRadians(kaabaLng)

        val y = sin(lon2 - lon1) * cos(lat2)
        val x = cos(lat1) * sin(lat2) - sin(lat1) * cos(lat2) * cos(lon2 - lon1)

        var bearing = toDegrees(atan2(y, x)).toFloat()
        bearing = (bearing + 360) % 360

        val R = 6371.0
        val dLat = lat2 - lat1
        val dLon = lon2 - lon1
        val a = sin(dLat / 2) * sin(dLat / 2) +
                cos(lat1) * cos(lat2) *
                sin(dLon / 2) * sin(dLon / 2)
        val c = 2 * atan2(kotlin.math.sqrt(a), kotlin.math.sqrt(1 - a))
        val distanceKm = (R * c).toFloat()

        pref.lastLatitude = coordinates.latitude
        pref.lastLongitude = coordinates.longitude
        pref.lastCity = cityName
        pref.lastBearing = bearing.toDouble()
        pref.lastDistance = distanceKm.toDouble()

        QiblaLocation(
            cityName = cityName,
            bearingToKaaba = bearing,
            distanceToKaabaKm = distanceKm
        )
    }

    override fun startCompass() = compassSensor.start()
    override fun stopCompass() = compassSensor.stop()
}
