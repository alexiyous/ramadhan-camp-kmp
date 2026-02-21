package com.iqbalwork.ramadhancamp.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.saveable.rememberSerializable
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import androidx.savedstate.compose.serialization.serializers.SnapshotStateListSerializer
import com.iqbalwork.ramadhancamp.application.MainBottomNavigation
import com.iqbalwork.ramadhancamp.navigation.BookmarkRoute
import com.iqbalwork.ramadhancamp.navigation.HomeRoute
import com.iqbalwork.ramadhancamp.navigation.MainRoute
import com.iqbalwork.ramadhancamp.navigation.QiblaRoute
import com.iqbalwork.ramadhancamp.navigation.QuranRoute
import com.iqbalwork.ramadhancamp.navigation.Route
import com.iqbalwork.ramadhancamp.navigation.topLevelRoutes

/**
 * iqbalfauzi
 * Email: work.iqbalfauzi@gmail.com
 * Github: https://github.com/iqbalwork
 */
@Composable
fun MainScreen() {
    val backStack: MutableList<Route> =
        rememberSerializable(serializer = SnapshotStateListSerializer()) {
            mutableStateListOf(HomeRoute)
        }

    Scaffold(
        bottomBar = {
            MainBottomNavigation(topLevelRoutes = topLevelRoutes, backStack = backStack)
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            NavDisplay(
                backStack = backStack,
                onBack = {
                    if (backStack.size > 1) {
                        backStack.removeAt(backStack.size - 1)
                    }
                },
                entryProvider = entryProvider {
                    entry<HomeRoute> {
                        HomeScreen()
                    }
                    entry<QuranRoute> {
                        QuranScreen()
                    }
                    entry<QiblaRoute> {
                        QiblaScreen()
                    }
                    entry<BookmarkRoute> {
                        BookmarkScreen()
                    }
                    entry<MainRoute> {
                        HomeScreen()
                    }
                }
            )
        }
    }
}
