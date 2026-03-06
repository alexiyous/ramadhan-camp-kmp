package com.iqbalwork.ramadhancamp.feature.home.data.repositories

import com.iqbalwork.ramadhancamp.feature.home.data.datasource.HomePreferences
import com.iqbalwork.ramadhancamp.feature.home.data.datasource.HomeRemoteDatasource
import com.iqbalwork.ramadhancamp.feature.home.data.mapper.nextPrayer
import com.iqbalwork.ramadhancamp.feature.home.data.model.ShalatScheduleDto
import com.iqbalwork.ramadhancamp.feature.home.domain.model.LastSurahRead
import com.iqbalwork.ramadhancamp.feature.home.domain.model.NextPrayer
import com.iqbalwork.ramadhancamp.feature.home.domain.repository.HomeRepository
import com.iqbalwork.ramadhancamp.shared.common.utils.math.haversineDistanceKm
import dev.jordond.compass.Coordinates
import dev.jordond.compass.Place
import dev.jordond.compass.Priority
import dev.jordond.compass.geocoder.Geocoder
import dev.jordond.compass.geolocation.Geolocator
import dev.jordond.compass.geolocation.GeolocatorResult
import io.github.aakira.napier.log
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock

private const val MIN_DISTANCE_KM_FOR_CACHE = 50.0

class HomeRepositoryImpl(
    private val geolocator: Geolocator,
    private val geocoder: Geocoder,
    private val pref: HomePreferences,
    private val homeRemoteDatasource: HomeRemoteDatasource,
): HomeRepository {
    private val _nextPrayer = MutableSharedFlow<NextPrayer>(
        replay = 1,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    override val nextPrayer = _nextPrayer.asSharedFlow()

    override val lastSurahRead: Flow<LastSurahRead?> = combine(
        flow = pref.surahName.flow,
        flow2 = pref.lastAyatNumber.flow,
        flow3 = pref.lastDateRead.flow,
    ) { name, ayat, date ->
        if (name != null && ayat != null && date != null) LastSurahRead(name, ayat, date)
        else null
    }

    private var currentShalatSchedule: ShalatScheduleDto? = null

    override suspend fun getCurrentLocation(): Result<GeolocatorResult> =
        runCatching {
            geolocator.current(Priority.HighAccuracy)
        }

    override suspend fun getCurrentCityAndProvince(coordinates: Coordinates): Result<Triple<String, String, String>> = runCatching {
        val lastLat = pref.lastLatitude
        val lastLng = pref.lastLongitude
        val distance = haversineDistanceKm(lastLat, lastLng, coordinates.latitude, coordinates.longitude)

        // If within 50km and we have a cached city, reuse it
       val result =
           if (distance < MIN_DISTANCE_KM_FOR_CACHE && !pref.lastCity.isNullOrBlank()) Triple(pref.lastCity!!, pref.lastProvince!!, pref.lastCountry!!)
        else {
               val place = geocoder
                   .reverse(coordinates.latitude, coordinates.longitude)
                   .getFirstOrNull() ?: error("Failed to geocode")

               val province = place.administrativeArea ?: error("Province not found")
               val city  = place.subAdministrativeArea ?: error("City not found")
               val country = place.country ?: error("Country not found")

               pref.lastLatitude  = coordinates.latitude
               pref.lastLongitude = coordinates.longitude
               pref.lastCity      = city
               pref.lastProvince  = province
               pref.lastCountry   = country

                Triple("Kota $city", province, country)
           }
        result
    }

    override suspend fun getShalatSchedule(
        province: String,
        city: String
    ): Result<Unit> = runCatching {
        currentShalatSchedule = homeRemoteDatasource.getShalatSchedule(province, city).getOrThrow()
        log { "SHALAT SCHEDULE $currentShalatSchedule" }
    }

    override suspend fun observerNextPrayer() {
        while (true) {
            val now = Clock.System.now()
            val nowLocal = now.toLocalDateTime(TimeZone.currentSystemDefault())

            log { "SCHEDULE ${ currentShalatSchedule?.data?.jadwal
                ?.find { it.tanggal == nowLocal.day }}" }

            currentShalatSchedule?.data?.jadwal
                ?.find { it.tanggal == nowLocal.day }
                ?.let { today ->
                    _nextPrayer.tryEmit(today.nextPrayer(nowLocal))
                    log { "SHALAT  $today" }
                }

            val secondsUntilNextMinute = 60 - nowLocal.second
            delay(secondsUntilNextMinute * 1000L)
        }
    }

    override suspend fun saveLastReadSurah(surah: LastSurahRead) {
        pref.surahName.set(surah.surahName)
        pref.lastAyatNumber.set(surah.ayatNumber)
        pref.lastDateRead.set(surah.readDate)
    }

    override suspend fun getProvinces(): Result<List<String>> =
        homeRemoteDatasource.getProvinces().map { it.data }

    override suspend fun getKabKota(provinsi: String): Result<List<String>> =
        homeRemoteDatasource.getKabKota(provinsi).map { it.data }

    override suspend fun saveManualLocation(province: String, city: String) {
        pref.lastCity = city
        pref.lastProvince = province
        // Clear coordinates so the 50 km cache doesn't suppress the next geocode attempt
        pref.lastLatitude = 0.0
        pref.lastLongitude = 0.0
    }
}