package com.iqbalwork.ramadhancamp.feature.home.data.mapper

import com.iqbalwork.ramadhancamp.feature.home.data.model.shalatSchedule.ShalatJadwalDto
import com.iqbalwork.ramadhancamp.feature.home.domain.model.NextPrayer
import com.iqbalwork.ramadhancamp.feature.pray.domain.model.Prayers
import com.iqbalwork.ramadhancamp.feature.pray.domain.model.toPrayerDisplayName
import com.iqbalwork.ramadhancamp.shared.common.extension.toMinutes
import kotlinx.datetime.LocalDateTime

fun ShalatJadwalDto.nextPrayer(now: LocalDateTime, tomorrowJadwal: ShalatJadwalDto? = null): NextPrayer {
    val nowMinutes = now.hour * 60 + now.minute
    val tom = tomorrowJadwal ?: this

    data class Point(val name: String, val time: String, val min: Int)
    val timeline = listOf(
        Point(Prayers.SUBUH.toPrayerDisplayName(), subuh, subuh.toMinutes()),
        Point(Prayers.DZUHUR.toPrayerDisplayName(), dzuhur, dzuhur.toMinutes()),
        Point(Prayers.ASHAR.toPrayerDisplayName(), ashar, ashar.toMinutes()),
        Point(Prayers.MAGHRIB.toPrayerDisplayName(), maghrib, maghrib.toMinutes()),
        Point(Prayers.ISYA.toPrayerDisplayName(), isya, isya.toMinutes()),
        Point(Prayers.SUBUH.toPrayerDisplayName(), tom.subuh, tom.subuh.toMinutes() + 24 * 60),
    )

    val next = timeline.firstOrNull { it.min > nowMinutes } ?: timeline.last()
    val remaining = next.min - nowMinutes

    return NextPrayer(name = next.name, time = next.time, remainingMinutes = remaining)
}