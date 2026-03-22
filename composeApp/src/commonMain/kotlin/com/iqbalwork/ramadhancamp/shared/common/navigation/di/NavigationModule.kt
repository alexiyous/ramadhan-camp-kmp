package com.iqbalwork.ramadhancamp.shared.common.navigation.di

import com.iqbalwork.ramadhancamp.shared.common.navigation.NavigationManager
import com.iqbalwork.ramadhancamp.shared.common.navigation.NavigationManagerImpl
import com.iqbalwork.ramadhancamp.shared.common.navigation.ResultNavigationRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module

private const val NAVIGATION_RESULT_SCOPE = "NAVIGATION_RESULT_SCOPE"

val navigationModule = module {

    single(named(NAVIGATION_RESULT_SCOPE)) { CoroutineScope(Dispatchers.Main) }

    single<ResultNavigationRepository> {
        ResultNavigationRepository(
            coroutineScope = get<CoroutineScope>(named(NAVIGATION_RESULT_SCOPE)),
        )
    }

    factory { NavigationManagerImpl(get()) } bind NavigationManager::class
}
