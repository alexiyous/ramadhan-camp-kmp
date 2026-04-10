package com.iqbalwork.ramadhancamp.feature.pray.data.mapper

import com.iqbalwork.ramadhancamp.feature.home.data.model.shalatSchedule.ShalatJadwalDto
import com.iqbalwork.ramadhancamp.feature.pray.domain.model.PrayCountdown
import com.iqbalwork.ramadhancamp.feature.pray.domain.model.PrayItem
import com.iqbalwork.ramadhancamp.feature.pray.domain.model.Prayers
import com.iqbalwork.ramadhancamp.feature.pray.domain.model.toPrayerDisplayName
import com.iqbalwork.ramadhancamp.shared.common.extension.padZero
import com.iqbalwork.ramadhancamp.shared.common.extension.toSeconds

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.plus
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock

fun ShalatJadwalDto.timeForKey(key: Prayers): String? = when (key) {
    Prayers.SUBUH   -> subuh
    Prayers.DZUHUR  -> dzuhur
    Prayers.ASHAR   -> ashar
    Prayers.MAGHRIB -> maghrib
    Prayers.ISYA    -> isya
}

fun ShalatJadwalDto.toPrayItems(
    selectedDate: LocalDate,
    now: LocalDateTime,
    alarmStates: Map<Prayers, Boolean>
): List<PrayItem> {
    val today = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
    val nowSeconds = now.hour * 3600 + now.minute * 60 + now.second

    val todayNextKey = Prayers.entries.firstOrNull { key ->
        val time = timeForKey(key) ?: return@firstOrNull false
        time.toSeconds() > nowSeconds
    }

    val expectedDate = if (todayNextKey == null) today.plus(1, DateTimeUnit.DAY) else today
    val expectedKey = todayNextKey ?: Prayers.SUBUH

    val nextPrayerKey: Prayers? = if (selectedDate == expectedDate) expectedKey else null
    return Prayers.entries.mapNotNull { key ->
        val time = timeForKey(key) ?: return@mapNotNull null
        PrayItem(
            key = key,
            displayName = key.toPrayerDisplayName(),
            time = time,
            isNextPrayer = key == nextPrayerKey,
            isAlarmOn = alarmStates[key] ?: false,
        )
    }
}

fun ShalatJadwalDto.toPrayCountdown(now: LocalDateTime, tomorrowJadwal: ShalatJadwalDto? = null): PrayCountdown {
    val nowSeconds = now.hour * 3600 + now.minute * 60 + now.second
    val tom = tomorrowJadwal ?: this

    data class Point(val name: String, val time: String, val sec: Long)
    val timeline = listOf(
        Point(Prayers.ISYA.toPrayerDisplayName(), isya, isya.toSeconds() - 86400L),
        Point(Prayers.SUBUH.toPrayerDisplayName(), subuh, subuh.toSeconds().toLong()),
        Point(Prayers.DZUHUR.toPrayerDisplayName(), dzuhur, dzuhur.toSeconds().toLong()),
        Point(Prayers.ASHAR.toPrayerDisplayName(), ashar, ashar.toSeconds().toLong()),
        Point(Prayers.MAGHRIB.toPrayerDisplayName(), maghrib, maghrib.toSeconds().toLong()),
        Point(Prayers.ISYA.toPrayerDisplayName(), isya, isya.toSeconds().toLong()),
        Point(Prayers.SUBUH.toPrayerDisplayName(), tom.subuh, tom.subuh.toSeconds() + 86400L),
        Point(Prayers.DZUHUR.toPrayerDisplayName(), tom.dzuhur, tom.dzuhur.toSeconds() + 86400L),
        Point(Prayers.ASHAR.toPrayerDisplayName(), tom.ashar, tom.ashar.toSeconds() + 86400L),
        Point(Prayers.MAGHRIB.toPrayerDisplayName(), tom.maghrib, tom.maghrib.toSeconds() + 86400L),
        Point(Prayers.ISYA.toPrayerDisplayName(), tom.isya, tom.isya.toSeconds() + 86400L),
    )

    val nextIdx = timeline.indexOfFirst { it.sec > nowSeconds }.takeIf { it > 0 } ?: 1
    val prev = timeline[nextIdx - 1]
    val next = timeline[nextIdx]
    val afterNext = timeline[nextIdx + 1]

    val remaining = next.sec - nowSeconds
    val h = remaining / 3600
    val m = (remaining % 3600) / 60
    val s = remaining % 60

    return PrayCountdown(
        prayerName = next.name,
        prayerTime = next.time,
        remainingTime = "${h.padZero()}:${m.padZero()}:${s.padZero()}",
        prevPrayerName = prev.name,
        prevPrayerTime = prev.time,
        nextPrayerName = afterNext.name,
        nextPrayerTime = afterNext.time,
    )
}
