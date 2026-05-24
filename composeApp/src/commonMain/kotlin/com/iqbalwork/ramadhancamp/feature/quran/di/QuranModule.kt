package com.iqbalwork.ramadhancamp.feature.quran.di

import com.iqbalwork.ramadhancamp.feature.quran.data.database.dao.AudioCheckpointDao
import com.iqbalwork.ramadhancamp.feature.quran.data.datasource.QuranRemoteDatasource
import com.iqbalwork.ramadhancamp.feature.quran.data.repositories.AudioCheckpointRepositoryImpl
import com.iqbalwork.ramadhancamp.feature.quran.data.repositories.QuranRepositoryImpl
import com.iqbalwork.ramadhancamp.feature.quran.domain.repository.AudioCheckpointRepository
import com.iqbalwork.ramadhancamp.feature.quran.domain.repository.QuranRepository
import com.iqbalwork.ramadhancamp.feature.quran.presentation.QuranDetailScreenParameters
import com.iqbalwork.ramadhancamp.feature.quran.presentation.QuranDetailViewModel
import com.iqbalwork.ramadhancamp.feature.quran.presentation.QuranMainViewModel
import com.iqbalwork.ramadhancamp.feature.quran.presentation.QuranSheetScreenParameters
import com.iqbalwork.ramadhancamp.feature.quran.presentation.QuranSheetViewModel
import com.iqbalwork.ramadhancamp.shared.common.database.AppDatabase
import com.iqbalwork.ramadhancamp.shared.common.navigation.BackStackNode
import com.iqbalwork.ramadhancamp.shared.common.navigation.NavigationManager
import com.iqbalwork.ramadhancamp.shared.common.navigation.TabState
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.dsl.bind
import org.koin.dsl.module

val quranModule = module {
    factoryOf(::QuranRemoteDatasource)
    factoryOf(::QuranRepositoryImpl) bind QuranRepository::class
    factory { get<AppDatabase>().audioCheckpointDao() }
    factoryOf(::AudioCheckpointRepositoryImpl) bind AudioCheckpointRepository::class

    viewModel<QuranMainViewModel> { params ->
        QuranMainViewModel(
            navigationManager = get<NavigationManager> {
                parametersOf(params.get<BackStackNode>(), params.get<TabState>())
            },
            quranRepository = get()
        )
    }

    viewModel<QuranDetailViewModel> { params ->
        QuranDetailViewModel(
            params = params.get<QuranDetailScreenParameters>(),
            navigationManager = get<NavigationManager> {
                parametersOf(params.get<BackStackNode>(), params.get<TabState>())
            },
            quranRepository = get(),
            shareManager = get(),
            updateLastSurahRead = get(),
            audioController = get(),
            bookmarkRepository = get(),
            audioCheckpointRepo = get()
        )
    }

    viewModel<QuranSheetViewModel> { params ->
        QuranSheetViewModel(
            params = params.get<QuranSheetScreenParameters>(),
            navigationManager = get<NavigationManager> {
                parametersOf(params.get<BackStackNode>(), params.get<TabState>())
            },
            shareManager = get(),
            bookmarkRepository = get()
        )
    }
}
