package com.iqbalwork.ramadhancamp

import com.iqbalwork.ramadhancamp.feature.bookmark.di.bookmarkModule
import com.iqbalwork.ramadhancamp.feature.home.di.homeModule
import com.iqbalwork.ramadhancamp.feature.pray.di.prayModule
import com.iqbalwork.ramadhancamp.feature.qibla.di.qiblaModule
import com.iqbalwork.ramadhancamp.feature.quran.di.quranModule
import com.iqbalwork.ramadhancamp.shared.common.navigation.di.navigationModule
import org.koin.core.context.startKoin
import org.koin.core.error.KoinApplicationAlreadyStartedException

fun initKoin() {
    startKoin {
        modules(navigationModule, homeModule, prayModule, quranModule, qiblaModule, bookmarkModule)
    }
}
