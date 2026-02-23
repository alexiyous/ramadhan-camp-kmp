package com.iqbalwork.ramadhancamp.shared.common.ui.components.snackbar

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.iqbalwork.ramadhancamp.shared.common.ui.components.swipe.SwipeToDismissVertical
import com.iqbalwork.ramadhancamp.shared.common.ui.utils.TextResource
import com.iqbalwork.ramadhancamp.shared.common.ui.utils.asString
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

object RamadhanSnackBarProps {
    enum class Duration(val time: Long) {
        Long(10_000), Short(4_000)
    }

    enum class Position { Top, Bottom }
}

data class SnackBarData(
    val message: TextResource,
    val icon: DrawableResource? = null,
    val durationMillis: RamadhanSnackBarProps.Duration = RamadhanSnackBarProps.Duration.Short,
    val position: RamadhanSnackBarProps.Position = RamadhanSnackBarProps.Position.Top,
)

@Composable
fun RamadhanSnackBarHost(
    modifier: Modifier,
    snackbarFlow: Flow<SnackBarData>,
    content: @Composable ColumnScope.() -> Unit
) {
    var currentSnackbar by remember { mutableStateOf<SnackBarData?>(null) }
    var isVisible by remember { mutableStateOf(false) }

    LaunchedEffect(snackbarFlow) {
        snackbarFlow.collectLatest { data ->
            currentSnackbar = data
            isVisible = true
            delay(data.durationMillis.time)
            isVisible = false
        }
    }

    Box(
        modifier = modifier
    ) {
        Column(
            content = content
        )
        Box(
            modifier = Modifier.align(Alignment.TopCenter),
            contentAlignment = when (currentSnackbar?.position) {
                RamadhanSnackBarProps.Position.Top -> Alignment.TopCenter
                else -> Alignment.BottomCenter
            }
        ) {
            AnimatedVisibility(
                visible = isVisible,
                enter = slideInVertically(
                    initialOffsetY = { if (currentSnackbar?.position == RamadhanSnackBarProps.Position.Top) -it else it }
                ) + fadeIn(),
                exit = slideOutVertically(
                    targetOffsetY = { if (currentSnackbar?.position == RamadhanSnackBarProps.Position.Top) -it else it }
                ) + fadeOut()
            ) {
                currentSnackbar?.let { data ->
                    SwipeToDismissVertical(
                        onDismiss = {
                            isVisible = false
                        }
                    ) {
                        SnackbarContainer(
                            modifier = Modifier
                                .padding(horizontal = 12.dp)
                                .fillMaxWidth(),
                            data = data
                        )
                    }

                }
            }
        }
    }

}

@Composable
private fun SnackbarContainer(
    modifier: Modifier = Modifier,
    data: SnackBarData,
) {
    Row(
        modifier = modifier
            .background(
                color = Color.Gray,
                shape = RoundedCornerShape(20.dp)
            )
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        data.icon?.let {
            Icon(
                modifier = Modifier.size(24.dp),
                painter = painterResource(data.icon),
                contentDescription = null,
                tint = Color.Unspecified
            )
        }
        Text(
            text = data.message.asString(),
            maxLines = 3,
            overflow = TextOverflow.Ellipsis
        )
    }
}

