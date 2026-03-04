package com.iqbalwork.ramadhancamp.feature.home.presentation.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.iqbalwork.ramadhancamp.feature.home.presentation.model.HomeEvent
import com.iqbalwork.ramadhancamp.shared.common.ui.components.error.ErrorEmptyState
import com.iqbalwork.ramadhancamp.shared.common.ui.components.error.RamadhanErrorEmptyState

@Composable
fun HomeMainErrorContent(
    permissionDenied: Boolean ,
    error: ErrorEmptyState,
    action: (HomeEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    RamadhanErrorEmptyState(
        modifier = modifier,
        errorEmptyState = error,
        onButtonClick = {
            if (permissionDenied) action(HomeEvent.GoToSetting)
            else action(HomeEvent.LoadInitialData)
        }
    )
}