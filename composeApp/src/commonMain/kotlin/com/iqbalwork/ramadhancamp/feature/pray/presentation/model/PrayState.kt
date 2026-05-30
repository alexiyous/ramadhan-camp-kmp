package com.iqbalwork.ramadhancamp.feature.pray.presentation.model

import com.iqbalwork.ramadhancamp.shared.common.utils.AppError
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock

data class PrayState(
    val isLoading: Boolean = false,
    val city: String = "",
    val country: String = "",
    val hasLocation: Boolean = false,
    val selectedDate: String = "",
    val countdown: PrayCountdownUiModel? = null,
    val prayers: List<PrayItemUiModel> = emptyList(),
    val error: AppError? = null,
    val isNotificationPermissionDenied: Boolean = false
)
