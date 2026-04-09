package com.iqbalwork.ramadhancamp.feature.home.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement.Absolute.spacedBy
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import com.iqbalwork.ramadhancamp.feature.home.presentation.model.SurahUiModel
import com.iqbalwork.ramadhancamp.shared.common.ui.components.card.RamadhanCardItem
import com.iqbalwork.ramadhancamp.shared.common.ui.theme.RamadhanTheme
import org.jetbrains.compose.resources.stringResource
import ramadhancamp.composeapp.generated.resources.Res
import ramadhancamp.composeapp.generated.resources.total_ayahs

@Composable
fun PopularSurahContainer(
    modifier: Modifier = Modifier,
    surahList: List<SurahUiModel>,
    onClick: (Int) -> Unit
) {
    val typography = RamadhanTheme.typography
    val colors = RamadhanTheme.colors

    Column(
        modifier = modifier,
        verticalArrangement = spacedBy(12.dp)
    ) {
        surahList.forEach {
            RamadhanCardItem(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    onClick(it.number)
                },
                leftComponent = {
                    Text(
                        modifier = Modifier.widthIn(27.dp),
                        text = it.number.toString(),
                        textAlign = TextAlign.Center,
                        color = colors.textPrimary,
                        style = typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                    )
                },
                rightComponent = {
                    Text(
                        modifier = Modifier.widthIn(min = 24.dp, max = 60.dp),
                        text = it.arabicName,
                        textAlign = TextAlign.Center,
                        color = colors.textPrimary,
                        style = typography.labelLarge.copy(fontWeight = FontWeight.Bold),
                    )
                },
                title = it.latinName,
                subtitle = stringResource(Res.string.total_ayahs, it.numberOfAyahs) + " • " + it.descentPlace,
                containerColor = colors.bgSecondary.copy(alpha = 0.4f)
            )
        }
    }
}

@Preview
@Composable
private fun PopularSurahContainerPreview() {
    RamadhanTheme {
        Box(
            modifier = Modifier
                .background(RamadhanTheme.colors.bgPrimary)
                .padding(16.dp)
        ) {
        PopularSurahContainer(
            surahList = listOf(
                SurahUiModel(
                    number = 1,
                    arabicName = "الفاتحة",
                    latinName = "Al-Fatihah",
                    numberOfAyahs = 7,
                    descentPlace = "Meccan"
                ),
                SurahUiModel(
                    number = 18,
                    arabicName = "الكهف",
                    latinName = "Al-Kahf",
                    numberOfAyahs = 110,
                    descentPlace = "Meccan"
                ),
                SurahUiModel(
                    number = 36,
                    arabicName = "يس",
                    latinName = "Ya-Sin",
                    numberOfAyahs = 83,
                    descentPlace = "Meccan"
                )
            ),
            onClick = {}
        )
        }
    }
}
