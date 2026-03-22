package com.iqbalwork.ramadhancamp.shared.common.extension

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.lifecycle.ViewModel
import com.iqbalwork.ramadhancamp.shared.common.navigation.LocalBackStackNode
import com.iqbalwork.ramadhancamp.shared.common.navigation.LocalCurrentTab
import com.iqbalwork.ramadhancamp.shared.common.ui.BaseViewModel
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.qualifier.Qualifier

@Composable
internal inline fun <reified T : ViewModel> rememberViewModel(
    qualifier: Qualifier? = null,
): T {
    val node     = LocalBackStackNode.current
    val tabState = LocalCurrentTab.current

    val viewModel = koinViewModel<T>(qualifier = qualifier)

    LaunchedEffect(node) {
        if (viewModel is BaseViewModel<*, *, *, *>) {
            viewModel.syncBackStack(node, tabState)
        }
    }

    return viewModel
}
