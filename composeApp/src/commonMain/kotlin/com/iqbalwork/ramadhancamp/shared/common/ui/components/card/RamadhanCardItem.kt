package com.iqbalwork.ramadhancamp.shared.common.ui.components.card

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Arrangement.Absolute.spacedBy
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.iqbalwork.ramadhancamp.shared.common.extension.debouncedClickable
import com.iqbalwork.ramadhancamp.shared.common.ui.theme.RamadhanTheme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import ramadhancamp.composeapp.generated.resources.Res
import ramadhancamp.composeapp.generated.resources.ayat_number
import ramadhancamp.composeapp.generated.resources.ic_open_book

@Composable
fun RamadhanCardItem(
    modifier: Modifier = Modifier,
    containerColor: Color,
    onClick: () -> Unit,
    leftComponent: @Composable () -> Unit,
    rightComponent: @Composable () -> Unit,
    title: String,
    subtitle: String,
) {
    val colors = RamadhanTheme.colors
    val typography = RamadhanTheme.typography

    Box(
        modifier = modifier
            .height(82.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(color = containerColor)
            .debouncedClickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            horizontalArrangement = spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            leftComponent()

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = title,
                    style = typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                    color = colors.textPrimary,
                )

                Text(
                    text = subtitle,
                    style = typography.labelSmall,
                    color = colors.textMuted,
                )

            }

            rightComponent()
        }
    }
}