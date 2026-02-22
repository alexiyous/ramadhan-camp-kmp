package com.iqbalwork.ramadhancamp

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.iqbalwork.ramadhancamp.core.domain.model.StartScreen
import com.iqbalwork.ramadhancamp.core.presentation.mapper.toRootDestination
import com.iqbalwork.ramadhancamp.feature.main.MainScreen
import com.iqbalwork.ramadhancamp.shared.common.navigation.AppNavigationControllerHolder
import com.iqbalwork.ramadhancamp.shared.common.navigation.ResultNavigationRepository
import com.iqbalwork.ramadhancamp.shared.common.navigation.RootDestination
import com.iqbalwork.ramadhancamp.shared.common.navigation.rememberAppNavigationController
import org.koin.compose.koinInject

@Composable
fun App(
    startScreen: StartScreen = StartScreen.Main,
) {
    MaterialTheme {
        val resultRepository: ResultNavigationRepository = koinInject()
        val navController = rememberAppNavigationController(
            startDestination = startScreen.toRootDestination(),
            resultRepository = resultRepository
        )
        val navControllerHolder: AppNavigationControllerHolder = koinInject()
        remember(navController) { navControllerHolder.set(navController) }

        NavDisplay(
            backStack = navController.rootBackStack,
            entryDecorators = listOf(
                rememberSaveableStateHolderNavEntryDecorator(),
                rememberViewModelStoreNavEntryDecorator(),
            ),
            transitionSpec = { slideInHorizontally { it } togetherWith slideOutHorizontally { -it } },
            popTransitionSpec = { slideInHorizontally { -it } togetherWith slideOutHorizontally { it } },
            entryProvider = entryProvider {
                entry<RootDestination.Auth> { /* AuthScreen() — placeholder */ }
                entry<RootDestination.Main> { MainScreen(navController) }
            },
        )
    }
}
