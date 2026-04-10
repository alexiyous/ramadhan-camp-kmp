package com.iqbalwork.ramadhancamp.feature.pray.presentation.mapper

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bedtime
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Nightlight
import androidx.compose.material.icons.filled.WbCloudy
import androidx.compose.material.icons.filled.WbTwilight
import androidx.compose.material.icons.outlined.Bedtime
import androidx.compose.material.icons.outlined.LightMode
import androidx.compose.material.icons.outlined.Nightlight
import androidx.compose.material.icons.outlined.WbCloudy
import androidx.compose.material.icons.outlined.WbTwilight
import com.iqbalwork.ramadhancamp.feature.pray.domain.model.PrayCountdown
import com.iqbalwork.ramadhancamp.feature.pray.domain.model.PrayItem
import com.iqbalwork.ramadhancamp.feature.pray.presentation.model.PrayCountdownUiModel
import com.iqbalwork.ramadhancamp.feature.pray.presentation.model.PrayItemUiModel

import com.iqbalwork.ramadhancamp.feature.pray.domain.model.Prayers

fun PrayItem.toUiModel(): PrayItemUiModel = PrayItemUiModel(
    key = key,
    displayName = displayName,
    time = time,
    icon = when (key) {
        Prayers.SUBUH   -> Icons.Outlined.Bedtime
        Prayers.DZUHUR  -> Icons.Outlined.LightMode
        Prayers.ASHAR   -> Icons.Outlined.WbCloudy
        Prayers.MAGHRIB -> Icons.Outlined.WbTwilight
        Prayers.ISYA    -> Icons.Outlined.Nightlight
    },
    isNextPrayer = isNextPrayer,
    isAlarmOn = isAlarmOn,
)

fun PrayCountdown.toUiModel(): PrayCountdownUiModel {
    return PrayCountdownUiModel(
        prayerName = prayerName,
        prayerTime = prayerTime,
        remainingTime = remainingTime,
        prevPrayerName = prevPrayerName,
        prevPrayerTime = prevPrayerTime,
        nextPrayerName = nextPrayerName,
        nextPrayerTime = nextPrayerTime,
    )
}
