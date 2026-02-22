package com.iqbalwork.ramadhancamp

import com.iqbalwork.ramadhancamp.feature.home.di.homeModule
import com.iqbalwork.ramadhancamp.shared.common.navigation.di.navigationModule
import org.koin.core.context.startKoin
import org.koin.core.error.KoinApplicationAlreadyStartedException

fun initKoin() {
    startKoin {
        modules(navigationModule, homeModule)
    }
}
