package com.iqbalwork.ramadhancamp.feature.home.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.iqbalwork.ramadhancamp.shared.common.extension.rememberViewModel
import com.iqbalwork.ramadhancamp.shared.common.ui.rememberDispatch

@Composable
fun HomeMainScreen() {
    val viewModel: HomeViewModel = rememberViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()
    val dispatch = viewModel.rememberDispatch()



}
