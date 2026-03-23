package com.iqbalwork.ramadhancamp.feature.home.di

import com.iqbalwork.ramadhancamp.feature.home.data.datasource.HomePreferences
import com.iqbalwork.ramadhancamp.feature.home.data.datasource.HomeRemoteDatasource
import com.iqbalwork.ramadhancamp.feature.home.data.repositories.HomeRepositoryImpl
import com.iqbalwork.ramadhancamp.feature.home.domain.repository.HomeRepository
import com.iqbalwork.ramadhancamp.feature.home.presentation.HomeMainScreenParameters
import com.iqbalwork.ramadhancamp.feature.home.presentation.HomeViewModel
import com.iqbalwork.ramadhancamp.feature.home.presentation.locationpicker.LocationPickerViewModel
import com.iqbalwork.ramadhancamp.shared.common.navigation.BackStackNode
import com.iqbalwork.ramadhancamp.shared.common.navigation.NavigationManager
import com.iqbalwork.ramadhancamp.shared.common.navigation.TabState
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.dsl.bind
import org.koin.dsl.module

val homeModule = module {
    viewModel {params ->
        HomeViewModel(
            navController = get<NavigationManager> {
                parametersOf(
                    params.get<BackStackNode>(),
                    params.get<TabState>(),
                )
            },
            homeRepository = get(),
            params = params.get<HomeMainScreenParameters>(),
        )
    }
    viewModel {params ->
        LocationPickerViewModel(
            navController = get<NavigationManager> {
                parametersOf(
                    params.get<BackStackNode>(),
                    params.get<TabState>(),
                )
            },
            homeRepository = get(),
        ) }
    singleOf(::HomePreferences)
    factoryOf(::HomeRemoteDatasource)
    factoryOf(::HomeRepositoryImpl) bind HomeRepository::class
}
