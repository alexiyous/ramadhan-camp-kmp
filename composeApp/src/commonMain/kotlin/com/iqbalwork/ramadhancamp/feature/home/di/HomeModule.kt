package com.iqbalwork.ramadhancamp.feature.home.di

import com.iqbalwork.ramadhancamp.feature.home.data.datasource.HomePreferences
import com.iqbalwork.ramadhancamp.feature.home.data.datasource.HomeRemoteDatasource
import com.iqbalwork.ramadhancamp.feature.home.data.repositories.HomeRepositoryImpl
import com.iqbalwork.ramadhancamp.feature.home.domain.repository.HomeRepository
import com.iqbalwork.ramadhancamp.feature.home.presentation.HomeViewModel
import com.iqbalwork.ramadhancamp.feature.home.presentation.locationpicker.LocationPickerViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.bind
import org.koin.dsl.module

val homeModule = module {
    viewModel { params -> HomeViewModel(
        params.get(),
        homeRepository = get()
    ) }
    viewModel { params -> LocationPickerViewModel(
        params.get(),
        homeRepository = get()
    ) }
    singleOf(::HomePreferences)
    factoryOf(::HomeRemoteDatasource)
    factoryOf(::HomeRepositoryImpl) bind HomeRepository::class
}
