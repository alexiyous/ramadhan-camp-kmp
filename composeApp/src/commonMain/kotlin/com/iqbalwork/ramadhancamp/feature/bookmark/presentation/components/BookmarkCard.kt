package com.iqbalwork.ramadhancamp.feature.bookmark.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.iqbalwork.ramadhancamp.feature.bookmark.domain.model.Bookmark
import com.iqbalwork.ramadhancamp.shared.common.ui.theme.RamadhanTheme

@Composable
fun BookmarkCard(
    bookmark: Bookmark,
    categoryName: String,
    onClick: () -> Unit,
    onPlayClick: () -> Unit
) {
    val colors = RamadhanTheme.colors
    val typography = RamadhanTheme.typography

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .clip(RoundedCornerShape(12.dp))
            .background(colors.bgSecondary)
            .clickable(onClick = onClick)
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .width(4.dp)
                .background(colors.accentPrimary)
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = categoryName,
                    style = typography.labelSmall,
                    color = colors.textMuted
                )
                Text(
                    text = "B",
                    color = colors.accentPrimary,
                    style = typography.labelLarge
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = bookmark.ayahDetails,
                style = typography.headlineLarge,
                color = colors.textPrimary,
                textAlign = TextAlign.End,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Translation here...",
                style = typography.bodyLarge,
                color = colors.textSecondary,
                textAlign = TextAlign.Start,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Surah Info",
                    style = typography.labelLarge,
                    color = colors.textPrimary
                )
                
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .background(colors.accentPrimary.copy(alpha = 0.2f))
                        .clickable(onClick = onPlayClick)
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = "Play",
                        style = typography.labelLarge,
                        color = colors.accentPrimary
                    )
                }
            }
        }
    }
}
