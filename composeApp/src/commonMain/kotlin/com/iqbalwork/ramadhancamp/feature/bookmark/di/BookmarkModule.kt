package com.iqbalwork.ramadhancamp.feature.bookmark.di

import com.iqbalwork.ramadhancamp.feature.bookmark.presentation.BookmarkViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val bookmarkModule = module {
    viewModel { BookmarkViewModel(get()) }
}
