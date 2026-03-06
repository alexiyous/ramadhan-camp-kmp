package com.iqbalwork.ramadhancamp.feature.home.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Arrangement.Absolute.spacedBy
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.AndroidUiModes.UI_MODE_TYPE_NORMAL
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.iqbalwork.ramadhancamp.shared.common.ui.theme.RamadhanTheme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import ramadhancamp.composeapp.generated.resources.Res
import ramadhancamp.composeapp.generated.resources.image_mosque
import ramadhancamp.composeapp.generated.resources.next
import ramadhancamp.composeapp.generated.resources.remaining_time

@Composable
fun CardNextPrayer(
    prayerName: String,
    prayerTime: String,
    remainingMinute: String,
    date: String,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .shadow(
                elevation = 20.dp,
                shape = RoundedCornerShape(16.dp),
                spotColor = RamadhanTheme.colors.bgSurface.copy(alpha = 0.20f),
                ambientColor = RamadhanTheme.colors.bgSurface.copy(alpha = 0.20f)
            )
            .clip(RoundedCornerShape(16.dp))
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(RamadhanTheme.colors.accentPrimary, RamadhanTheme.colors.bgSurfaceLight)
                )
            )
    ) {
        Column(modifier = Modifier.padding(24.dp)) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp)
            ) {
                Column(
                    modifier = Modifier.align(Alignment.CenterStart),
                    verticalArrangement = Arrangement.Center,
                ) {
                    Text(
                        text = stringResource(Res.string.next),
                        style = RamadhanTheme.typography.bodyMedium,
                        color = RamadhanTheme.colors.textPrimary
                    )
                    Text(
                        text = prayerName,
                        style = RamadhanTheme.typography.headlineLarge,
                        color = RamadhanTheme.colors.textPrimary
                    )
                    Text(
                        text = prayerTime,
                        style = RamadhanTheme.typography.titleMedium,
                        color = RamadhanTheme.colors.textPrimary
                    )
                }

                Column(
                    modifier = Modifier.align(Alignment.TopEnd),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.End
                ) {
                    Text(
                        text = stringResource(Res.string.remaining_time),
                        style = RamadhanTheme.typography.labelSmall.copy(fontWeight = FontWeight.Light),
                        color = RamadhanTheme.colors.textPrimary
                    )
                    Text(
                        text = "$remainingMinute Menit",
                        style = RamadhanTheme.typography.headlineMedium,
                        color = RamadhanTheme.colors.textPrimary
                    )
                }
            }

            Box(
                modifier = Modifier
                    .clip(CircleShape.copy(all = CornerSize(50)))
                    .background( Color.Black.copy(alpha = 0.20f))
                    .padding(vertical = 6.dp, horizontal = 12.dp)
            ) {
                Row(
                    modifier = Modifier.align(Alignment.Center),
                    horizontalArrangement = spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        modifier = Modifier.size(11.dp),
                        imageVector = Icons.Default.CalendarToday,
                        tint = RamadhanTheme.colors.textPrimary,
                        contentDescription = "Icon Tanggal"
                    )
                    Text(
                        text = date,
                        style = RamadhanTheme.typography.labelSmall,
                        color = RamadhanTheme.colors.textPrimary
                    )
                }
            }
        }

        Image(
            modifier = Modifier
                .size(height = 100.dp, width = 120.dp)
                .align(Alignment.BottomEnd)
                .alpha(0.1f),
            painter = painterResource(Res.drawable.image_mosque),
            contentDescription = "Ilustrasi Masjid"
        )
    }
}

@Preview()
@Composable
private fun CardNextPrayerPreview() {
    RamadhanTheme() {
        CardNextPrayer(
            prayerName = "Maghrib",
            prayerTime = "18:05",
            remainingMinute = "15 Menit",
            date = "Selasa, 24 Sya'ban 1445 H",
            modifier = Modifier.fillMaxWidth().heightIn(196.dp)
        )
    }
}
