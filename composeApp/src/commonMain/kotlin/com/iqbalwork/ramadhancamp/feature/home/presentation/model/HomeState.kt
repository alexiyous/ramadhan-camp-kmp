package com.iqbalwork.ramadhancamp.feature.home.presentation.model

import com.iqbalwork.ramadhancamp.shared.common.ui.components.error.ErrorEmptyState
import com.iqbalwork.ramadhancamp.shared.common.utils.AppError

data class HomeState(
    val isLoading: Boolean = false,
    val appError: AppError? = null,
    val emptyErrorState: ErrorEmptyState? = null,
    val screenData: HomeScreenUiModel = HomeScreenUiModel()
)
