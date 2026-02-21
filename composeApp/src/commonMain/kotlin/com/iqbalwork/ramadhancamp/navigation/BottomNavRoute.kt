package com.iqbalwork.ramadhancamp.navigation

import kotlinx.serialization.Serializable

/**
 * iqbalfauzi
 * Email: work.iqbalfauzi@gmail.com
 * Github: https://github.com/iqbalwork
 */
@Serializable
sealed interface BottomNavRoute : Route

@Serializable
data object HomeRoute: BottomNavRoute

@Serializable
data object QuranRoute: BottomNavRoute

@Serializable
data object QiblaRoute: BottomNavRoute

@Serializable
data object BookmarkRoute: BottomNavRoute
