package com.iqbalwork.ramadhancamp.feature.home.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.iqbalwork.ramadhancamp.shared.common.ui.components.card.RamadhanCardItem
import com.iqbalwork.ramadhancamp.shared.common.ui.theme.RamadhanTheme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import ramadhancamp.composeapp.generated.resources.Res
import ramadhancamp.composeapp.generated.resources.ayat_number
import ramadhancamp.composeapp.generated.resources.ic_open_book

@Composable
fun LastSurahCard(
    modifier: Modifier = Modifier,
    surahName: String ,
    ayatNumber: Int,
    lastReadDate: String,
    onClick: () -> Unit
) {
    val colors = RamadhanTheme.colors

    RamadhanCardItem(
        modifier = modifier,
        leftComponent = {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(color = colors.bgSurface),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    modifier = Modifier.sizeIn(22.dp),
                    painter = painterResource(Res.drawable.ic_open_book),
                    contentDescription = "Last Read",
                    tint = colors.textPrimary,
                )
            }
        },
        rightComponent = {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                contentDescription = null,
                tint = colors.accentPrimary,
                modifier = Modifier.size(12.dp)
            )
        },
        title = surahName,
        subtitle = stringResource(Res.string.ayat_number, ayatNumber) + " • " + lastReadDate,
        onClick = onClick,
        containerColor = colors.bgSecondary
    )
}

@Preview
@Composable
private fun LastSurahCardPreview() {
    RamadhanTheme {
        LastSurahCard(
            surahName = "Al-Baqarah",
            ayatNumber = 155,
            lastReadDate = "24 Sya'ban 1445 H",
            onClick = {}
        )
    }
}
