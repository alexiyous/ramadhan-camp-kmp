package com.iqbalwork.ramadhancamp.feature.home.presentation.locationpicker.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.iqbalwork.ramadhancamp.feature.home.presentation.locationpicker.model.LocationPickerEvent
import com.iqbalwork.ramadhancamp.feature.home.presentation.locationpicker.model.LocationPickerState
import com.iqbalwork.ramadhancamp.shared.common.ui.components.button.RamadhanButton
import com.iqbalwork.ramadhancamp.shared.common.ui.components.button.RamadhanButtonProps
import com.iqbalwork.ramadhancamp.shared.common.ui.theme.RamadhanTheme
import org.jetbrains.compose.resources.stringResource
import ramadhancamp.composeapp.generated.resources.Res
import ramadhancamp.composeapp.generated.resources.back
import ramadhancamp.composeapp.generated.resources.confirm_location
import ramadhancamp.composeapp.generated.resources.pick_city
import ramadhancamp.composeapp.generated.resources.pick_location
import ramadhancamp.composeapp.generated.resources.pick_province

private sealed interface AnimateLocationContentState {
    data object ShowProvincePicker : AnimateLocationContentState
    data object ShowCityPicker : AnimateLocationContentState
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocationPickerSuccessContent(
    modifier: Modifier,
    action: (LocationPickerEvent) -> Unit,
    state: LocationPickerState,
) {
    val colors = RamadhanTheme.colors
    val typography = RamadhanTheme.typography

    Scaffold(
        modifier = modifier,
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(Res.string.pick_location),
                        style = typography.headlineSmall,
                        color = colors.textPrimary,
                    )
                },
                navigationIcon = {
                    if (state.selectedProvince != null) {
                        IconButton(onClick = { action(LocationPickerEvent.Cancel) }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = stringResource(Res.string.back),
                                tint = colors.textPrimary,
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = colors.bgPrimary,
                ),
            )
        },
        containerColor = colors.bgPrimary,
    ) { innerPadding ->

        AnimatedContent(
            modifier = Modifier.fillMaxSize().padding(innerPadding),
            targetState = if (state.selectedProvince == null) AnimateLocationContentState.ShowProvincePicker
            else AnimateLocationContentState.ShowCityPicker,
            transitionSpec = {
                if (targetState == AnimateLocationContentState.ShowCityPicker) {
                    (slideInVertically { it } + fadeIn()) togetherWith fadeOut()
                } else {
                    fadeIn() togetherWith (slideOutVertically { it } + fadeOut())
                }
            },
        ) { targetState ->

            when (targetState) {
                AnimateLocationContentState.ShowProvincePicker -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        LocationSectionHeader(title = stringResource(Res.string.pick_province), colors = colors, typography = typography)

                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                        ) {
                            items(state.provinces) { province ->
                                LocationPickerItem(
                                    text = province,
                                    selected = state.selectedProvince == province,
                                    onClick = { action(LocationPickerEvent.SelectProvince(province)) },
                                    colors = colors,
                                    typography = typography,
                                )
                                HorizontalDivider(thickness = 0.5.dp, color = colors.divider)
                            }
                        }
                    }
                }
                AnimateLocationContentState.ShowCityPicker -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        LocationSectionHeader(
                            title = stringResource(Res.string.pick_city),
                            colors = colors,
                            typography = typography,
                        )

                        LazyColumn(modifier = Modifier.fillMaxWidth().weight(1f)) {
                            items(state.cities) { city ->
                                LocationPickerItem(
                                    text = city,
                                    selected = state.selectedCity == city,
                                    onClick = { action(LocationPickerEvent.SelectCity(city)) },
                                    colors = colors,
                                    typography = typography,
                                )
                                HorizontalDivider(thickness = 0.5.dp, color = colors.divider)
                            }
                        }

                        RamadhanButton(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 20.dp, vertical = 16.dp),
                            text = stringResource(Res.string.confirm_location),
                            enabled = state.selectedCity != null,
                            onClick = { action(LocationPickerEvent.Confirm) },
                            variant = RamadhanButtonProps.Variant.Primary
                        )
                    }
                }
            }
        }
    }
}