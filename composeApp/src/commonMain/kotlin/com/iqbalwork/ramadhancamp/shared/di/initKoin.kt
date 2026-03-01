package com.iqbalwork.ramadhancamp.shared.di

import com.iqbalwork.ramadhancamp.feature.bookmark.di.bookmarkModule
import com.iqbalwork.ramadhancamp.feature.home.di.homeModule
import com.iqbalwork.ramadhancamp.feature.pray.di.prayModule
import com.iqbalwork.ramadhancamp.feature.qibla.di.qiblaModule
import com.iqbalwork.ramadhancamp.feature.quran.di.quranModule
import com.iqbalwork.ramadhancamp.shared.common.navigation.di.navigationModule
import com.iqbalwork.ramadhancamp.shared.common.network.di.networkModule
import org.koin.core.context.startKoin

fun initKoin() {
    startKoin {
        modules(
            navigationModule,
            platformModule(),
            networkModule,
            homeModule,
            prayModule,
            quranModule,
            qiblaModule,
            bookmarkModule
        )
    }

}
