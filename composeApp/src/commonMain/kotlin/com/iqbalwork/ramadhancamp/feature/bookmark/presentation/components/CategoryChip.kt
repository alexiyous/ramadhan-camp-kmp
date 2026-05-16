package com.iqbalwork.ramadhancamp.feature.bookmark.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.iqbalwork.ramadhancamp.shared.common.ui.theme.RamadhanTheme

@Composable
fun CategoryChip(
    name: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val colors = RamadhanTheme.colors
    val typography = RamadhanTheme.typography

    val backgroundColor = if (isSelected) colors.accentPrimary else Color.Transparent
    val contentColor = if (isSelected) colors.textOnLight else colors.textPrimary
    val modifier = if (isSelected) {
        Modifier.background(backgroundColor, RoundedCornerShape(16.dp))
    } else {
        Modifier.border(1.dp, colors.divider, RoundedCornerShape(16.dp))
    }

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            text = name,
            style = typography.labelLarge,
            color = contentColor
        )
    }
}