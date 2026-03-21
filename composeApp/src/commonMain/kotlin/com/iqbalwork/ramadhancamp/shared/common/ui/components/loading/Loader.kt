package com.iqbalwork.ramadhancamp.shared.common.ui.components.loading

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.iqbalwork.ramadhancamp.shared.common.extension.modifyIf
import com.iqbalwork.ramadhancamp.shared.common.ui.theme.RamadhanTheme

@Composable
fun Loader(
    modifier: Modifier = Modifier,
    isSemiTransparent: Boolean = false,
    color: Color = RamadhanTheme.colors.accentPrimary,
) {
    Box(
        modifier = modifier
            .modifyIf(isSemiTransparent) {
                background(RamadhanTheme.colors.accentEmerald)
            }
            .pointerInput(Unit) {
                detectDragGestures { _, _ -> }
                detectTapGestures { }
            },
    ) {
        CircularProgressIndicator(
            modifier = Modifier
                .size(24.dp)
                .align(Alignment.Center),
            color = color,
            strokeWidth = 2.dp,
        )
    }
}