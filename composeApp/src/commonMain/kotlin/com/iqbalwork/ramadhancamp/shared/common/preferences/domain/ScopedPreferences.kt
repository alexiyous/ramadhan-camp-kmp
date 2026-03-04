package com.iqbalwork.ramadhancamp.shared.common.preferences.domain

import kotlinx.coroutines.flow.Flow

interface ScopedPreferences {
    fun getString(key: String, default: String = ""): String
    fun putString(key: String, value: String)
    fun getInt(key: String, default: Int = 0): Int
    fun putInt(key: String, value: Int)
    fun getDouble(key: String, default: Double = 0.0): Double
    fun putDouble(key: String, value: Double)
    fun getBoolean(key: String, default: Boolean = false): Boolean
    fun putBoolean(key: String, value: Boolean)
    fun getLong(key: String, default: Long = 0L): Long
    fun putLong(key: String, value: Long)
    fun remove(key: String)
    fun clear()

    fun getStringFlow(key: String, default: String = ""): Flow<String>
    fun getStringOrNullFlow(key: String): Flow<String?>
    fun getIntFlow(key: String, default: Int = 0): Flow<Int>
    fun getIntOrNullFlow(key: String): Flow<Int?>
    fun getDoubleFlow(key: String, default: Double = 0.0): Flow<Double>
    fun getDoubleOrNullFlow(key: String): Flow<Double?>
    fun getBooleanFlow(key: String, default: Boolean = false): Flow<Boolean>
    fun getBooleanOrNullFlow(key: String): Flow<Boolean?>
    fun getLongFlow(key: String, default: Long = 0L): Flow<Long>
    fun getLongOrNullFlow(key: String): Flow<Long?>
}