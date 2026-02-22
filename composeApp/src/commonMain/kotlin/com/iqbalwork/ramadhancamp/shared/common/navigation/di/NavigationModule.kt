package com.iqbalwork.ramadhancamp.shared.common.navigation.di

import com.iqbalwork.ramadhancamp.shared.common.navigation.AppNavigationControllerHolder
import com.iqbalwork.ramadhancamp.shared.common.navigation.ResultNavigationRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.koin.core.qualifier.named
import org.koin.dsl.module

private const val NAVIGATION_RESULT_SCOPE = "NAVIGATION_RESULT_SCOPE"

val navigationModule =
    module {
        single { AppNavigationControllerHolder() }

        single(named(NAVIGATION_RESULT_SCOPE)) { CoroutineScope(Dispatchers.Main) }

        single<ResultNavigationRepository> {
            ResultNavigationRepository(
                coroutineScope = get<CoroutineScope>(named(NAVIGATION_RESULT_SCOPE)),
            )
        }
    }