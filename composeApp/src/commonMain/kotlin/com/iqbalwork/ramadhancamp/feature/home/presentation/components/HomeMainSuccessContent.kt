package com.iqbalwork.ramadhancamp.feature.home.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.iqbalwork.ramadhancamp.feature.home.presentation.model.HomeEvent
import com.iqbalwork.ramadhancamp.feature.home.presentation.model.HomeScreenUiModel
import com.iqbalwork.ramadhancamp.feature.home.presentation.model.NextPrayerUiModel
import com.iqbalwork.ramadhancamp.shared.common.ui.theme.RamadhanTheme
import org.jetbrains.compose.resources.stringResource
import ramadhancamp.composeapp.generated.resources.Res
import ramadhancamp.composeapp.generated.resources.home_main_header_greeting

@Composable
fun HomeMainSuccessContent(
    homeMainData: HomeScreenUiModel,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState()),
    ) {
        HomeMainHeader(
            modifier = Modifier.statusBarsPadding().fillMaxWidth().padding(bottom = 24.dp),
            greetingText = stringResource(Res.string.home_main_header_greeting),
            city = homeMainData.city,
            country = homeMainData.country,
        )
        
        CardNextPrayer(
            modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp),
            prayerName = homeMainData.nextPrayerData.nextPrayerName,
            prayerTime = homeMainData.nextPrayerData.nextPrayerTime,
            date = homeMainData.currentDate,
            remainingMinute = homeMainData.nextPrayerData.remainingMinutesToNextPrayer.toString()
        )
    }
}

@Preview
@Composable
private fun HomeMainSuccessContentPreview() {
    RamadhanTheme {
        HomeMainSuccessContent(
            homeMainData = HomeScreenUiModel(
                city = "Jakarta",
                country = "Indonesia",
                currentDate = "Selasa, 24 Sya'ban 1445 H",
                nextPrayerData = NextPrayerUiModel(
                    nextPrayerName = "Maghrib",
                    nextPrayerTime = "18:05",
                    remainingMinutesToNextPrayer = 15
                )
            ),
            modifier = Modifier.fillMaxSize().padding(16.dp)
        )
    }
}