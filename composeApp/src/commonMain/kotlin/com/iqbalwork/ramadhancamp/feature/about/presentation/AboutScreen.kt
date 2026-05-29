package com.iqbalwork.ramadhancamp.feature.about.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement.Absolute.spacedBy
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ChevronRight
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.iqbalwork.ramadhancamp.feature.about.presentation.model.AboutEvent
import com.iqbalwork.ramadhancamp.feature.about.presentation.model.AboutScreenParameters
import com.iqbalwork.ramadhancamp.feature.about.presentation.model.AboutState
import com.iqbalwork.ramadhancamp.shared.common.extension.rememberViewModel
import com.iqbalwork.ramadhancamp.shared.common.ui.rememberDispatch
import com.iqbalwork.ramadhancamp.shared.common.ui.theme.RamadhanTheme
import org.jetbrains.compose.resources.stringResource
import org.koin.core.parameter.parametersOf
import ramadhancamp.composeapp.generated.resources.Res
import ramadhancamp.composeapp.generated.resources.about_app_name
import ramadhancamp.composeapp.generated.resources.about_copyright
import ramadhancamp.composeapp.generated.resources.about_equran_attribution
import ramadhancamp.composeapp.generated.resources.about_license
import ramadhancamp.composeapp.generated.resources.about_oss_licenses
import ramadhancamp.composeapp.generated.resources.about_version

@Composable
fun AboutScreen() {
    val viewModel: AboutViewModel = rememberViewModel(parameters = { parametersOf(AboutScreenParameters()) })
    val state by viewModel.state.collectAsStateWithLifecycle()
    val dispatch = viewModel.rememberDispatch()
    AboutContent(state = state, action = dispatch)
}

@Composable
fun AboutContent(
    state: AboutState,
    action: (AboutEvent) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(RamadhanTheme.colors.bgPrimary)
            .statusBarsPadding()
            .padding(horizontal = 24.dp, vertical = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.height(48.dp))

        // App icon placeholder
        Box(
            modifier = Modifier
                .size(80.dp)
                .background(
                    color = RamadhanTheme.colors.accentPrimary,
                    shape = CircleShape,
                ),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = "R",
                style = RamadhanTheme.typography.headlineLarge,
                color = RamadhanTheme.colors.textOnLight,
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // App name
        Text(
            text = stringResource(Res.string.about_app_name),
            style = RamadhanTheme.typography.headlineLarge,
            color = RamadhanTheme.colors.textPrimary,
        )

        Spacer(modifier = Modifier.height(4.dp))

        // Version
        Text(
            text = stringResource(Res.string.about_version, state.appVersion),
            style = RamadhanTheme.typography.labelSmall,
            color = RamadhanTheme.colors.textMuted,
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Copyright
        Text(
            text = stringResource(Res.string.about_copyright),
            style = RamadhanTheme.typography.labelSmall,
            color = RamadhanTheme.colors.textMuted,
        )

        Spacer(modifier = Modifier.height(4.dp))

        // License type
        Text(
            text = stringResource(Res.string.about_license),
            style = RamadhanTheme.typography.labelSmall,
            color = RamadhanTheme.colors.textMuted,
        )

        Spacer(modifier = Modifier.height(24.dp))

        HorizontalDivider(color = RamadhanTheme.colors.divider)

        // eQuran attribution
        Text(
            text = stringResource(Res.string.about_equran_attribution),
            style = RamadhanTheme.typography.bodyLarge,
            color = RamadhanTheme.colors.textSecondary,
            modifier = Modifier.padding(vertical = 16.dp),
        )

        HorizontalDivider(color = RamadhanTheme.colors.divider)

        // OSS licenses row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { action(AboutEvent.NavigateToOssLicenses) }
                .padding(vertical = 16.dp),
            horizontalArrangement = spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = stringResource(Res.string.about_oss_licenses),
                style = RamadhanTheme.typography.bodyLarge,
                color = RamadhanTheme.colors.textPrimary,
                modifier = Modifier.weight(1f),
            )
            Icon(
                imageVector = Icons.Outlined.ChevronRight,
                contentDescription = null,
                tint = RamadhanTheme.colors.textMuted,
                modifier = Modifier.size(20.dp),
            )
        }

        HorizontalDivider(color = RamadhanTheme.colors.divider)
    }
}
