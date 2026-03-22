package com.iqbalwork.ramadhancamp.shared.common.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.rememberNavBackStack

class BackStackNode(
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
fun rememberRootBackStack(initialDestination: NavKey): BackStackNode {
    val backStack = rememberNavBackStack(appSavedStateConfig, initialDestination)
    return remember { BackStackNode(backStack = backStack, parent = null) }
}

@Composable
fun rememberTabBackStack(initialDestination: NavKey): BackStackNode =
    rememberChildBackStack(initialDestination)

@Composable
fun rememberChildBackStack(initialDestination: NavKey): BackStackNode {
    val parent    = LocalBackStackNode.current
    val backStack = rememberNavBackStack(appSavedStateConfig, initialDestination)
    return remember{ BackStackNode(backStack, parent = parent) }
}


