package com.iqbalwork.ramadhancamp.shared.common.navigation

/**
 * A synchronous holder for [AppNavigationController] registered as a Koin single.
 *
 * [AppNavigationController] is created inside a @Composable via [rememberAppNavigationController],
 * so it cannot be a Koin single itself. Instead, [App] calls [set] inside a [remember] block that
 * executes synchronously during composition — before [NavDisplay] renders any entries and before
 * any ViewModel constructor runs — guaranteeing that [get] is always safe to call from ViewModels.
 */
class AppNavigationControllerHolder {

    private var controller: AppNavigationController? = null

    fun set(controller: AppNavigationController) {
        this.controller = controller
    }

    fun get(): AppNavigationController =
        controller ?: error(
            "AppNavigationController has not been set yet. " +
                "Ensure AppNavigationControllerHolder.set() is called inside " +
                "remember(navController) { ... } before NavDisplay in App.kt."
        )
}
