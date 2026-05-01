package com.iqbalwork.ramadhancamp.feature.qibla.data.datasource

import com.iqbalwork.ramadhancamp.shared.common.preferences.domain.AppPreferences
import com.iqbalwork.ramadhancamp.shared.common.preferences.utils.double
import com.iqbalwork.ramadhancamp.shared.common.preferences.utils.nullableString

class QiblaPreferences(prefs: AppPreferences) {
    private val scoped = prefs.scope("qibla")

    var lastLatitude: Double by scoped.double("lat")
    var lastLongitude: Double by scoped.double("lon")
    var lastCity: String? by scoped.nullableString("lastCity")
    var lastBearing: Double by scoped.double("bearing")
    var lastDistance: Double by scoped.double("distance")
}
