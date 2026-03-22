package com.iqbalwork.ramadhancamp.feature.qibla.di

import com.iqbalwork.ramadhancamp.feature.qibla.presentation.QiblaViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val qiblaModule = module {
    viewModel { QiblaViewModel(get()) }
}
