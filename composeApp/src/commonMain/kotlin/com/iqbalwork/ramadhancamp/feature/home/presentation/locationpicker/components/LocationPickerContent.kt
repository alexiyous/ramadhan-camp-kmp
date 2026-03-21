package com.iqbalwork.ramadhancamp.feature.home.presentation.locationpicker.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.iqbalwork.ramadhancamp.feature.home.presentation.locationpicker.model.LocationPickerEvent
import com.iqbalwork.ramadhancamp.feature.home.presentation.locationpicker.model.LocationPickerState
import com.iqbalwork.ramadhancamp.shared.common.ui.components.error.RamadhanErrorEmptyState
import com.iqbalwork.ramadhancamp.shared.common.ui.components.error.toErrorEmptyState
import com.iqbalwork.ramadhancamp.shared.common.ui.components.loading.Loader
import com.iqbalwork.ramadhancamp.shared.common.ui.theme.RamadhanTheme
import com.iqbalwork.ramadhancamp.shared.common.utils.AppError

private sealed interface AnimateContentState {
    data object Loading : AnimateContentState
    class Error(val error: AppError) : AnimateContentState
    data object Success : AnimateContentState
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocationPickerContent(
    state: LocationPickerState,
    action: (LocationPickerEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    val colors = RamadhanTheme.colors

    Box(
        modifier = modifier
            .background(colors.bgPrimary)
    ) {
        AnimatedContent(
            modifier = Modifier.fillMaxSize(),
            targetState = when {
                state.isLoading -> AnimateContentState.Loading
                state.error != null -> AnimateContentState.Error(state.error)
                else -> AnimateContentState.Success
            }
        ) { targetState ->
            when (targetState) {
                is AnimateContentState.Error -> RamadhanErrorEmptyState(
                    modifier = Modifier.fillMaxSize(),
                    errorEmptyState = targetState.error.toErrorEmptyState(),
                    onButtonClick = { action(LocationPickerEvent.LoadProvinces) },
                )
                AnimateContentState.Loading -> Loader(
                    modifier = Modifier.fillMaxSize()
                )
                AnimateContentState.Success -> LocationPickerSuccessContent(
                    modifier = Modifier.fillMaxSize(),
                    state = state,
                    action = action
                )
            }
        }
    }
}