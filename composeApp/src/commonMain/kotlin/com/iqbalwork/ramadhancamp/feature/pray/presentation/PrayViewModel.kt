package com.iqbalwork.ramadhancamp.feature.pray.presentation

import androidx.lifecycle.viewModelScope
import com.iqbalwork.ramadhancamp.feature.pray.domain.model.Prayers
import com.iqbalwork.ramadhancamp.feature.pray.domain.repository.PrayRepository
import com.iqbalwork.ramadhancamp.feature.pray.presentation.mapper.toUiModel
import com.iqbalwork.ramadhancamp.feature.pray.presentation.model.PrayEffect
import com.iqbalwork.ramadhancamp.feature.pray.presentation.model.PrayEvent
import com.iqbalwork.ramadhancamp.feature.pray.presentation.model.PrayState
import com.iqbalwork.ramadhancamp.shared.common.navigation.NavigationManager
import com.iqbalwork.ramadhancamp.shared.common.ui.BaseViewModel
import com.iqbalwork.ramadhancamp.shared.common.utils.AppError
import com.iqbalwork.ramadhancamp.shared.common.utils.date.DateFormatPattern
import com.iqbalwork.ramadhancamp.shared.common.utils.date.format
import com.iqbalwork.ramadhancamp.shared.common.utils.date.toLocalDate
import com.iqbalwork.ramadhancamp.shared.common.utils.goToDeviceSettings
import com.iqbalwork.ramadhancamp.shared.common.utils.toAppError
import dev.icerock.moko.permissions.DeniedAlwaysException
import dev.icerock.moko.permissions.DeniedException
import dev.icerock.moko.permissions.Permission
import dev.icerock.moko.permissions.RequestCanceledException
import dev.icerock.moko.permissions.notifications.REMOTE_NOTIFICATION
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock

class PrayViewModel(
    params: PrayMainScreenParameters,
    navController: NavigationManager,
    private val prayRepository: PrayRepository,
) : BaseViewModel<PrayMainScreenParameters, PrayState, PrayEvent, PrayEffect>(
    params = params,
    initialState = PrayState(),
    navigationManager = navController,
    resultKeys = arrayOf(),
) {
    private var hasLoadTodaySchedule = false
    val permissionController = params.permissionController

    init {
        viewModelScope.apply {
            launch { requestNotifPermission() }
            launch { initPraySchedule() }
            launch { initNextPraySchedule() }
        }
    }

    private suspend fun requestNotifPermission() {
        try {
            params.permissionController?.providePermission(Permission.REMOTE_NOTIFICATION)
        } catch (_: DeniedAlwaysException) {
            updateState { copy(isNotificationPermissionDenied = true) }
        }
        catch (_: DeniedException) {
            updateState { copy(isNotificationPermissionDenied = true) }
        }
        catch (_: RequestCanceledException) {
            updateState { copy(isNotificationPermissionDenied = true) }
        }
    }

    private suspend fun checkNotificationPermission(): Boolean {
        return permissionController?.isPermissionGranted(Permission.REMOTE_NOTIFICATION) ?: false
    }

    private suspend fun initPraySchedule() {
        prayRepository.lastLocation
            .collect {
                updateState { copy(isLoading = true) }
                if (it == null) {
                    updateState { copy(isLoading = false, hasLocation = false) }
                    return@collect
                }

                updateState {
                    copy(
                        city = it.city,
                        country = it.country
                    )
                }

                loadTodaySchedule()
            }
    }

    private suspend fun initNextPraySchedule() {
        prayRepository.countdown.collect { countdown ->
            updateState { copy(countdown = countdown.toUiModel()) }
        }
    }

    override fun handleEvent(event: PrayEvent) {
        when (event) {
            is PrayEvent.DateSelected -> viewModelScope.launch { loadScheduleForDate(event.date) }
            is PrayEvent.ToggleAlarm -> viewModelScope.launch { onToggleAlarm(event.prayerKey, event.enabled) }
            PrayEvent.RetryLoadSchedule -> viewModelScope.launch {
                if (hasLoadTodaySchedule) loadScheduleForDate(state.value.selectedDate.toLocalDate(
                    formatPattern = DateFormatPattern.SHORT_DAY_DATE_MONTH_YEAR))
                else loadTodaySchedule()
            }
            PrayEvent.GoToSetting -> goToDeviceSettings()
            PrayEvent.CheckNotificationPermission -> viewModelScope.launch {
                if (!state.value.isNotificationPermissionDenied) return@launch
                val isGranted = checkNotificationPermission()
                if (!isGranted) return@launch
                updateState { copy(isNotificationPermissionDenied = false) }
            }
        }
    }

    private suspend fun loadTodaySchedule() {
        val today = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
        updateState { copy(error = null, selectedDate = today.format(formatPattern = DateFormatPattern.SHORT_DAY_DATE_MONTH_YEAR)) }
        prayRepository.loadSchedule(today)
            .onSuccess { schedule ->
                updateState { copy(isLoading = false, prayers = schedule.prayers.map { it.toUiModel() }, hasLocation = true) }
                prayRepository.resyncAlarms()
                hasLoadTodaySchedule = true
            }
            .onFailure { error ->
                val appError = error.toAppError()
                if (appError is AppError.ServerError && appError.httpCode == 404) {
                    updateState { copy(isLoading = false, hasLocation = false) }
                } else {
                    updateState { copy(isLoading = false, error = appError) }
                }
            }
    }

    private suspend fun loadScheduleForDate(date: LocalDate) {
        updateState {
            copy(
                isLoading = true,
                error = null,
                selectedDate = date.format(formatPattern = DateFormatPattern.SHORT_DAY_DATE_MONTH_YEAR),
               )
        }
        prayRepository.loadSchedule(date)
            .onSuccess { schedule ->
                updateState { copy(isLoading = false, prayers = schedule.prayers.map { it.toUiModel() }) }
            }
            .onFailure { error ->
                updateState { copy(isLoading = false, error = error.toAppError()) }
            }
    }

    private suspend fun onToggleAlarm(prayerKey: Prayers, enabled: Boolean) {
        prayRepository.toggleAlarm(prayerKey, enabled)
            .onSuccess {
                updateState {
                    copy(prayers = prayers.map { item ->
                        if (item.key == prayerKey) item.copy(isAlarmOn = enabled) else item
                    })
                }
            }
            .onFailure {
                //TODO throw snackbar
            }
    }
}
