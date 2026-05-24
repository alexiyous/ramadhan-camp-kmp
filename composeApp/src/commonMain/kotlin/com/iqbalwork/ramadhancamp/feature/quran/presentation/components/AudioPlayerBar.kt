package com.iqbalwork.ramadhancamp.feature.quran.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.SkipPrevious
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.iqbalwork.ramadhancamp.shared.common.ui.theme.RamadhanTheme
import kotlinx.coroutines.delay
import kotlin.math.abs

@Composable
fun AudioPlayerBar(
    surahName: String,
    ayatNumber: Int,
    reciterName: String,
    currentTimeMs: Long,
    totalDurationMs: Long,
    isPlaying: Boolean,
    isBuffering: Boolean = false,
    onSeek: (Long) -> Unit,
    onPlayPause: () -> Unit,
    onNext: (() -> Unit)? = null,
    onPrev: (() -> Unit)? = null,
    onClose: () -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = RamadhanTheme.colors
    val typography = RamadhanTheme.typography

    var smoothProgressMs by remember { mutableFloatStateOf(currentTimeMs.toFloat()) }
    var isDraggingSlider by remember { mutableStateOf(false) }
    var localSliderProgress by remember { mutableFloatStateOf(0f) }

    // Sync smooth progress when currentTimeMs from ViewModel updates significantly
    LaunchedEffect(currentTimeMs) {
        if (!isDraggingSlider && abs(smoothProgressMs - currentTimeMs) > 1500f) {
            smoothProgressMs = currentTimeMs.toFloat()
        }
    }

    // 60fps ticker
    LaunchedEffect(isPlaying, isBuffering) {
        if (!isPlaying || isBuffering) return@LaunchedEffect
        val startWallClock = kotlinx.datetime.Clock.System.now().toEpochMilliseconds()
        val startProgress = smoothProgressMs
        while (true) {
            kotlinx.coroutines.delay(16L)
            val elapsed = kotlinx.datetime.Clock.System.now().toEpochMilliseconds() - startWallClock
            val rawProgress = startProgress + elapsed
            smoothProgressMs = if (totalDurationMs > 0L) rawProgress.coerceAtMost(totalDurationMs.toFloat()) else rawProgress
        }
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(colors.bgSecondary)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = when {
                        isBuffering -> "Buffering..."
                        !isPlaying -> "Paused"
                        else -> "Now Playing"
                    },
                    style = typography.labelSmall,
                    color = if (isPlaying && !isBuffering) colors.accentGold else colors.textMuted
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "$surahName - Ayat $ayatNumber",
                    style = typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                    color = colors.textPrimary,
                    maxLines = 1
                )
                Text(
                    text = reciterName,
                    style = typography.labelSmall,
                    color = colors.textMuted,
                    maxLines = 1
                )
            }

            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Close",
                tint = colors.textMuted,
                modifier = Modifier
                    .size(24.dp)
                    .clickable { onClose() }
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                if (onPrev != null) {
                    Icon(
                        imageVector = Icons.Default.SkipPrevious,
                        contentDescription = "Previous",
                        tint = colors.textPrimary,
                        modifier = Modifier
                            .size(36.dp)
                            .clickable { onPrev() }
                    )
                }

                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(colors.accentPrimary)
                        .clickable { onPlayPause() },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                        contentDescription = if (isPlaying) "Pause" else "Play",
                        tint = colors.textOnLight,
                        modifier = Modifier.size(28.dp)
                    )
                }

                if (onNext != null) {
                    Icon(
                        imageVector = Icons.Default.SkipNext,
                        contentDescription = "Next",
                        tint = colors.textPrimary,
                        modifier = Modifier
                            .size(36.dp)
                            .clickable { onNext() }
                    )
                }
            }
        }

        val displayProgressMs = if (isDraggingSlider) (localSliderProgress * totalDurationMs).toLong() else smoothProgressMs.toLong()

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = formatDuration(displayProgressMs),
                style = typography.labelSmall,
                color = colors.textMuted
            )
            Text(
                text = if (totalDurationMs > 0L) formatDuration(totalDurationMs) else "--:--",
                style = typography.labelSmall,
                color = colors.textMuted
            )
        }

        val sliderProgress = if (isDraggingSlider) localSliderProgress else if (totalDurationMs > 0L) smoothProgressMs / totalDurationMs.toFloat() else 0f

        if (totalDurationMs > 0L) {
            Slider(
                value = sliderProgress,
                onValueChange = { newValue ->
                    isDraggingSlider = true
                    localSliderProgress = newValue
                },
                onValueChangeFinished = {
                    isDraggingSlider = false
                    val seekMs = (localSliderProgress * totalDurationMs).toLong()
                    smoothProgressMs = seekMs.toFloat()
                    onSeek(seekMs)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp),
                colors = SliderDefaults.colors(
                    thumbColor = colors.accentGold,
                    activeTrackColor = colors.accentGold,
                    inactiveTrackColor = colors.divider
                )
            )
        } else {
            LinearProgressIndicator(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp),
                color = colors.accentGold,
                trackColor = colors.divider
            )
        }
    }
}

private fun formatDuration(millis: Long): String {
    val totalSeconds = millis / 1000
    val minutes = totalSeconds / 60
    val seconds = totalSeconds % 60
    return "$minutes:${seconds.toString().padStart(2, '0')}"
}
