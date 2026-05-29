package com.iqbalwork.ramadhancamp.feature.about.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.iqbalwork.ramadhancamp.feature.about.domain.model.OssLicense
import com.iqbalwork.ramadhancamp.shared.common.ui.theme.RamadhanTheme
import org.jetbrains.compose.resources.stringResource
import ramadhancamp.composeapp.generated.resources.Res
import ramadhancamp.composeapp.generated.resources.oss_licenses_by

@Composable
fun OssLicenseItem(
    license: OssLicense,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.padding(horizontal = 16.dp, vertical = 12.dp),
    ) {
        Text(
            text = license.name,
            style = RamadhanTheme.typography.headlineSmall,
            color = RamadhanTheme.colors.textPrimary,
        )
        Text(
            text = stringResource(Res.string.oss_licenses_by, license.author),
            style = RamadhanTheme.typography.bodyLarge,
            color = RamadhanTheme.colors.textSecondary,
        )
        Text(
            text = license.licenseType,
            style = RamadhanTheme.typography.labelSmall,
            color = RamadhanTheme.colors.textMuted,
        )
    }
}
