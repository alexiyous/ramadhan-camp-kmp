package com.iqbalwork.ramadhancamp.feature.home.data.datasource

import com.iqbalwork.ramadhancamp.feature.home.data.model.ShalatScheduleDto
import com.iqbalwork.ramadhancamp.feature.home.data.model.StringListResponseDto
import com.iqbalwork.ramadhancamp.shared.common.network.safeApiCall
import io.ktor.client.HttpClient
import io.ktor.client.request.setBody
import io.ktor.http.HttpMethod
import io.ktor.http.path

class HomeRemoteDatasource(
    private val httpClient: HttpClient
) {
    suspend fun getShalatSchedule(province: String, city: String): Result<ShalatScheduleDto> {
        return httpClient.safeApiCall {
            method = HttpMethod.Post
            url {
                path("v2/shalat")
            }
            setBody(mapOf("provinsi" to province, "kabkota" to city))
        }
    }

    suspend fun getProvinces(): Result<StringListResponseDto> {
        return httpClient.safeApiCall {
            method = HttpMethod.Get
            url { path("v2/shalat/provinsi") }
        }
    }

    suspend fun getKabKota(provinsi: String): Result<StringListResponseDto> {
        return httpClient.safeApiCall {
            method = HttpMethod.Post
            url { path("v2/shalat/kabkota") }
            setBody(mapOf("provinsi" to provinsi))
        }
    }
}