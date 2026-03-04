package com.iqbalwork.ramadhancamp.shared.common.preferences.utils

import com.iqbalwork.ramadhancamp.shared.common.preferences.domain.ScopedPreferences
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty
import kotlinx.coroutines.flow.Flow

fun ScopedPreferences.string(key: String, default: String = "") =
    object : ReadWriteProperty<Any?, String> {
        override fun getValue(thisRef: Any?, property: KProperty<*>) = getString(key, default)
        override fun setValue(thisRef: Any?, property: KProperty<*>, value: String) = putString(key, value)
    }

fun ScopedPreferences.nullableString(key: String) =
    object : ReadWriteProperty<Any?, String?> {
        override fun getValue(thisRef: Any?, property: KProperty<*>) = getString(key, "").takeIf { it.isNotEmpty() }
        override fun setValue(thisRef: Any?, property: KProperty<*>, value: String?) {
            if (value == null) remove(key) else putString(key, value)
        }
    }

fun ScopedPreferences.int(key: String, default: Int = 0) =
    object : ReadWriteProperty<Any?, Int> {
        override fun getValue(thisRef: Any?, property: KProperty<*>) = getInt(key, default)
        override fun setValue(thisRef: Any?, property: KProperty<*>, value: Int) = putInt(key, value)
    }

fun ScopedPreferences.nullableInt(key: String) =
    object : ReadWriteProperty<Any?, Int?> {
        override fun getValue(thisRef: Any?, property: KProperty<*>) = getInt(key, Int.MIN_VALUE).takeIf { it != Int.MIN_VALUE }
        override fun setValue(thisRef: Any?, property: KProperty<*>, value: Int?) {
            if (value == null) remove(key) else putInt(key, value)
        }
    }

fun ScopedPreferences.double(key: String, default: Double = 0.0) =
    object : ReadWriteProperty<Any?, Double> {
        override fun getValue(thisRef: Any?, property: KProperty<*>) = getDouble(key, default)
        override fun setValue(thisRef: Any?, property: KProperty<*>, value: Double) = putDouble(key, value)
    }

fun ScopedPreferences.nullableDouble(key: String) =
    object : ReadWriteProperty<Any?, Double?> {
        override fun getValue(thisRef: Any?, property: KProperty<*>) = getDouble(key, Double.NaN).takeIf { !it.isNaN() }
        override fun setValue(thisRef: Any?, property: KProperty<*>, value: Double?) {
            if (value == null) remove(key) else putDouble(key, value)
        }
    }

fun ScopedPreferences.boolean(key: String, default: Boolean = false) =
    object : ReadWriteProperty<Any?, Boolean> {
        override fun getValue(thisRef: Any?, property: KProperty<*>) = getBoolean(key, default)
        override fun setValue(thisRef: Any?, property: KProperty<*>, value: Boolean) = putBoolean(key, value)
    }

fun ScopedPreferences.nullableBoolean(key: String) =
    object : ReadWriteProperty<Any?, Boolean?> {
        override fun getValue(thisRef: Any?, property: KProperty<*>): Boolean? {
            val sentinel = -1
            return when (getInt(key, sentinel)) {
                1 -> true
                0 -> false
                else -> null
            }
        }
        override fun setValue(thisRef: Any?, property: KProperty<*>, value: Boolean?) {
            if (value == null) remove(key) else putInt(key, if (value) 1 else 0)
        }
    }

fun ScopedPreferences.long(key: String, default: Long = 0L) =
    object : ReadWriteProperty<Any?, Long> {
        override fun getValue(thisRef: Any?, property: KProperty<*>) = getLong(key, default)
        override fun setValue(thisRef: Any?, property: KProperty<*>, value: Long) =
            putLong(key, value)
    }

fun ScopedPreferences.nullableLong(key: String) =
    object : ReadWriteProperty<Any?, Long?> {
        override fun getValue(thisRef: Any?, property: KProperty<*>) = getLong(key, Long.MIN_VALUE).takeIf { it != Long.MIN_VALUE }
        override fun setValue(thisRef: Any?, property: KProperty<*>, value: Long?) {
            if (value == null) remove(key) else putLong(key, value)
        }
    }

// Single FlowPref<T> — T carries nullability.
// FlowPref<String>  → flow never emits null, set() requires non-null value.
// FlowPref<String?> → flow emits null when key absent, set(null) removes the key.
class FlowPref<T>(
    val flow: Flow<T>,
    private val write: (T) -> Unit,
) {
    fun set(value: T) = write(value)
}


fun ScopedPreferences.stringFlowPref(key: String, default: String = ""): FlowPref<String> =
    FlowPref(flow = getStringFlow(key, default)) { putString(key, it) }

fun ScopedPreferences.nullableStringFlowPref(key: String): FlowPref<String?> =
    FlowPref(flow = getStringOrNullFlow(key)) { if (it == null) remove(key) else putString(key, it) }

fun ScopedPreferences.intFlowPref(key: String, default: Int = 0): FlowPref<Int> =
    FlowPref(flow = getIntFlow(key, default)) { putInt(key, it) }

fun ScopedPreferences.nullableIntFlowPref(key: String): FlowPref<Int?> =
    FlowPref(flow = getIntOrNullFlow(key)) { if (it == null) remove(key) else putInt(key, it) }

fun ScopedPreferences.doubleFlowPref(key: String, default: Double = 0.0): FlowPref<Double> =
    FlowPref(flow = getDoubleFlow(key, default)) { putDouble(key, it) }

fun ScopedPreferences.nullableDoubleFlowPref(key: String): FlowPref<Double?> =
    FlowPref(flow = getDoubleOrNullFlow(key)) { if (it == null) remove(key) else putDouble(key, it) }

fun ScopedPreferences.booleanFlowPref(key: String, default: Boolean = false): FlowPref<Boolean> =
    FlowPref(flow = getBooleanFlow(key, default)) { putBoolean(key, it) }

fun ScopedPreferences.nullableBooleanFlowPref(key: String): FlowPref<Boolean?> =
    FlowPref(flow = getBooleanOrNullFlow(key)) { if (it == null) remove(key) else putBoolean(key, it) }

fun ScopedPreferences.longFlowPref(key: String, default: Long = 0L): FlowPref<Long> =
    FlowPref(flow = getLongFlow(key, default)) { putLong(key, it) }

fun ScopedPreferences.nullableLongFlowPref(key: String): FlowPref<Long?> =
    FlowPref(flow = getLongOrNullFlow(key)) { if (it == null) remove(key) else putLong(key, it) }
