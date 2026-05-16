package com.iqbalwork.ramadhancamp.shared.di
import com.iqbalwork.ramadhancamp.feature.bookmark.di.bookmarkModule
import com.iqbalwork.ramadhancamp.feature.home.di.homeModule
import com.iqbalwork.ramadhancamp.feature.pray.di.prayModule
import com.iqbalwork.ramadhancamp.feature.qibla.di.qiblaModule
import com.iqbalwork.ramadhancamp.feature.quran.di.quranModule
import com.iqbalwork.ramadhancamp.shared.common.geo.di.geoModule
import com.iqbalwork.ramadhancamp.shared.common.navigation.di.navigationModule
import com.iqbalwork.ramadhancamp.shared.common.network.di.networkModule
import com.iqbalwork.ramadhancamp.shared.common.notifs.di.notifModule
import com.iqbalwork.ramadhancamp.shared.common.preferences.di.preferencesModule
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.includes

fun initKoin(config: KoinAppDeclaration? = null) {
    startKoin {
        includes(config)
        modules(
            navigationModule,
            platformModule(),
            networkModule,
            geoModule,
            preferencesModule,
            homeModule,
            prayModule,
            quranModule,
        bookmarkModule,
            qiblaModule,
            bookmarkModule,
            notifModule,
        )
    }
}
