package com.iqbalwork.ramadhancamp.shared.common.preferences.data

import com.iqbalwork.ramadhancamp.shared.common.preferences.domain.ScopedPreferences
import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.ObservableSettings
import com.russhwolf.settings.coroutines.getBooleanFlow
import com.russhwolf.settings.coroutines.getBooleanOrNullFlow
import com.russhwolf.settings.coroutines.getDoubleFlow
import com.russhwolf.settings.coroutines.getDoubleOrNullFlow
import com.russhwolf.settings.coroutines.getIntFlow
import com.russhwolf.settings.coroutines.getIntOrNullFlow
import com.russhwolf.settings.coroutines.getLongFlow
import com.russhwolf.settings.coroutines.getLongOrNullFlow
import com.russhwolf.settings.coroutines.getStringFlow
import com.russhwolf.settings.coroutines.getStringOrNullFlow
import kotlinx.coroutines.flow.Flow

@OptIn(ExperimentalSettingsApi::class)
class ScopedPreferencesImpl(
    private val settings: ObservableSettings,
    private val scope: String,
) : ScopedPreferences {

    private fun key(key: String) = "$scope.$key"

    override fun getString(key: String, default: String) = settings.getString(key(key), default)
    override fun putString(key: String, value: String) = settings.putString(key(key), value)
    override fun getInt(key: String, default: Int) = settings.getInt(key(key), default)
    override fun putInt(key: String, value: Int) = settings.putInt(key(key), value)
    override fun getDouble(key: String, default: Double) = settings.getDouble(key(key), default)
    override fun putDouble(key: String, value: Double) = settings.putDouble(key(key), value)
    override fun getBoolean(key: String, default: Boolean) = settings.getBoolean(key(key), default)
    override fun putBoolean(key: String, value: Boolean) = settings.putBoolean(key(key), value)
    override fun getLong(key: String, default: Long) = settings.getLong(key(key), default)
    override fun putLong(key: String, value: Long) = settings.putLong(key(key), value)
    override fun remove(key: String) = settings.remove(key(key))
    override fun clear() = settings.clear()

    override fun getStringFlow(key: String, default: String): Flow<String> = settings.getStringFlow(key(key), default)
    override fun getStringOrNullFlow(key: String): Flow<String?> = settings.getStringOrNullFlow(key(key))
    override fun getIntFlow(key: String, default: Int): Flow<Int> = settings.getIntFlow(key(key), default)
    override fun getIntOrNullFlow(key: String): Flow<Int?> = settings.getIntOrNullFlow(key(key))
    override fun getDoubleFlow(key: String, default: Double): Flow<Double> = settings.getDoubleFlow(key(key), default)
    override fun getDoubleOrNullFlow(key: String): Flow<Double?> = settings.getDoubleOrNullFlow(key(key))
    override fun getBooleanFlow(key: String, default: Boolean): Flow<Boolean> = settings.getBooleanFlow(key(key), default)
    override fun getBooleanOrNullFlow(key: String): Flow<Boolean?> = settings.getBooleanOrNullFlow(key(key))
    override fun getLongFlow(key: String, default: Long): Flow<Long> = settings.getLongFlow(key(key), default)
    override fun getLongOrNullFlow(key: String): Flow<Long?> = settings.getLongOrNullFlow(key(key))
}