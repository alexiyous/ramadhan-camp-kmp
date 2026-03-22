package com.iqbalwork.ramadhancamp.feature.quran.di

import com.iqbalwork.ramadhancamp.feature.quran.presentation.QuranViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val quranModule = module {
    viewModel { QuranViewModel(get()) }
}
