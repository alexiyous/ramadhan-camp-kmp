package com.iqbalwork.ramadhancamp.feature.bookmark.presentation.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.iqbalwork.ramadhancamp.shared.common.ui.theme.RamadhanTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CategoryChip(
    name: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    onLongClick: (() -> Unit)? = null,
    onDeleteClick: (() -> Unit)? = null,
    color: Long
) {
    val colors = RamadhanTheme.colors
    val typography = RamadhanTheme.typography
    val categoryColor = Color(color)

    val backgroundColor = if (isSelected) {
        categoryColor.copy(alpha = 0.2f)
    } else {
        categoryColor.copy(alpha = 0.08f)
    }

    val contentColor = if (isSelected) {
        categoryColor
    } else {
        categoryColor.copy(alpha = 0.7f)
    }

    val chip = @Composable {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .background(backgroundColor)
                .then(
                    if (isSelected) {
                        Modifier.border(1.5.dp, categoryColor.copy(alpha = 0.5f), RoundedCornerShape(16.dp))
                    } else {
                        Modifier
                    }
                )
                .widthIn(max = 140.dp)
                .combinedClickable(
                    onClick = onClick,
                    onLongClick = onLongClick
                )
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Text(
                text = name,
                style = typography.labelLarge,
                color = contentColor,
                maxLines = 1,
                modifier = Modifier.basicMarquee()
            )
        }
    }

    if (onDeleteClick != null) {
        Box {
            chip()
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .offset(x = 4.dp, y = (-4).dp)
                    .size(20.dp)
                    .clip(CircleShape)
                    .background(colors.bgPrimary)
                    .clickable(onClick = onDeleteClick),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.Close,
                    contentDescription = "Delete category",
                    tint = categoryColor,
                    modifier = Modifier.size(12.dp)
                )
            }
        }
    } else {
        chip()
    }
}