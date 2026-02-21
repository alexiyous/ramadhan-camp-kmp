package com.iqbalwork.ramadhancamp.application

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Navigation
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.iqbalwork.ramadhancamp.navigation.BookmarkRoute
import com.iqbalwork.ramadhancamp.navigation.BottomNavRoute
import com.iqbalwork.ramadhancamp.navigation.HomeRoute
import com.iqbalwork.ramadhancamp.navigation.QiblaRoute
import com.iqbalwork.ramadhancamp.navigation.QuranRoute
import com.iqbalwork.ramadhancamp.navigation.Route

/**
 * iqbalfauzi
 * Email: work.iqbalfauzi@gmail.com
 * Github: https://github.com/iqbalwork
 */
@Composable
fun MainBottomNavigation(
    topLevelRoutes: List<BottomNavRoute>,
    backStack: MutableList<Route>
) {
    val currentRoute = backStack.lastOrNull()

    NavigationBar {
        topLevelRoutes.forEach { route ->
            val isSelected = currentRoute == route
            NavigationBarItem(
                icon = {
                    val icon = when (route) {
                        HomeRoute -> Icons.Default.Home
                        QuranRoute -> Icons.Default.Book
                        QiblaRoute -> Icons.Default.Navigation
                        BookmarkRoute -> Icons.Default.Bookmark
                    }
                    Icon(icon, contentDescription = route::class.simpleName)
                },
                label = {
                    Text(text = route::class.simpleName ?: "")
                },
                selected = isSelected,
                onClick = {
                    if (!isSelected) {
                        // For bottom nav, we usually want to replace the current top level route
                        // or clear backstack and add the new one.
                        // Simple implementation: replace the last if it's a BottomNavRoute
                        if (backStack.lastOrNull() is BottomNavRoute) {
                            backStack.removeAt(backStack.size - 1)
                        }
                        backStack.add(route)
                    }
                }
            )
        }
    }
}
