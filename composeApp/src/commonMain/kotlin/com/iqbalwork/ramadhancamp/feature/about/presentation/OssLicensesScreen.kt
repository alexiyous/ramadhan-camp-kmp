package com.iqbalwork.ramadhancamp.feature.about.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.iqbalwork.ramadhancamp.feature.about.presentation.components.OssLicenseItem
import com.iqbalwork.ramadhancamp.feature.about.presentation.model.OssLicensesEvent
import com.iqbalwork.ramadhancamp.feature.about.presentation.model.OssLicensesScreenParameters
import com.iqbalwork.ramadhancamp.feature.about.presentation.model.OssLicensesState
import com.iqbalwork.ramadhancamp.shared.common.extension.rememberViewModel
import com.iqbalwork.ramadhancamp.shared.common.ui.rememberDispatch
import com.iqbalwork.ramadhancamp.shared.common.ui.theme.RamadhanTheme
import org.jetbrains.compose.resources.stringResource
import org.koin.core.parameter.parametersOf
import ramadhancamp.composeapp.generated.resources.Res
import ramadhancamp.composeapp.generated.resources.oss_licenses_title

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OssLicensesScreen() {
    val viewModel: OssLicensesViewModel = rememberViewModel(parameters = { parametersOf(OssLicensesScreenParameters()) })
    val state by viewModel.state.collectAsStateWithLifecycle()
    val dispatch = viewModel.rememberDispatch()
    OssLicensesContent(state = state, action = dispatch)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OssLicensesContent(
    state: OssLicensesState,
    action: (OssLicensesEvent) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(RamadhanTheme.colors.bgPrimary),
    ) {
        TopAppBar(
            title = {
                Text(
                    text = stringResource(Res.string.oss_licenses_title),
                    style = RamadhanTheme.typography.headlineSmall,
                    color = RamadhanTheme.colors.textPrimary,
                )
            },
            navigationIcon = {
                IconButton(onClick = { action(OssLicensesEvent.NavigateBack) }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = null,
                        tint = RamadhanTheme.colors.textPrimary,
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = RamadhanTheme.colors.bgPrimary,
            ),
            modifier = Modifier.statusBarsPadding(),
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
        ) {
            items(state.licenses, key = { it.name }) { license ->
                OssLicenseItem(license = license)
                HorizontalDivider(color = RamadhanTheme.colors.divider)
            }
        }
    }
}
