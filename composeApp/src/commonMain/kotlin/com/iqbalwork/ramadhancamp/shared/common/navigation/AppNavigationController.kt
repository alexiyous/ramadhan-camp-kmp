package com.iqbalwork.ramadhancamp.shared.common.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.rememberNavBackStack
import kotlinx.coroutines.flow.SharedFlow
import org.koin.compose.koinInject


/**
 * Navigation levels:
 *  - Root back stack  : [RootDestination] — auth, main, full-screen overlays
 *  - Tab back stacks  : [TabDestination] + [DialogDestination] — screens and dialogs inside each
 *                       bottom-tab navigator. Dialogs are pushed/popped directly on the tab stack;
 *                       [BottomSheetSceneStrategy] detects them by entry metadata.
 *
 * Lives as a Koin singleton. Back-stack references are vars updated by
 * [getAppNavigationController] on every recomposition so ViewModels always
 * push to the live stacks — even after a configuration change.
 */
class AppNavigationController(
    private val resultRepository: ResultNavigationRepository,
) : AppNavigationAction, ResultNavigationAction {

    var rootBackStack: NavBackStack<NavKey>? = null
    var tabBackStacks: Map<AppTab, NavBackStack<NavKey>> = emptyMap()
    var currentTab: MutableState<AppTab> = mutableStateOf(AppTab.Home)

    // ─── Root navigation ─────────────────────────────────────────────────────

    override fun navigateTo(dest: RootDestination, withReplace: Boolean) {
        val stack = rootBackStack ?: return
        if (withReplace) {
            if (stack.isNotEmpty()) stack[stack.lastIndex] = dest
            else stack.add(dest)
        } else {
            stack.add(dest)
        }
    }

    // ─── In-tab navigation ───────────────────────────────────────────────────

    override fun navigateToInsideTab(dest: TabDestination, withReplace: Boolean) {
        val stack = tabBackStacks[currentTab.value] ?: return
        if (withReplace) {
            if (stack.isNotEmpty()) stack[stack.lastIndex] = dest
            else stack.add(dest)
        } else {
            stack.add(dest)
        }
    }

    // ─── Back navigation ─────────────────────────────────────────────────────

    override fun back(navigationResult: NavigationResult?) {
        navigationResult?.let { sendResult(it) }
        val root = rootBackStack ?: return
        val tabStack = tabBackStacks[currentTab.value]
        when {
            tabStack != null && tabStack.size > 1 -> tabStack.removeLastOrNull()
            root.size > 1 -> root.removeLastOrNull()
        }
    }

    override fun backToScreen(key: NavKey, navigationResult: NavigationResult?) {
        navigationResult?.let { sendResult(it) }
        val root = rootBackStack ?: return
        val tabStack = tabBackStacks[currentTab.value]
        if (tabStack != null && tabStack.size > 1) {
            while (tabStack.size > 1 && tabStack.last() != key) {
                tabStack.removeLastOrNull()
            }
        } else {
            while (root.size > 1 && root.last() != key) {
                root.removeLastOrNull()
            }
        }
    }

    // ─── Flow / tab navigation ───────────────────────────────────────────────

    override fun startNewFlow(dest: RootDestination) {
        val stack = rootBackStack ?: return
        stack.clear()
        stack.add(dest)
    }

    override fun switchTab(tab: AppTab) {
        currentTab.value = tab
    }

    // ─── Dialog navigation ───────────────────────────────────────────────────

    override fun showDialog(dialog: DialogDestination) {
        tabBackStacks[currentTab.value]?.add(dialog)
    }

    override fun hideDialog() {
        val tabStack = tabBackStacks[currentTab.value] ?: return
        if (tabStack.lastOrNull() is DialogDestination) {
            tabStack.removeLastOrNull()
        }
    }

    // ─── Navigation results ──────────────────────────────────────────────────

    override fun sendResult(value: NavigationResult) {
        resultRepository.sendResult(value.key, value)
    }

    override fun removeKey(key: String) {
        resultRepository.removeKey(key)
    }

    override fun subscribeToResult(key: String): SharedFlow<NavigationResult> {
        return resultRepository.getResultFlow(key)
    }
}

// ─── Interfaces ──────────────────────────────────────────────────────────────

interface AppNavigationAction {
    fun navigateTo(dest: RootDestination, withReplace: Boolean = false)
    fun navigateToInsideTab(dest: TabDestination, withReplace: Boolean = false)
    fun back(navigationResult: NavigationResult? = null)
    fun backToScreen(key: NavKey, navigationResult: NavigationResult? = null)
    fun startNewFlow(dest: RootDestination)
    fun switchTab(tab: AppTab)
    fun showDialog(dialog: DialogDestination)
    fun hideDialog()
}

interface ResultNavigationAction {
    fun sendResult(value: NavigationResult)

    fun removeKey(key: String)

    fun subscribeToResult(key: String): SharedFlow<NavigationResult>
}

// ─── Factory ─────────────────────────────────────────────────────────────────

/**
 * Injects the singleton [AppNavigationController] and wires the Compose-managed
 * back stacks into it on every recomposition. Direct assignment (rather than
 * SideEffect) is intentional: [NavDisplay] reads [AppNavigationController.rootBackStack]
 * in the same composition pass, so the value must be set before that call.
 */
@Composable
fun getAppNavigationController(
    startDestination: RootDestination,
): AppNavigationController {
    val controller: AppNavigationController = koinInject()

    val rootBackStack     = rememberNavBackStack(appSavedStateConfig, startDestination)
    val currentTab        = rememberSaveable { mutableStateOf(AppTab.Home) }
    val homeBackStack     = rememberNavBackStack(appSavedStateConfig, TabDestination.HomeMain     as NavKey)
    val prayBackStack     = rememberNavBackStack(appSavedStateConfig, TabDestination.PrayMain     as NavKey)
    val quranBackStack    = rememberNavBackStack(appSavedStateConfig, TabDestination.QuranMain    as NavKey)
    val qiblaBackStack    = rememberNavBackStack(appSavedStateConfig, TabDestination.QiblaMain    as NavKey)
    val bookmarkBackStack = rememberNavBackStack(appSavedStateConfig, TabDestination.BookmarkMain as NavKey)

    controller.rootBackStack      = rootBackStack
    controller.currentTab = currentTab
    controller.tabBackStacks = mapOf(
        AppTab.Home     to homeBackStack,
        AppTab.Pray     to prayBackStack,
        AppTab.Quran    to quranBackStack,
        AppTab.Qibla    to qiblaBackStack,
        AppTab.Bookmark to bookmarkBackStack,
    )

    return controller
}

val LocalAppNavController = compositionLocalOf<AppNavigationController> {
    error("AppNavigationController not provided")
}
