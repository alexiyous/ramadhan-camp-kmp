package com.iqbalwork.ramadhancamp.shared.common.network

import com.iqbalwork.shared.platform
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.HttpRequestRetry
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

private const val DEFAULT_URL = "https://equran.id/api/"

object HttpClientFactory {
    fun create(engine: HttpClientEngine): HttpClient {
        return HttpClient(engine) {
            install(Logging) {
                logger = Logger.SIMPLE
                level = LogLevel.HEADERS
            }
            install(HttpTimeout) {
                socketTimeoutMillis = 15_000
                requestTimeoutMillis = 15_000
            }

            defaultRequest {
                url(DEFAULT_URL)
                headers {
                    append("Content-Type", "application/json")
                    append("Accept", "application/json")
                    append("Accept", "text/plain")
                }
            }

            install(HttpRequestRetry) {
                retryOnException(maxRetries = 1, retryOnTimeout = true)
                retryOnServerErrors(maxRetries = 1)

                exponentialDelay()
            }

            install(ContentNegotiation) {
                val jsonConfig = Json {
                    ignoreUnknownKeys = true
                    prettyPrint = true
                }

                json(jsonConfig, ContentType.Application.Json)
                json(jsonConfig, ContentType.Text.Plain)
            }
        }
    }
}