package com.iqbalwork.ramadhancamp.feature.home.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.iqbalwork.ramadhancamp.feature.home.presentation.model.HomeScreenUiModel
import com.iqbalwork.ramadhancamp.shared.common.ui.components.searchbox.RamadhanSearchBox
import com.iqbalwork.ramadhancamp.shared.common.ui.theme.RamadhanTheme
import org.jetbrains.compose.resources.stringResource
import ramadhancamp.composeapp.generated.resources.Res
import ramadhancamp.composeapp.generated.resources.find_ayat
import ramadhancamp.composeapp.generated.resources.home_main_header_greeting
import ramadhancamp.composeapp.generated.resources.last_read
import ramadhancamp.composeapp.generated.resources.populer_surah

@Composable
fun HomeMainSuccessContent(
    homeMainData: HomeScreenUiModel,
    modifier: Modifier = Modifier,
    onSearchBoxClicked: () -> Unit,
    onLastSurahCardClick: () -> Unit,
    onPopularSurahClick: (Int) -> Unit,
    onAboutClick: () -> Unit = {},
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
            onAboutClick = onAboutClick,
        )
        
        CardNextPrayer(
            modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp),
            prayerName = homeMainData.nextPrayerData.nextPrayerName,
            prayerTime = homeMainData.nextPrayerData.nextPrayerTime,
            date = homeMainData.currentDate,
            remainingMinute = homeMainData.nextPrayerData.remainingMinutesToNextPrayer.toString()
        )

        RamadhanSearchBox(
            modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp),
            placeholder = stringResource(Res.string.find_ayat),
            onClick = onSearchBoxClicked
        )

        if (homeMainData.lastSurahReadData != null) {
            Text(
                text = stringResource(Res.string.last_read),
                style = RamadhanTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                color = RamadhanTheme.colors.textPrimary,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            LastSurahCard(
                modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp),
                surahName = homeMainData.lastSurahReadData.surahName,
                ayatNumber = homeMainData.lastSurahReadData.ayatNumber,
                lastReadDate = homeMainData.lastSurahReadData.readDate,
                onClick = onLastSurahCardClick
            )
        }

        Text(
            text = stringResource(Res.string.populer_surah),
            style = RamadhanTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
            color = RamadhanTheme.colors.textPrimary,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        PopularSurahContainer(
            modifier = Modifier.fillMaxWidth(),
            surahList = homeMainData.popularSurahList,
            onClick = onPopularSurahClick
        )
    }
}
