package com.iqbalwork.ramadhancamp.feature.quran.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.iqbalwork.ramadhancamp.feature.quran.presentation.model.QuranSheetEffect
import com.iqbalwork.ramadhancamp.feature.quran.presentation.model.QuranSheetEvent
import com.iqbalwork.ramadhancamp.feature.quran.presentation.model.QuranSheetState
import com.iqbalwork.ramadhancamp.shared.common.extension.rememberViewModel
import com.iqbalwork.ramadhancamp.shared.common.ui.rememberDispatch
import com.iqbalwork.ramadhancamp.shared.common.ui.theme.RamadhanTheme
import org.koin.core.parameter.parametersOf

@Composable
fun QuranSheetScreen(params: QuranSheetScreenParameters) {
    val viewModel: QuranSheetViewModel = rememberViewModel { parametersOf(params) }
    val state by viewModel.state.collectAsStateWithLifecycle()
    val action = viewModel.rememberDispatch()

    // Handle effects (e.g., copy to clipboard)
    LaunchedEffect(Unit) {
        viewModel.effects.collect { effect ->
            when (effect) {
                is QuranSheetEffect.CopyToClipboard -> {
                    // Clipboard handling will be implemented in a follow-up
                }
            }
        }
    }

    QuranSheetContent(
        state = state,
        action = action,
        params = params
    )
}

@Composable
private fun QuranSheetContent(
    state: QuranSheetState,
    action: (QuranSheetEvent) -> Unit,
    params: QuranSheetScreenParameters
) {
    val colors = RamadhanTheme.colors
    val typography = RamadhanTheme.typography

    // Show snackbar on bookmark success
    val snackbarHostState = remember { SnackbarHostState() }
    LaunchedEffect(state.bookmarkMessage) {
        state.bookmarkMessage?.let {
            snackbarHostState.showSnackbar(it)
            action(QuranSheetEvent.BookmarkSuccessHandled)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Title
        Text(
            text = "Pilihan Ayat",
            style = typography.headlineSmall,
            color = colors.textPrimary,
            modifier = Modifier.padding(bottom = 4.dp)
        )

        // Surah context subtitle
        Text(
            text = "QS ${params.surahName}: ${params.ayatNumber}",
            style = typography.labelSmall,
            color = colors.textMuted,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        // Action buttons row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            ActionItem(
                icon = Icons.Default.PlayArrow,
                label = "Putar Audio",
                onClick = { action(QuranSheetEvent.PlayAudio) }
            )
            ActionItem(
                icon = Icons.Default.BookmarkBorder,
                label = "Simpan ke\nFavorit",
                onClick = { action(QuranSheetEvent.Bookmark) }
            )
            ActionItem(
                icon = Icons.Default.Share,
                label = "Bagikan Ayat",
                onClick = { action(QuranSheetEvent.Share) }
            )
            ActionItem(
                icon = Icons.Default.ContentCopy,
                label = "Salin Ayat",
                onClick = { action(QuranSheetEvent.Copy) }
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Cancel button
        OutlinedButton(
            onClick = { action(QuranSheetEvent.Dismiss) },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = colors.textPrimary
            ),
            border = androidx.compose.foundation.BorderStroke(1.dp, colors.divider)
        ) {
            Text(
                text = "Batal",
                style = typography.labelLarge,
                color = colors.textPrimary
            )
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
private fun ActionItem(
    icon: ImageVector,
    label: String,
    onClick: () -> Unit
) {
    val colors = RamadhanTheme.colors
    val typography = RamadhanTheme.typography

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .width(80.dp)
            .clip(RoundedCornerShape(8.dp))
            .clickable(onClick = onClick)
            .padding(4.dp)
    ) {
        Box(
            modifier = Modifier
                .size(56.dp)
                .background(colors.bgSecondary, RoundedCornerShape(16.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = colors.accentEmerald,
                modifier = Modifier.size(24.dp)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = label,
            style = typography.labelSmall,
            color = colors.textSecondary,
            textAlign = TextAlign.Center,
            minLines = 2
        )
    }
}
