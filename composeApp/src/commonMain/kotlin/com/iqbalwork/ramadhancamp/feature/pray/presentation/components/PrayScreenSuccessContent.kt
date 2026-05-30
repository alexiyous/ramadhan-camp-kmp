package com.iqbalwork.ramadhancamp.feature.pray.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.iqbalwork.ramadhancamp.feature.pray.domain.model.Prayers
import com.iqbalwork.ramadhancamp.feature.pray.presentation.model.PrayCountdownUiModel
import com.iqbalwork.ramadhancamp.feature.pray.presentation.model.PrayItemUiModel
import com.iqbalwork.ramadhancamp.shared.common.ui.theme.RamadhanTheme
import kotlinx.datetime.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrayScreenSuccessContent(
    modifier: Modifier = Modifier,
    currentCity: String,
    currentCountry: String,
    selectedDate: String,
    countdown: PrayCountdownUiModel?,
    prayers: List<PrayItemUiModel>,
    onAlarmClicked: (key: Prayers, enabled: Boolean) -> Unit,
    onDateSelect: (date: LocalDate) -> Unit,
) {
    var showDatePicker by rememberSaveable { mutableStateOf(false) }

    LazyColumn(
        modifier = modifier,
    ) {
        item {
            PrayHeader(
                modifier = Modifier
                    .statusBarsPadding()
                    .fillMaxWidth()
                    .padding(bottom = 20.dp),
                city = currentCity,
                selectedDate = selectedDate,
                onCalendarClick = { showDatePicker = true },
                country = currentCountry
            )
        }

        countdown?.let { countdown ->
            item {
                NextPrayerCard(
                    countdown = countdown,
                    modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp)
                )
            }
        }

        items(
            prayers,
            key = { it.key }
        ) { prayer ->
            PrayerRowItem(
                item = prayer,
                onAlarmToggle = onAlarmClicked,
                modifier = Modifier.padding(vertical = 6.dp)
            )
        }
    }

    if (showDatePicker) {
        PrayDatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            onDateSelect = { date ->
                onDateSelect(date)
                showDatePicker = false
            }
        )
    }
}

@Preview
@Composable
private fun PrayScreenSuccessContentPreview() {
    val dummyPrayers = listOf(
        PrayItemUiModel(Prayers.SUBUH, "Fajr", "04:30", Icons.Default.Notifications, isNextPrayer = false, isAlarmOn = true),
        PrayItemUiModel(Prayers.DZUHUR, "Dhuhr", "11:45", Icons.Default.Notifications, isNextPrayer = true, isAlarmOn = false),
        PrayItemUiModel(Prayers.ASHAR, "Asr", "15:00", Icons.Default.Notifications, isNextPrayer = false, isAlarmOn = true),
        PrayItemUiModel(Prayers.MAGHRIB, "Maghrib", "17:45", Icons.Default.Notifications, isNextPrayer = false, isAlarmOn = true),
        PrayItemUiModel(Prayers.ISYA, "Isha", "19:00", Icons.Default.Notifications, isNextPrayer = false, isAlarmOn = true),
    )
    val dummyCountdown = PrayCountdownUiModel(
        prayerName = "Dhuhr",
        prayerTime = "11:45",
        remainingTime = "00:45:00",
        prevPrayerName = "Fajr",
        prevPrayerTime = "04:30",
        nextPrayerName = "Asr",
        nextPrayerTime = "15:00"
    )

    RamadhanTheme {
        Surface {
            PrayScreenSuccessContent(
                modifier = Modifier
                    .fillMaxSize()
                    .background(RamadhanTheme.colors.bgPrimary),
                currentCity = "Jakarta",
                selectedDate = "Friday, 10 March 2026",
                countdown = dummyCountdown,
                prayers = dummyPrayers,
                onAlarmClicked = { _, _ -> },
                onDateSelect = {},
                currentCountry = "Indonesia",
            )
        }
    }
}

@Preview
@Composable
private fun PrayScreenSuccessContentNoCountdownPreview() {
    val dummyPrayers = listOf(
        PrayItemUiModel(Prayers.SUBUH, "Fajr", "04:30", Icons.Default.Notifications, isNextPrayer = false, isAlarmOn = true),
        PrayItemUiModel(Prayers.DZUHUR, "Dhuhr", "11:45", Icons.Default.Notifications, isNextPrayer = false, isAlarmOn = false),
        PrayItemUiModel(Prayers.ASHAR, "Asr", "15:00", Icons.Default.Notifications, isNextPrayer = false, isAlarmOn = true),
    )

    RamadhanTheme {
        Surface {
            PrayScreenSuccessContent(
                modifier = Modifier
                    .fillMaxSize()
                    .background(RamadhanTheme.colors.bgPrimary),
                currentCity = "Jakarta",
                selectedDate = "Friday, 10 March 2026",
                countdown = null,
                prayers = dummyPrayers,
                onAlarmClicked = { _, _ -> },
                onDateSelect = {},
                currentCountry = "Indonesia",
            )
        }
    }
}