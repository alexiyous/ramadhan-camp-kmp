package com.iqbalwork.ramadhancamp.shared.common.network.di

import com.iqbalwork.ramadhancamp.shared.common.network.HttpClientFactory
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import org.koin.dsl.module

val networkModule = module {
    single<HttpClient> { HttpClientFactory.create(get<HttpClientEngine>()) }
}