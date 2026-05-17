package com.iqbalwork.ramadhancamp.feature.bookmark.di

import com.iqbalwork.ramadhancamp.feature.bookmark.data.repositories.BookmarkRepositoryImpl
import com.iqbalwork.ramadhancamp.feature.bookmark.domain.repository.BookmarkRepository
import com.iqbalwork.ramadhancamp.feature.bookmark.presentation.BookmarkViewModel
import com.iqbalwork.ramadhancamp.shared.common.database.AppDatabase
import com.iqbalwork.ramadhancamp.shared.common.navigation.BackStackNode
import com.iqbalwork.ramadhancamp.shared.common.navigation.NavigationManager
import com.iqbalwork.ramadhancamp.shared.common.navigation.TabState
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.dsl.bind
import org.koin.dsl.module

val bookmarkModule = module {
    factory { get<AppDatabase>().bookmarkDao() }
    factoryOf(::BookmarkRepositoryImpl) bind BookmarkRepository::class

    viewModel<BookmarkViewModel> { params ->
        BookmarkViewModel(
            navigationManager = get<NavigationManager> {
                parametersOf(params.get<BackStackNode>(), params.get<TabState>())
            },
            bookmarkRepository = get()
        )
    }
}
