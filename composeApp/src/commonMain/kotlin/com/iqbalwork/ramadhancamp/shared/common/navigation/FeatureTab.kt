package com.iqbalwork.ramadhancamp.shared.common.navigation

import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import org.jetbrains.compose.resources.DrawableResource

abstract class FeatureTab {

    /** First destination pushed onto this tab's back stack. */
    abstract val initialDestination: NavKey

    /** Label shown under the bottom bar icon. */
    abstract val label: String

    /** Icon shown when this tab is selected. */
    abstract val selectedIcon: DrawableResource

    /** Icon shown when this tab is not selected. */
    abstract val unselectedIcon: DrawableResource

    /**
     * Creates this tab's [BackStackNode] at TAB_LEVEL (1).
     * Called once per composition from the tabbed screen — do not call manually.
     */
    @Composable
    abstract fun backstack(): BackStackNode

    /**
     * Registers all destinations for this tab's [NavDisplay].
     * Called from the shared entryProvider block in the tabbed screen.
     */
    abstract fun EntryProviderScope<NavKey>.registerEntries()
}
