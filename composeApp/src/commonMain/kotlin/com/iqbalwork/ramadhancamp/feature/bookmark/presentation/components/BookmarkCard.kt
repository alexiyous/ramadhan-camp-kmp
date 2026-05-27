package com.iqbalwork.ramadhancamp.feature.bookmark.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.iqbalwork.ramadhancamp.feature.bookmark.domain.model.Bookmark
import com.iqbalwork.ramadhancamp.shared.common.ui.theme.RamadhanTheme

@Composable
fun BookmarkCard(
    bookmark: Bookmark,
    categoryName: String,
    categoryColor: Long,
    onClick: () -> Unit,
    onBookmarkClick: () -> Unit = {}
) {
    val colors = RamadhanTheme.colors
    val typography = RamadhanTheme.typography

    val accentColor = Color(categoryColor)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .clip(RoundedCornerShape(12.dp))
            .background(colors.bgSecondary)
            .clickable(onClick = onClick)
    ) {
        // Left accent line — matches category color
        VerticalDivider(
            modifier = Modifier.fillMaxHeight(),
            thickness = 4.dp,
            color = accentColor
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 16.dp, top = 16.dp, bottom = 16.dp)
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                // Top row: Category badge + Timestamp + Bookmark icon
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Category badge (pill shape, low opacity background)
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .background(accentColor.copy(alpha = 0.1f))
                                .widthIn(max = 150.dp)
                                .padding(horizontal = 10.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = categoryName.uppercase(),
                                style = typography.labelSmall,
                                color = accentColor,
                                maxLines = 1,
                                modifier = Modifier.basicMarquee()
                            )
                        }

                        // Timestamp
                        Text(
                            text = "Baru ditambahkan",
                            style = typography.labelSmall,
                            color = colors.textMuted
                        )
                    }

                    // Bookmark icon (tappable to delete)
                    IconButton(
                        onClick = onBookmarkClick,
                        modifier = Modifier.size(24.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Bookmark,
                            contentDescription = "Hapus bookmark",
                            tint = RamadhanTheme.colors.accentPrimary,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Arabic text (right-aligned, large)
                Text(
                    text = bookmark.teksArab,
                    style = typography.quranAyahMedium,
                    color = colors.textPrimary,
                    textAlign = TextAlign.End,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Translation (left-aligned, grey)
                Text(
                    text = bookmark.teksIndonesia,
                    style = typography.translationText,
                    color = colors.textMuted,
                    textAlign = TextAlign.Start,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Bottom row: Surah number + Surah name
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    // Surah number inside a circle
                    Box(
                        modifier = Modifier
                            .size(28.dp)
                            .clip(CircleShape)
                            .background(colors.bgSurface),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = bookmark.surahId.toString(),
                            style = typography.labelSmall,
                            color = colors.textPrimary,
                            textAlign = TextAlign.Center
                        )
                    }

                    Column {
                        // Surah name
                        Text(
                            text = bookmark.surahName,
                            style = typography.labelLarge,
                            color = colors.textPrimary
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun BookmarkCardPreview() {
    RamadhanTheme {
        Box(modifier = Modifier.padding(16.dp)) {
            BookmarkCard(
                bookmark = Bookmark(
                    id = 1L,
                    ayahDetails = "2:255",
                    categoryId = 1L,
                    timestamp = 0L,
                    surahId = 2,
                    ayatNumber = 255,
                    surahName = "Al-Baqarah",
                    audioUrl = "",
                    teksArab = "اللَّهُ لَا إِلَٰهَ إِلَّا هُوَ الْحَيُّ الْقَيُّومُ",
                    teksIndonesia = "Allah, tidak ada tuhan selain Dia. Yang Maha Hidup, yang terus menerus mengurus (makhluk-Nya)."
                ),
                categoryName = "Doa",
                categoryColor = 0xFF4ADE80L,
                onClick = {},
                onBookmarkClick = {}
            )
        }
    }
}