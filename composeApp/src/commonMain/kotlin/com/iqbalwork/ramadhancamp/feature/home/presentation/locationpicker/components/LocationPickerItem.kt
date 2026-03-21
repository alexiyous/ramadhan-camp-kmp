package com.iqbalwork.ramadhancamp.feature.home.presentation.locationpicker.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.iqbalwork.ramadhancamp.shared.common.ui.theme.RamadhanColorScheme
import com.iqbalwork.ramadhancamp.shared.common.ui.theme.TypographyScheme

@Composable
fun LocationPickerItem(
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
            color =  colors.textPrimary,
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