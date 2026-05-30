package com.iqbalwork.ramadhancamp.feature.pray.presentation.model

import com.iqbalwork.ramadhancamp.feature.pray.domain.model.Prayers
import com.iqbalwork.ramadhancamp.shared.common.ui.UiEvent
import kotlinx.datetime.LocalDate

sealed interface PrayEvent : UiEvent {
    data class DateSelected(val date: LocalDate) : PrayEvent
    data class ToggleAlarm(val prayerKey: Prayers, val enabled: Boolean) : PrayEvent
    data object RetryLoadSchedule : PrayEvent
    data object GoToSetting : PrayEvent
    data object CheckNotificationPermission : PrayEvent
}
