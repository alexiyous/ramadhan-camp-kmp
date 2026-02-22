package com.iqbalwork.quranapp

import android.app.Application
import com.iqbalwork.ramadhancamp.initKoin
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier

class RamadhanCampApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Napier.base(DebugAntilog())
        initKoin()
    }
}
