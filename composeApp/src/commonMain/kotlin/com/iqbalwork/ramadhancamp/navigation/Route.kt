package com.iqbalwork.ramadhancamp.navigation

import kotlinx.serialization.Serializable

/**
 * iqbalfauzi
 * Email: work.iqbalfauzi@gmail.com
 * Github: https://github.com/iqbalwork
 */
@Serializable
sealed interface Route

object BottomNavItem {
    const val HOME_NAV_ITEM = "home_screen"
    const val QURAN_NAV_ITEM = "quran_screen"
    const val QIBLA_NAV_ITEM = "qibla_screen"
    const val BOOKMARK_NAV_ITEM = "bookmark_screen"
}

data object MainRoute : Route

data object QuranRouteDetail : Route

val topLevelRoutes: List<BottomNavRoute> = listOf(HomeRoute, QuranRoute, QiblaRoute, BookmarkRoute)
