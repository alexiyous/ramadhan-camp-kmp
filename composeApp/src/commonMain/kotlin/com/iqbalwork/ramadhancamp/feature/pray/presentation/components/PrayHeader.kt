package com.iqbalwork.ramadhancamp.feature.pray.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.iqbalwork.ramadhancamp.shared.common.ui.theme.RamadhanTheme

@Composable
fun PrayHeader(
    city: String,
    country: String,
    selectedDate: String,
    onCalendarClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = RamadhanTheme.colors
    val typography = RamadhanTheme.typography

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier.size(16.dp),
                    imageVector = Icons.Outlined.LocationOn,
                    contentDescription = "Location",
                    tint = colors.accentPrimary
                )

                Text(
                    text = "$city, $country",
                    style = typography.headlineSmall,
                    color = colors.accentPrimary
                )
            }

            Text(
                text = selectedDate,
                style = typography.labelSmall,
                color = colors.textMuted
            )
        }
        IconButton(onClick = onCalendarClick) {
            Icon(
                imageVector = Icons.Default.CalendarMonth,
                contentDescription = "Pick date",
                tint = colors.textSecondary
            )
        }
    }
}

@Preview
@Composable
private fun PrayHeaderPreview() {
    RamadhanTheme {
        Surface(color = RamadhanTheme.colors.bgPrimary) {
            PrayHeader(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                city = "Jakarta",
                selectedDate = "Friday, 10 March 2026",
                country = "Indonesia",
                onCalendarClick = {}
            )
        }
    }
}
