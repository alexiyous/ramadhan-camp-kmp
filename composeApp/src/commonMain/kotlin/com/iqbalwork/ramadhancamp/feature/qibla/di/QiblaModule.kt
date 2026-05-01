package com.iqbalwork.ramadhancamp.feature.qibla.di

import com.iqbalwork.ramadhancamp.feature.qibla.data.datasource.QiblaPreferences
import com.iqbalwork.ramadhancamp.feature.qibla.data.repositories.QiblaRepositoryImpl
import com.iqbalwork.ramadhancamp.feature.qibla.domain.repository.QiblaRepository
import com.iqbalwork.ramadhancamp.feature.qibla.presentation.QiblaScreenParameters
import com.iqbalwork.ramadhancamp.feature.qibla.presentation.QiblaViewModel
import com.iqbalwork.ramadhancamp.shared.common.navigation.BackStackNode
import com.iqbalwork.ramadhancamp.shared.common.navigation.NavigationManager
import com.iqbalwork.ramadhancamp.shared.common.navigation.TabState
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.dsl.bind
import org.koin.dsl.module

val qiblaModule = module {
    singleOf(::QiblaPreferences)
    factoryOf(::QiblaRepositoryImpl) bind QiblaRepository::class
    viewModel { params ->
        QiblaViewModel(
            navController = get<NavigationManager> {
                parametersOf(
                    params.get<BackStackNode>(),
                    params.get<TabState>(),
                )
            },
            params = params.get<QiblaScreenParameters>(),
            qiblaRepository = get())
    }
}
