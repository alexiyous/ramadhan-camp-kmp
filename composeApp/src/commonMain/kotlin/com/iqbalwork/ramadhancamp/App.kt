package com.iqbalwork.ramadhancamp

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.iqbalwork.ramadhancamp.core.domain.model.StartScreen
import com.iqbalwork.ramadhancamp.core.presentation.mapper.toRootDestination
import com.iqbalwork.ramadhancamp.feature.auth.presentation.AuthScreen
import com.iqbalwork.ramadhancamp.feature.main.presentation.MainScreen
import com.iqbalwork.ramadhancamp.shared.common.navigation.LocalBackStackNode
import com.iqbalwork.ramadhancamp.shared.common.navigation.RootDestination
import com.iqbalwork.ramadhancamp.shared.common.navigation.rememberRootBackStack
import com.iqbalwork.ramadhancamp.shared.common.ui.theme.RamadhanTheme

@Composable
fun App(
    startScreen: StartScreen = StartScreen.Main,
) {
    RamadhanTheme {
        val backStackNode = rememberRootBackStack(startScreen.toRootDestination(), "APP")

        CompositionLocalProvider(LocalBackStackNode provides backStackNode) {
            NavDisplay(
                backStack = backStackNode.backStack,
                entryDecorators = listOf(
                    rememberSaveableStateHolderNavEntryDecorator(),
                    rememberViewModelStoreNavEntryDecorator(),
                ),
                transitionSpec    = { slideInHorizontally { it }  togetherWith slideOutHorizontally { -it } },
                popTransitionSpec = { slideInHorizontally { -it } togetherWith slideOutHorizontally { it } },
                entryProvider = entryProvider {
                    entry<RootDestination.Auth> { AuthScreen() }
                    entry<RootDestination.Main> { dest -> MainScreen(dest.initialTab) }
                },
            )
        }
    }
}
