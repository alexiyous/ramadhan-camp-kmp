package com.iqbalwork.quranapp

import android.app.Application
import com.iqbalwork.ramadhancamp.shared.di.initKoin
import com.iqbalwork.ramadhancamp.shared.utils.initLogging

class RamadhanCampApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        //TODO Allow only when DEBUG,
        initLogging()
        initKoin()
    }
}
