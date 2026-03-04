package com.iqbalwork.ramadhancamp.feature.home.domain.repository

import com.iqbalwork.ramadhancamp.feature.home.domain.model.LastSurahRead
import com.iqbalwork.ramadhancamp.feature.home.domain.model.NextPrayer
import dev.jordond.compass.Coordinates
import dev.jordond.compass.geolocation.GeolocatorResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow

interface HomeRepository {
    val lastSurahRead: Flow<LastSurahRead?>
    val nextPrayer: SharedFlow<NextPrayer>
    suspend fun getCurrentLocation() : Result<GeolocatorResult>
    suspend fun getCurrentCityAndProvince(coordinates: Coordinates): Result<Triple<String, String, String>>
    suspend fun getShalatSchedule(province: String, city: String): Result<Unit>
    suspend fun observerNextPrayer()
    suspend fun saveLastReadSurah(surah: LastSurahRead)
}