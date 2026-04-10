package com.iqbalwork.ramadhancamp.feature.pray.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material.icons.filled.NotificationsOff
import androidx.compose.material.icons.outlined.NotificationsActive
import androidx.compose.material.icons.outlined.NotificationsOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.iqbalwork.ramadhancamp.feature.pray.domain.model.Prayers
import com.iqbalwork.ramadhancamp.feature.pray.presentation.model.PrayItemUiModel
import com.iqbalwork.ramadhancamp.shared.common.ui.theme.RamadhanTheme
import org.jetbrains.compose.resources.stringResource
import ramadhancamp.composeapp.generated.resources.Res
import ramadhancamp.composeapp.generated.resources.next_uppercase

@Composable
fun PrayerRowItem(
    item: PrayItemUiModel,
    onAlarmToggle: (key: Prayers, enabled: Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = RamadhanTheme.colors
    val typography = RamadhanTheme.typography
    val isHighlighted = item.isNextPrayer

    val containerColor = if (isHighlighted)
        colors.bgSurface.copy(alpha = 0.2f)
    else
        colors.bgSecondary

    val borderModifier = if (isHighlighted)
        Modifier.border(
            width = 2.dp,
            color = colors.borderMint.copy(alpha = 0.3f),
            shape = RoundedCornerShape(12.dp)
        )
    else Modifier.border(
        width = 1.dp,
        color = colors.textPrimary.copy(alpha = 0.05f),
        shape = RoundedCornerShape(12.dp)
    )

    Box(
        modifier = modifier
            .height(IntrinsicSize.Min)
            .clip(RoundedCornerShape(12.dp))
            .then(borderModifier)
            .background(containerColor)
    ) {
        if (isHighlighted) {
            VerticalDivider(
                modifier = Modifier
                    .fillMaxHeight(),
                thickness = 4.dp,
                color = colors.borderMint
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = if (isHighlighted) 20.dp else 16.dp,
                    end = 16.dp,
                    top = 16.dp,
                    bottom = 16.dp
                ),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = item.icon,
                    contentDescription = item.displayName,
                    tint = if (isHighlighted) colors.borderMint else colors.textSecondary,
                    modifier = Modifier.size(22.dp)
                )
                Column {
                    Text(
                        text = item.displayName,
                        style = typography.bodyLarge,
                        color = if (isHighlighted) colors.borderMint else colors.textPrimary,
                        fontWeight = if (isHighlighted) FontWeight.Bold else FontWeight.SemiBold
                    )
                    if (isHighlighted) {
                        Text(
                            text = stringResource(Res.string.next_uppercase),
                            style = typography.labelSmall,
                            color = colors.textMuted
                        )
                    }
                }
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = item.time,
                    style = typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                    color = colors.textPrimary
                )
                    IconButton(
                        onClick = { onAlarmToggle(item.key, !item.isAlarmOn) },
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            imageVector = if (item.isAlarmOn)
                                Icons.Outlined.NotificationsActive
                            else
                                Icons.Outlined.NotificationsOff,
                            contentDescription = if (item.isAlarmOn) "Alarm on" else "Alarm off",
                            tint = if (item.isAlarmOn) colors.borderMint else colors.textMuted,
                            modifier = Modifier.size(20.dp)
                        )
                    }

            }
        }
    }
}

@Preview
@Composable
private fun PrayerRowItemPreviews() {
    RamadhanTheme {
        Surface(color = RamadhanTheme.colors.bgPrimary) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Scenario 1: Normal row, Alarm is OFF
                PrayerRowItem(
                    item = PrayItemUiModel(
                        key = Prayers.SUBUH,
                        displayName = "Fajr",
                        time = "04:30",
                        icon = Icons.Default.NotificationsActive,
                        isNextPrayer = false,
                        isAlarmOn = false
                    ),
                    onAlarmToggle = { _, _ -> }
                )

                // Scenario 2: Normal row, Alarm is ON
                PrayerRowItem(
                    item = PrayItemUiModel(
                        key = Prayers.DZUHUR,
                        displayName = "Dhuhr",
                        time = "11:45",
                        icon = Icons.Default.NotificationsActive,
                        isNextPrayer = false,
                        isAlarmOn = true
                    ),
                    onAlarmToggle = { _, _ -> }
                )

                // Scenario 3: Highlighted (Next Prayer), Alarm is OFF
                PrayerRowItem(
                    item = PrayItemUiModel(
                        key = Prayers.ASHAR,
                        displayName = "Asr",
                        time = "15:00",
                        icon = Icons.Default.NotificationsActive,
                        isNextPrayer = true,
                        isAlarmOn = false
                    ),
                    onAlarmToggle = { _, _ -> }
                )

                // Scenario 4: Highlighted (Next Prayer), Alarm is ON
                PrayerRowItem(
                    item = PrayItemUiModel(
                        key = Prayers.MAGHRIB,
                        displayName = "Maghrib",
                        time = "17:45",
                        icon = Icons.Default.NotificationsActive,
                        isNextPrayer = true,
                        isAlarmOn = true
                    ),
                    onAlarmToggle = { _, _ -> }
                )
            }
        }
    }
}
