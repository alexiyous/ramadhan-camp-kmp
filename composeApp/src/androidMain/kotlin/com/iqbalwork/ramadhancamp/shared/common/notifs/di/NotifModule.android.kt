package com.iqbalwork.ramadhancamp.shared.common.notifs.di

import android.app.NotificationManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.ui.graphics.Color
import com.iqbalwork.ramadhancamp.R
import com.iqbalwork.ramadhancamp.shared.common.ui.theme.RamadhanTheme
import com.tweener.alarmee.AlarmeeService
import com.tweener.alarmee.channel.AlarmeeNotificationChannel
import com.tweener.alarmee.configuration.AlarmeeAndroidPlatformConfiguration
import com.tweener.alarmee.createAlarmeeService
import org.koin.core.module.Module
import org.koin.dsl.module

@RequiresApi(Build.VERSION_CODES.N)
actual val notifModule: Module = module {
    single<AlarmeeService> {
        createAlarmeeService().apply {
            initialize(platformConfiguration =
                AlarmeeAndroidPlatformConfiguration(
                    notificationIconResId = R.drawable.ic_shalat_notif_icon,
                    notificationIconColor = Color(0xFF4ADE80),
                    useExactScheduling = true,
                    notificationChannels = listOf(
                        AlarmeeNotificationChannel(
                            id = PRAY_ALARM_CHANNEL_ID,
                            name = PRAY_ALARM_CHANNEL_NAME,
                            importance = NotificationManager.IMPORTANCE_HIGH,
                            soundFilename = "adzan"
                        )
                    )
                )
            )
        }
    }
}