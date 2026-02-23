package com.iqbalwork.ramadhancamp.feature.pray.di

import com.iqbalwork.ramadhancamp.feature.pray.presentation.PrayViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val prayModule = module {
    viewModel { PrayViewModel(get()) }
}
