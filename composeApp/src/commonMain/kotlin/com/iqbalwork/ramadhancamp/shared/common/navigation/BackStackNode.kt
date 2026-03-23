package com.iqbalwork.ramadhancamp.shared.common.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.rememberNavBackStack

class BackStackNode(
    val key: Any? = null,
    val backStack: NavBackStack<NavKey>,
    val parent: BackStackNode?,
) {
    val level: Int = if (parent == null) 0 else parent.level + 1

    val root: BackStackNode get() = parent?.root ?: this

    val isRoot: Boolean get() = parent == null

    fun nodeAtLevel(targetLevel: Int): BackStackNode? = when {
        level == targetLevel -> this
        level > targetLevel  -> parent?.nodeAtLevel(targetLevel)
        else                 -> null
    }
}

const val ROOT_LEVEL = 0
const val TAB_LEVEL = 1

val LocalBackStackNode = staticCompositionLocalOf<BackStackNode> {
    error(
        "No BackStackNode provided. Wrap your NavDisplay with " +
            "CompositionLocalProvider(LocalBackStackNode provides node)"
    )
}
@Composable
fun rememberRootBackStack(initialDestination: NavKey, key: String): BackStackNode {
    val backStack = rememberNavBackStack(appSavedStateConfig, initialDestination)
    return remember { BackStackNode(backStack = backStack, parent = null, key = key) }
}

@Composable
fun rememberTabBackStack(initialDestination: NavKey, key: String): BackStackNode =
    rememberChildBackStack(initialDestination, key)

@Composable
fun rememberChildBackStack(initialDestination: NavKey, key: String): BackStackNode {
    val parent    = LocalBackStackNode.current
    val backStack = rememberNavBackStack(appSavedStateConfig, initialDestination)
    return remember{ BackStackNode(backStack = backStack, parent = parent, key = key) }
}


