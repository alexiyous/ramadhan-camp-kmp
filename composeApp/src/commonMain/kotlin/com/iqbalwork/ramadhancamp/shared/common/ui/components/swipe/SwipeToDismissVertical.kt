package com.iqbalwork.ramadhancamp.shared.common.ui.components.swipe

import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.abs

@Composable
fun SwipeToDismissVertical(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit,
    enabled: Boolean = true,
    swipeThreshold: Dp = 40.dp,
    content: @Composable BoxScope.() -> Unit
) {
    val thresholdPx = with(LocalDensity.current) { swipeThreshold.toPx() }
    Box(
        modifier = modifier
            .pointerInput(enabled) {
                if (!enabled) return@pointerInput
                var totalDrag = 0f
                detectVerticalDragGestures(
                    onDragStart = {
                        totalDrag = 0f
                    },
                    onVerticalDrag = { change, dragAmount ->
                        change.consume()
                        totalDrag += abs(dragAmount)
                    },
                    onDragEnd = {
                        if (totalDrag > thresholdPx) {
                            onDismiss.invoke()
                        }
                    },
                )
            },
        content = content
    )
}