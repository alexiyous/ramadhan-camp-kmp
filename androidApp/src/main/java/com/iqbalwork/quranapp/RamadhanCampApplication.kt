package com.iqbalwork.quranapp

import android.app.Application
import com.iqbalwork.ramadhancamp.initKoin
import com.iqbalwork.ramadhancamp.shared.utils.initLogging
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier

class RamadhanCampApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        //TODO Allow only when DEBUG,
        initLogging()
        initKoin()
    }
}
