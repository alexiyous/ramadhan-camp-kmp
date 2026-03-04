package com.iqbalwork.ramadhancamp.feature.home.data.datasource

import com.iqbalwork.ramadhancamp.shared.common.preferences.domain.AppPreferences
import com.iqbalwork.ramadhancamp.shared.common.preferences.utils.FlowPref
import com.iqbalwork.ramadhancamp.shared.common.preferences.utils.double
import com.iqbalwork.ramadhancamp.shared.common.preferences.utils.nullableIntFlowPref
import com.iqbalwork.ramadhancamp.shared.common.preferences.utils.nullableString
import com.iqbalwork.ramadhancamp.shared.common.preferences.utils.nullableStringFlowPref

private const val HOME_PREF_SCOPE = "HOME_PREF_SCOPE"

class HomePreferences(prefs: AppPreferences) {
    private val scoped = prefs.scope(HOME_PREF_SCOPE)

    // Location data — plain read/write only, no observation needed
    var lastLatitude: Double by scoped.double("lat")
    var lastLongitude: Double by scoped.double("lon")
    var lastCity: String? by scoped.nullableString("lastCity")
    var lastProvince: String? by scoped.nullableString("lastProvince")

    // Last surah read — nullable because user may not have read any surah yet
    val surahName: FlowPref<String?> = scoped.nullableStringFlowPref("surahName")
    val lastAyatNumber: FlowPref<Int?> = scoped.nullableIntFlowPref("lastAyatNumber")
    val lastDateRead: FlowPref<String?> = scoped.nullableStringFlowPref("lastDateRead")

    fun clear() = scoped.clear()
}