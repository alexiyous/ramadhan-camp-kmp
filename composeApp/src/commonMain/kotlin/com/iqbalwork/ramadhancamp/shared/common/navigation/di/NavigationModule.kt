package com.iqbalwork.ramadhancamp.shared.common.navigation.di

import com.iqbalwork.ramadhancamp.shared.common.navigation.BackStackNode
import com.iqbalwork.ramadhancamp.shared.common.navigation.NavigationManager
import com.iqbalwork.ramadhancamp.shared.common.navigation.NavigationManagerImpl
import com.iqbalwork.ramadhancamp.shared.common.navigation.ResultNavigationRepository
import com.iqbalwork.ramadhancamp.shared.common.navigation.TabState
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

    factory<NavigationManager> { params ->
        NavigationManagerImpl(
            resultRepository = get<ResultNavigationRepository>(),
            currentNode = params.get<BackStackNode>(),
            _tabState = params.get<TabState>(),
        )
    }
}
