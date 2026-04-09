package com.iqbalwork.ramadhancamp.shared.common.ui.components.searchbox

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement.Absolute.spacedBy
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.iqbalwork.ramadhancamp.shared.common.extension.debouncedClickable
import com.iqbalwork.ramadhancamp.shared.common.extension.modifyIf
import com.iqbalwork.ramadhancamp.shared.common.ui.theme.RamadhanTheme


@Composable
fun RamadhanSearchBox(
    modifier: Modifier = Modifier,
    placeholder: String,
    onClick: () -> Unit,
) {
    RamadhanSearchBoxImpl(
        modifier = modifier,
        state = null,
        placeholder = placeholder,
        onClick = onClick,
        onSearchClick = null
    )
}

@Composable
fun RamadhanSearchBox(
    modifier: Modifier = Modifier,
    state: TextFieldState,
    placeholder: String,
    onSearchClick: () -> Unit,
) {
    RamadhanSearchBoxImpl(
        modifier = modifier,
        state = state,
        placeholder = placeholder,
        onClick = null,
        onSearchClick = onSearchClick
    )
}

@Composable
private fun RamadhanSearchBoxImpl(
    modifier: Modifier = Modifier,
    state: TextFieldState?,
    placeholder: String,
    onClick: (() -> Unit)?,
    onSearchClick: (() -> Unit)?,
) {
    val colors = RamadhanTheme.colors
    val isButton = onClick != null
    val typography = RamadhanTheme.typography

    val placeholderTextComponent = @Composable {
        Text(
            text = placeholder,
            style = typography.bodyMedium,
            color = colors.textMuted,
        )
    }

    Box(
        modifier = modifier
            .heightIn(48.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(colors.bgSecondary)
            .modifyIf(isButton) {
                debouncedClickable {
                    onClick?.invoke()
                }
            }
    ) {
        Row(
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalArrangement = spacedBy(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(imageVector = Icons.Outlined.Search, contentDescription = null, tint = colors.textMuted)

            if (state != null) {
                BasicTextField(
                    textStyle = typography.bodyMedium.copy(
                        color = colors.textPrimary,
                    ),
                    state = state,
                    lineLimits = TextFieldLineLimits.SingleLine,
                    decorator = { innerTextField ->
                        if (state.text.isEmpty()) {
                           placeholderTextComponent()
                        }
                        innerTextField()
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Search
                    ),
                    onKeyboardAction = { onSearchClick?.invoke() }
                )
            } else {
                placeholderTextComponent()
            }
        }
    }
}

@Preview
@Composable
private fun RamadhanSearchBoxPreview() {
    RamadhanTheme {
        Box(modifier = Modifier.background(RamadhanTheme.colors.bgPrimary).padding(16.dp)) {
            RamadhanSearchBox(
                placeholder = "Cari ayat...",
                onClick = {}
            )
        }
    }
}

@Preview
@Composable
private fun AyatSearchBoxWithStatePreview() {
    RamadhanTheme {
        Box(modifier = Modifier.background(RamadhanTheme.colors.bgPrimary).padding(16.dp)) {
            RamadhanSearchBox(
                state = TextFieldState(""),
                placeholder = "Cari ayat...",
                onSearchClick = {}
            )
        }
    }
}
