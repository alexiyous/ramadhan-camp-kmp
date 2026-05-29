package com.iqbalwork.ramadhancamp.feature.about.di

import com.iqbalwork.ramadhancamp.feature.about.data.repository.OssLicenseRepositoryImpl
import com.iqbalwork.ramadhancamp.feature.about.domain.repository.OssLicenseRepository
import com.iqbalwork.ramadhancamp.feature.about.presentation.AboutViewModel
import com.iqbalwork.ramadhancamp.feature.about.presentation.OssLicensesViewModel
import com.iqbalwork.ramadhancamp.feature.about.presentation.model.AboutScreenParameters
import com.iqbalwork.ramadhancamp.feature.about.presentation.model.OssLicensesScreenParameters
import com.iqbalwork.ramadhancamp.shared.common.navigation.BackStackNode
import com.iqbalwork.ramadhancamp.shared.common.navigation.NavigationManager
import com.iqbalwork.ramadhancamp.shared.common.navigation.TabState
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.dsl.bind
import org.koin.dsl.module

val aboutModule = module {
    viewModel { params ->
        AboutViewModel(
            navController = get<NavigationManager> {
                parametersOf(
                    params.get<BackStackNode>(),
                    params.get<TabState>(),
                )
            },
            params = params.get<AboutScreenParameters>(),
            repository = get(),
        )
    }
    viewModel { params ->
        OssLicensesViewModel(
            navController = get<NavigationManager> {
                parametersOf(
                    params.get<BackStackNode>(),
                    params.get<TabState>(),
                )
            },
            params = params.get<OssLicensesScreenParameters>(),
            repository = get(),
        )
    }
    factoryOf(::OssLicenseRepositoryImpl) bind OssLicenseRepository::class
}
