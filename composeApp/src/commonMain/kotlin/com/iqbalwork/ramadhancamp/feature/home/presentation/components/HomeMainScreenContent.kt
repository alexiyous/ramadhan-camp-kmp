package com.iqbalwork.ramadhancamp.feature.home.presentation.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.iqbalwork.ramadhancamp.feature.home.presentation.model.HomeEvent
import com.iqbalwork.ramadhancamp.feature.home.presentation.model.HomeState
import com.iqbalwork.ramadhancamp.shared.common.extension.debouncedClickable
import com.iqbalwork.ramadhancamp.shared.common.ui.components.error.ErrorEmptyState
import com.iqbalwork.ramadhancamp.shared.common.ui.components.error.toErrorEmptyState
import com.iqbalwork.ramadhancamp.shared.common.ui.components.loading.Loader
import com.iqbalwork.ramadhancamp.shared.common.ui.theme.RamadhanTheme
import com.iqbalwork.ramadhancamp.shared.common.utils.AppError

private sealed interface AnimateContentState {
    data object Loading : AnimateContentState
    class Error(val error: ErrorEmptyState) : AnimateContentState
    data object Success : AnimateContentState
}

@Composable
fun HomeMainScreenContent(
    state: HomeState,
    action: (HomeEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .background(RamadhanTheme.colors.bgPrimary)
    ) {
        AnimatedContent(
            modifier = Modifier.fillMaxSize(),
            targetState = when {
                state.isLoading -> AnimateContentState.Loading
                state.emptyErrorState != null -> AnimateContentState.Error(state.emptyErrorState)
                state.appError != null -> AnimateContentState.Error(state.appError.toErrorEmptyState())
                else -> AnimateContentState.Success
            }
        ) { targetState ->
            when(targetState) {
                is AnimateContentState.Error -> HomeMainErrorContent(
                    permissionDenied = state.isPermissionDenied,
                    error = targetState.error,
                    onRetry = { action(HomeEvent.LoadInitialData) },
                    onPermissionDenied = { action(HomeEvent.GoToSetting) },
                    modifier = Modifier.fillMaxSize()
                )
                AnimateContentState.Loading -> Loader(
                    modifier = Modifier.fillMaxSize()
                )
                AnimateContentState.Success -> HomeMainSuccessContent(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 24.dp)
                        .padding(top = 12.dp)
                    ,
                    homeMainData = state.screenData,
                    onSearchBoxClicked = { action(HomeEvent.OnSearchBoxClicked) },
                    onLastSurahCardClick = {
                        action(HomeEvent.OnLastSurahClicked)
                    },
                    onPopularSurahClick = { surahId ->
                        action(HomeEvent.OnPopularSurahClicked(surahId))
                    },
                    onAboutClick = { action(HomeEvent.NavigateToAbout) }
                )
            }
        }
    }
}
