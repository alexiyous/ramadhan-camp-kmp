package com.iqbalwork.ramadhancamp.feature.home.presentation.locationpicker.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.iqbalwork.ramadhancamp.shared.common.ui.theme.RamadhanColorScheme
import com.iqbalwork.ramadhancamp.shared.common.ui.theme.TypographyScheme

@Composable
fun LocationSectionHeader(
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