package com.iqbalwork.ramadhancamp.feature.home.presentation.locationpicker

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
    LocationPickerContent(state = state, action = viewModel.rememberDispatch())
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocationPickerContent(
    state: LocationPickerState,
    action: (LocationPickerEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    val colors = RamadhanTheme.colors
    val typography = RamadhanTheme.typography

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Pilih Lokasi",
                        style = typography.headlineSmall,
                        color = colors.textPrimary,
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { action(LocationPickerEvent.Cancel) }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Kembali",
                            tint = colors.textPrimary,
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = colors.bgPrimary,
                ),
            )
        },
        containerColor = colors.bgPrimary,
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
        ) {
            // ── Province section ─────────────────────────────────────────────
            LocationSectionHeader(title = "PILIH PROVINSI", colors = colors, typography = typography)
            if (state.isLoadingProvinces) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(if (state.selectedProvince == null) 0.90f else 0.45f),
                    contentAlignment = Alignment.Center,
                ) {
                    CircularProgressIndicator(color = colors.accentPrimary)
                }
            } else {
                LazyColumn(
                    modifier = Modifier.weight(if (state.selectedProvince == null) 0.90f else 0.45f),
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

            // ── City section — visible after province selected ────────────────
            if (state.selectedProvince != null) {
                HorizontalDivider(thickness = 1.dp, color = colors.divider)
                LocationSectionHeader(
                    title = "PILIH KOTA / KABUPATEN",
                    colors = colors,
                    typography = typography,
                )
                Box(
                    modifier = Modifier
                        .weight(0.45f)
                        .fillMaxWidth(),
                ) {
                    if (state.isLoadingCities) {
                        CircularProgressIndicator(
                            color = colors.accentPrimary,
                            modifier = Modifier.align(Alignment.Center),
                        )
                    } else {
                        LazyColumn(modifier = Modifier.fillMaxSize()) {
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
                    }
                }
            }

            // ── Confirm button ────────────────────────────────────────────────
            Button(
                onClick = { action(LocationPickerEvent.Confirm) },
                enabled = state.canConfirm,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 16.dp)
                    .navigationBarsPadding(),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colors.accentPrimary,
                    disabledContainerColor = colors.accentPrimary.copy(alpha = 0.30f),
                    contentColor = colors.textOnLight,
                    disabledContentColor = colors.textOnLight.copy(alpha = 0.50f),
                ),
            ) {
                Text(
                    text = "Konfirmasi Lokasi",
                    style = typography.labelLarge,
                )
            }
        }
    }
}

@Composable
private fun LocationSectionHeader(
    title: String,
    colors: RamadhanColorScheme,
    typography: TypographyScheme,
) {
    Text(
        text = title,
        style = typography.labelSmall,
        color = colors.textMuted,
        modifier = Modifier
            .fillMaxWidth()
            .background(colors.bgSecondary)
            .padding(horizontal = 20.dp, vertical = 10.dp),
    )
}

@Composable
private fun LocationPickerItem(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
    colors: RamadhanColorScheme,
    typography: TypographyScheme,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(if (selected) colors.bgSurface else Color.Transparent)
            .clickable(onClick = onClick)
            .padding(horizontal = 20.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = text,
            style = typography.bodyLarge,
            color = if (selected) colors.accentPrimary else colors.textPrimary,
            modifier = Modifier.weight(1f),
        )
        if (selected) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = null,
                tint = colors.accentPrimary,
                modifier = Modifier.size(18.dp),
            )
        }
    }
}
