package com.iqbalwork.ramadhancamp.feature.pray.data.repositories

import com.iqbalwork.ramadhancamp.feature.home.data.datasource.HomePreferences
import com.iqbalwork.ramadhancamp.feature.home.data.model.shalatSchedule.ShalatScheduleDto
import com.iqbalwork.ramadhancamp.feature.pray.data.datasource.PrayPreferences
import com.iqbalwork.ramadhancamp.feature.pray.data.datasource.PrayRemoteDatasource
import com.iqbalwork.ramadhancamp.feature.pray.data.mapper.timeForKey
import com.iqbalwork.ramadhancamp.feature.pray.data.mapper.toPrayCountdown
import com.iqbalwork.ramadhancamp.feature.pray.data.mapper.toPrayItems
import com.iqbalwork.ramadhancamp.feature.pray.domain.model.LastLocation
import com.iqbalwork.ramadhancamp.feature.pray.domain.model.PrayCountdown
import com.iqbalwork.ramadhancamp.feature.pray.domain.model.PraySchedule
import com.iqbalwork.ramadhancamp.feature.pray.domain.model.Prayers
import com.iqbalwork.ramadhancamp.feature.pray.domain.model.toPrayerDisplayName
import com.iqbalwork.ramadhancamp.feature.pray.domain.repository.PrayRepository
import com.iqbalwork.ramadhancamp.shared.common.notifs.di.PRAY_ALARM_CHANNEL_ID
import com.tweener.alarmee.AlarmeeService
import com.tweener.alarmee.model.Alarmee
import com.tweener.alarmee.model.AndroidNotificationConfiguration
import com.tweener.alarmee.model.AndroidNotificationPriority
import com.tweener.alarmee.model.IosNotificationConfiguration
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.number
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.resources.getString
import ramadhancamp.composeapp.generated.resources.Res
import ramadhancamp.composeapp.generated.resources.pray_notification_body
import kotlin.time.Clock

@OptIn(ExperimentalCoroutinesApi::class)
class PrayRepositoryImpl(
    private val remoteDatasource: PrayRemoteDatasource,
    private val prayPreferences: PrayPreferences,
    private val homePreferences: HomePreferences,
    private val alarmeeService: AlarmeeService,
) : PrayRepository {

    override val lastLocation: Flow<LastLocation?> = homePreferences.lastCityStateFlow().map {
        val city = it ?: return@map null
        val country = homePreferences.lastCountry ?: return@map null
        LastLocation(city, country)
    }

    private val todayScheduleFlow = MutableStateFlow<ShalatScheduleDto?>(null)

    override val countdown: Flow<PrayCountdown> = todayScheduleFlow
        .flatMapLatest { schedule ->
            flow {
                while (true) {
                    val now = Clock.System.now()
                    val tz = TimeZone.currentSystemDefault()
                    val nowLocal = now.toLocalDateTime(tz)
                    val tomorrowLocal = now.plus(1, DateTimeUnit.DAY, tz).toLocalDateTime(tz)

                    val todayJadwal = schedule?.data?.jadwal?.find { it.tanggal == nowLocal.day }
                    val tomorrowJadwal = schedule?.data?.jadwal?.find { it.tanggal == tomorrowLocal.day }

                    todayJadwal?.let { emit(it.toPrayCountdown(nowLocal, tomorrowJadwal)) }
                    delay(1000L)
                }
            }
        }

    override suspend fun loadSchedule(date: LocalDate): Result<PraySchedule> = runCatching {
        val schedule = getOrFetchMonthSchedule(date.month.number, date.year).getOrThrow()

        val today = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
        if (date.month.number == today.month.number && date.year == today.year) {
            todayScheduleFlow.value = schedule
        }

        val jadwal = schedule?.data?.jadwal?.find { it.tanggal == date.day }

        val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
        val alarmStates = prayPreferences.getAllAlarmsState()

        PraySchedule(
            date = date,
            prayers = jadwal!!.toPrayItems(
                selectedDate = date,
                now = now,
                alarmStates = alarmStates
            )
        )
    }

    override suspend fun toggleAlarm(prayerKey: Prayers, enabled: Boolean): Result<Unit> = runCatching {
        prayPreferences.setAlarmState(prayerKey, enabled)

        val today = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date

        if (enabled) {
            for (dayOffset in 0..9) {
                val targetDate = today.plus(dayOffset, DateTimeUnit.DAY)
                val schedule = getOrFetchMonthSchedule(targetDate.month.number, targetDate.year).getOrThrow() ?: continue
                val jadwal = schedule.data.jadwal.find { it.tanggal == targetDate.day } ?: continue
                val timeStr = jadwal.timeForKey(prayerKey) ?: continue
                val (hour, minute) = timeStr.split(":").map { it.toInt() }

                val scheduledDateTime = LocalDateTime(
                    year = targetDate.year,
                    month = targetDate.month.number,
                    day = targetDate.day,
                    hour = hour,
                    minute = minute,
                )

                val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
                if (scheduledDateTime > now) {
                    val displayName = prayerKey.toPrayerDisplayName()
                    alarmeeService.local.schedule(
                        alarmee = Alarmee(
                            uuid = "pray-$prayerKey-$targetDate",
                            notificationTitle = displayName,
                            notificationBody = getString(Res.string.pray_notification_body, displayName),
                            scheduledDateTime = scheduledDateTime,
                            androidNotificationConfiguration = AndroidNotificationConfiguration(
                                channelId = PRAY_ALARM_CHANNEL_ID,
                                priority = AndroidNotificationPriority.HIGH,
                            ),
                            iosNotificationConfiguration = IosNotificationConfiguration(),
                        )
                    )
                }
            }
        } else {
            for (dayOffset in -1..30) {
                val targetDate = today.plus(dayOffset, DateTimeUnit.DAY)
                alarmeeService.local.cancel(uuid = "pray-$prayerKey-$targetDate")
            }
        }
    }

    override suspend fun resyncAlarms() {
        Prayers.entries.forEach { key ->
            if (prayPreferences.getAlarmState(key)) {
                toggleAlarm(key, enabled = true)
            }
        }
    }

    private suspend fun getOrFetchMonthSchedule(month: Int, year: Int): Result<ShalatScheduleDto?> = runCatching {
        val city = homePreferences.lastCity ?: return Result.success(null)
        prayPreferences.getShalatSchedule(month, year, city)?.let { return Result.success(it) }

        val province = homePreferences.lastProvince ?: return Result.success(null)

        return remoteDatasource.getShalatSchedule(province, city, month, year)
            .onSuccess { prayPreferences.saveShalatSchedule(month, year, city,it) }
    }
}
