package com.iqbalwork.ramadhancamp.feature.home.presentation.locationpicker

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.iqbalwork.ramadhancamp.feature.home.presentation.locationpicker.components.LocationPickerContent
import com.iqbalwork.ramadhancamp.feature.home.presentation.locationpicker.model.LocationPickerEvent
import com.iqbalwork.ramadhancamp.feature.home.presentation.locationpicker.model.LocationPickerState
import com.iqbalwork.ramadhancamp.feature.home.presentation.locationpicker.model.canConfirm
import com.iqbalwork.ramadhancamp.shared.common.extension.rememberViewModel
import com.iqbalwork.ramadhancamp.shared.common.ui.rememberDispatch
import com.iqbalwork.ramadhancamp.shared.common.ui.theme.RamadhanColorScheme
import com.iqbalwork.ramadhancamp.shared.common.ui.theme.RamadhanTheme
import com.iqbalwork.ramadhancamp.shared.common.ui.theme.TypographyScheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocationPickerScreen() {
    val viewModel: LocationPickerViewModel = rememberViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()
    val action = viewModel.rememberDispatch()
    LocationPickerContent(
        modifier = Modifier.fillMaxSize(),
        state = state,
        action = action
    )
}
