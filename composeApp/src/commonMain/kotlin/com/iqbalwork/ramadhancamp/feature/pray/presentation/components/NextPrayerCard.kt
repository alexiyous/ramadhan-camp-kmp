package com.iqbalwork.ramadhancamp.feature.pray.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.iqbalwork.ramadhancamp.feature.pray.presentation.model.PrayCountdownUiModel
import com.iqbalwork.ramadhancamp.shared.common.ui.theme.RamadhanTheme
import org.jetbrains.compose.resources.stringResource
import ramadhancamp.composeapp.generated.resources.Res
import ramadhancamp.composeapp.generated.resources.next_shalat_schedule

@Composable
fun NextPrayerCard(
    countdown: PrayCountdownUiModel,
    modifier: Modifier = Modifier
) {
    val colors = RamadhanTheme.colors
    val typography = RamadhanTheme.typography

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(24.dp))
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(colors.bgSurface, colors.bgSecondary)
                )
            )
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                modifier = Modifier.padding(bottom = 4.dp),
                text = stringResource(Res.string.next_shalat_schedule),
                style = typography.labelSmall,
                color = colors.bgAccentLight.copy(alpha = 0.8f),
                letterSpacing = 0.7.sp
            )

            Text(
                modifier = Modifier.padding(bottom = 24.dp),
                text = countdown.prayerName,
                style = typography.headlineLarge,
                color = colors.textPrimary,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = countdown.remainingTime,
                style = typography.headlineLarge.copy(fontSize = 48.sp),
                color = colors.textPrimary,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 22.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = countdown.prevPrayerName,
                        style = typography.labelSmall,
                        color = colors.bgAccentLight.copy(alpha = 0.9f)
                    )
                    Text(
                        text = countdown.prevPrayerTime,
                        style = typography.bodyLarge,
                        color = colors.bgAccentLight.copy(alpha = 0.7f),
                        fontWeight = FontWeight.SemiBold
                    )
                }

                VerticalDivider(
                    modifier = Modifier.height(32.dp),
                    color = colors.dividerLightTeal
                )

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = countdown.nextPrayerName,
                        style = typography.labelSmall,
                        color = colors.bgAccentLight.copy(alpha = 0.9f)
                    )
                    Text(
                        text = countdown.nextPrayerTime,
                        style = typography.bodyLarge,
                        color = colors.bgAccentLight.copy(alpha = 0.7f),
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun NextPrayerCardPreview() {
    val dummyCountdown = PrayCountdownUiModel(
        prayerName = "Dzuhur",
        prayerTime = "11:45",
        remainingTime = "00:45:00",
        prevPrayerName = "Subuh",
        prevPrayerTime = "04:30",
        nextPrayerName = "Ashar",
        nextPrayerTime = "15:00"
    )

    RamadhanTheme {
        Surface(color = RamadhanTheme.colors.bgPrimary) {
            NextPrayerCard(
                countdown = dummyCountdown,
                modifier = Modifier.padding(16.dp).fillMaxWidth()
            )
        }
    }
}
