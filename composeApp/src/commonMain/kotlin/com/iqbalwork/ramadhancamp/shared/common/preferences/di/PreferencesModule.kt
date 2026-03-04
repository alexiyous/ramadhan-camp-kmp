package com.iqbalwork.ramadhancamp.shared.common.preferences.di

import com.iqbalwork.ramadhancamp.shared.common.preferences.data.AppPreferencesImpl
import com.iqbalwork.ramadhancamp.shared.common.preferences.domain.AppPreferences
import com.russhwolf.settings.ObservableSettings
import com.russhwolf.settings.Settings
import org.koin.dsl.module

val preferencesModule = module {
    single<ObservableSettings> { Settings() as ObservableSettings }
    single<AppPreferences> { AppPreferencesImpl(get()) }
}