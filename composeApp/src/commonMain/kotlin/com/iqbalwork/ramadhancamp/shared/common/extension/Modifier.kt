package com.iqbalwork.ramadhancamp.shared.common.extension

import androidx.compose.foundation.Indication
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

const val CLICK_DEBOUNCE_TIME = 600L

@OptIn(ExperimentalTime::class)
@Composable
inline fun debounced(
    debounceTime: Long = CLICK_DEBOUNCE_TIME,
    crossinline onClick: () -> Unit,
): () -> Unit {
    var lastTimeClicked by remember { mutableLongStateOf(0L) }
    val onClickLambda = {
        val now = Clock.System.now().toEpochMilliseconds()
        if (now - lastTimeClicked > debounceTime) {
            onClick()
            lastTimeClicked = now
        }
    }
    return onClickLambda
}

@Composable
fun Modifier.modifyIf(condition: Boolean, block: @Composable Modifier.() -> Modifier): Modifier = if (condition) {
    block(this)
} else {
    this
}

@Composable
fun Modifier.debouncedClickable(
    enabled: Boolean = true,
    debounceTime: Long = CLICK_DEBOUNCE_TIME,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    indication: Indication? = LocalIndication.current,
    onClick: () -> Unit,
): Modifier =
    composed {
        val clickable = debounced(debounceTime = debounceTime, onClick = { onClick() })
        this.clickable(enabled = enabled, interactionSource = interactionSource, indication = indication) { clickable() }
    }