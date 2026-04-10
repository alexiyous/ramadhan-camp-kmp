package com.iqbalwork.ramadhancamp.feature.home.data.repositories

import com.iqbalwork.ramadhancamp.feature.home.data.datasource.HomePreferences
import com.iqbalwork.ramadhancamp.feature.home.data.datasource.HomeRemoteDatasource
import com.iqbalwork.ramadhancamp.feature.home.data.mapper.nextPrayer
import com.iqbalwork.ramadhancamp.feature.home.data.model.shalatSchedule.ShalatScheduleDto
import com.iqbalwork.ramadhancamp.feature.home.domain.model.LastSurahRead
import com.iqbalwork.ramadhancamp.feature.home.domain.model.NextPrayer
import com.iqbalwork.ramadhancamp.feature.home.domain.model.Surah
import com.iqbalwork.ramadhancamp.feature.home.domain.repository.HomeRepository
import com.iqbalwork.ramadhancamp.shared.common.utils.math.haversineDistanceKm
import dev.jordond.compass.Coordinates
import dev.jordond.compass.Priority
import dev.jordond.compass.geocoder.Geocoder
import dev.jordond.compass.geolocation.Geolocator
import dev.jordond.compass.geolocation.GeolocatorResult
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.TimeZone
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock

private const val MIN_DISTANCE_KM_FOR_CACHE = 50.0

@OptIn(ExperimentalCoroutinesApi::class)
class HomeRepositoryImpl(
    private val geolocator: Geolocator,
    private val geocoder: Geocoder,
    private val pref: HomePreferences,
    private val homeRemoteDatasource: HomeRemoteDatasource,
): HomeRepository {

    private val popularSurahNumbers = arrayOf(36, 67, 18)
    private val scheduleFlow = MutableStateFlow<ShalatScheduleDto?>(null)

    override val nextPrayer: Flow<NextPrayer> = scheduleFlow
        .flatMapLatest { schedule ->
            flow {
                while (true) {
                    val now = Clock.System.now()
                    val tz = TimeZone.currentSystemDefault()
                    val nowLocal = now.toLocalDateTime(tz)
                    val tomorrowLocal = now.plus(1, DateTimeUnit.DAY, tz).toLocalDateTime(tz)

                    val todayJadwal = schedule?.data?.jadwal?.find { it.tanggal == nowLocal.day }
                    val tomorrowJadwal = schedule?.data?.jadwal?.find { it.tanggal == tomorrowLocal.day }

                    todayJadwal?.let { emit(it.nextPrayer(nowLocal, tomorrowJadwal)) }
                    val secondsUntilNextMinute = 60 - nowLocal.second
                    delay(secondsUntilNextMinute * 1000L)
                }
            }
        }

    override val lastSurahRead: Flow<LastSurahRead?> = combine(
        flow = pref.surahNameStateFlow(),
        flow2 = pref.lastAyatNumberStateFlow(),
        flow3 = pref.lastDateReadStateFlow()
    ) { name, ayat, date ->
        if (name != null && ayat != null && date != null) LastSurahRead(name, ayat, date)
        else null
    }

    override suspend fun getCurrentCoordinate(): Result<GeolocatorResult> =
        runCatching {
            geolocator.current(Priority.HighAccuracy)
        }


    override suspend fun getCurrentPlace(coordinates: Coordinates): Result<Triple<String, String, String>> = runCatching {
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

                Triple(city, province, country)
           }
        result
    }

    override suspend fun getShalatSchedule(
        province: String,
        city: String
    ): Result<Unit> = runCatching {
        scheduleFlow.value = homeRemoteDatasource.getShalatSchedule(province, city).getOrThrow()
    }

    override suspend fun getProvinces(): Result<List<String>> =
        homeRemoteDatasource.getProvinces().map { it.data }

    override suspend fun getKabKota(provinsi: String): Result<List<String>> =
        homeRemoteDatasource.getKabKota(provinsi).map { it.data }

    override suspend fun saveManualLocation(province: String, city: String) {
        pref.lastCity = city
        pref.lastProvince = province
        pref.lastCountry = "Indonesia"
        // Clear coordinates so the 50 km cache doesn't suppress the next geocode attempt
        pref.lastLatitude = 0.0
        pref.lastLongitude = 0.0
    }

    override suspend fun getPopularSurah(): Result<List<Surah>> {
        return homeRemoteDatasource.getSurahList().map { surahDto ->
            surahDto.data
                .filter { popularSurahNumbers.contains(it.nomor) }
                .map {
                    Surah(
                        it.nomor,
                        it.nama,
                        it.namaLatin,
                        it.jumlahAyat,
                        it.tempatTurun
                    )
                }
        }
    }
}