package com.iqbalwork.ramadhancamp.feature.home.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement.Absolute.spacedBy
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.iqbalwork.ramadhancamp.shared.common.ui.theme.RamadhanTheme
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

private const val TRIPLE_TAP_COUNT = 3
private const val TAP_TIMEOUT_MS = 2000L

@OptIn(ExperimentalTime::class)
@Composable
fun HomeMainHeader(
    modifier: Modifier = Modifier,
    greetingText: String,
    city: String,
    country: String,
    onAboutClick: () -> Unit = {},
) {
    val tapCount = remember { mutableIntStateOf(0) }
    val lastTapTime = remember { mutableLongStateOf(0L) }

    Column(
        modifier = modifier,
        verticalArrangement = spacedBy(4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 28.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = greetingText,
                style = RamadhanTheme.typography.headlineLarge,
                color = RamadhanTheme.colors.textPrimary,
                modifier = Modifier
                    .weight(1f)
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    ) {
                        val now = Clock.System.now().toEpochMilliseconds()
                        if (now - lastTapTime.value > TAP_TIMEOUT_MS) {
                            tapCount.value = 1
                        } else {
                            tapCount.value++
                            if (tapCount.value >= TRIPLE_TAP_COUNT) {
                                tapCount.value = 0
                                onAboutClick()
                            }
                        }
                        lastTapTime.value = now
                    }
            )
        }

        Row(
            modifier = Modifier
                .heightIn(20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = spacedBy(8.dp)
        ) {
            Icon(
                imageVector = Icons.Outlined.LocationOn,
                contentDescription = null,
                tint = RamadhanTheme.colors.bgSurface
            )
            Text(
                text = "$city, $country",
                style = RamadhanTheme.typography.bodyMedium,
                color = RamadhanTheme.colors.textPrimary
            )
        }
    }
}

@Preview()
@Composable
private fun HomeMainHeaderPreview() {
    RamadhanTheme {
        HomeMainHeader(
            greetingText = "Assalamu'alaikum, Iqbal",
            city = "Jakarta Selatan",
            country = "Indonesia",
            modifier = Modifier.fillMaxWidth().background(RamadhanTheme.colors.bgPrimary)
        )
    }
}
