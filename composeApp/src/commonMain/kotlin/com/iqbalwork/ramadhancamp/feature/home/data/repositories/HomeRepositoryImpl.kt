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
import dev.jordond.compass.geocoder.Geocoder
import dev.jordond.compass.geolocation.Geolocator
import dev.jordond.compass.geolocation.GeolocatorResult
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
            geolocator.current()
        }

    override suspend fun getCurrentCityAndProvince(coordinates: Coordinates): Result<Pair<String, String>> = runCatching {
        val lastLat = pref.lastLatitude
        val lastLng = pref.lastLongitude
        val distance = haversineDistanceKm(lastLat, lastLng, coordinates.latitude, coordinates.longitude)

        // If within 50km and we have a cached city, reuse it
       val result =
           if (distance < MIN_DISTANCE_KM_FOR_CACHE && !pref.lastCity.isNullOrBlank())  pref.lastCity!! to pref.lastProvince!!
        else {
               val place = geocoder
                   .reverse(coordinates.latitude, coordinates.longitude)
                   .getFirstOrNull() ?: error("Failed to geocode")

               val province = place.administrativeArea ?: error("Province not found")
               val city  = place.subAdministrativeArea ?: error("City not found")

               pref.lastLatitude  = coordinates.latitude
               pref.lastLongitude = coordinates.longitude
               pref.lastCity      = city
               pref.lastProvince  = province

               city to province
           }

        result
    }

    override suspend fun getShalatSchedule(
        province: String,
        city: String
    ): Result<Unit> = runCatching {
        currentShalatSchedule = homeRemoteDatasource.getShalatSchedule(province, city).getOrThrow()
    }

    override suspend fun observerNextPrayer() {
        while (true) {
            val now = Clock.System.now()
            val nowLocal = now.toLocalDateTime(TimeZone.currentSystemDefault())

            currentShalatSchedule?.data?.jadwal
                ?.find { it.tanggal == nowLocal.day }
                ?.let { today -> _nextPrayer.tryEmit(today.nextPrayer(nowLocal)) }

            val secondsUntilNextMinute = 60 - nowLocal.second
            delay(secondsUntilNextMinute * 1000L)
        }
    }

    override suspend fun saveLastReadSurah(surah: LastSurahRead) {
        pref.surahName.set(surah.surahName)
        pref.lastAyatNumber.set(surah.ayatNumber)
        pref.lastDateRead.set(surah.readDate)
    }
}