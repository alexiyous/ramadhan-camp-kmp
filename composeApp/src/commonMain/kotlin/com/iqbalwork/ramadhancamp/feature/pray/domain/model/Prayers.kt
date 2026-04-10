package com.iqbalwork.ramadhancamp.feature.pray.domain.model

import com.iqbalwork.ramadhancamp.feature.pray.domain.model.Prayers.ASHAR
import com.iqbalwork.ramadhancamp.feature.pray.domain.model.Prayers.DZUHUR
import com.iqbalwork.ramadhancamp.feature.pray.domain.model.Prayers.ISYA
import com.iqbalwork.ramadhancamp.feature.pray.domain.model.Prayers.MAGHRIB
import com.iqbalwork.ramadhancamp.feature.pray.domain.model.Prayers.SUBUH

enum class Prayers {
    SUBUH,
    DZUHUR,
    ASHAR,
    MAGHRIB,
    ISYA
}

fun Prayers.toPrayerDisplayName(): String = when (this) {
    SUBUH   -> "Subuh"
    DZUHUR  -> "Dzuhur"
    ASHAR   -> "Ashar"
    MAGHRIB -> "Maghrib"
    ISYA    -> "Isya"
}