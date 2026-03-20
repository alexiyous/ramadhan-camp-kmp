package com.iqbalwork.ramadhancamp.shared.common.ui.components.error

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.iqbalwork.ramadhancamp.shared.common.ui.components.button.RamadhanButton
import com.iqbalwork.ramadhancamp.shared.common.ui.components.button.RamadhanButtonProps
import com.iqbalwork.ramadhancamp.shared.common.ui.theme.RamadhanTheme
import org.jetbrains.compose.resources.painterResource
import ramadhancamp.composeapp.generated.resources.Res

@Composable
fun EmptyContent(
    modifier: Modifier = Modifier,
    title: String,
    subtitle: String,
    image: Painter,
    buttonText: String? = null,
    isFullscreen: Boolean = true,
    buttonAction: (() -> Unit)? = null
) {
    EmptyContent(
        modifier = modifier,
        title = title,
        subtitle = subtitle,
        image = image,
        isFullscreen = isFullscreen,
        buttonSlot = {
            buttonText?.let {
                RamadhanButton (
                    variant = RamadhanButtonProps.Variant.Primary,
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = buttonText,
                    onClick = { buttonAction?.invoke() }
                )
            }
        }
    )
}

@Composable
fun EmptyContent(
    modifier: Modifier = Modifier,
    title: String,
    subtitle: String,
    image: Painter,
    isFullscreen: Boolean = true,
    buttonSlot: (@Composable () -> Unit)? = null
) {
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .fillMaxSize()
            .padding(horizontal = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.weight(1f))
        Image(
            modifier = Modifier.padding(horizontal = if (isFullscreen) 42.dp else 53.dp),
            painter = image,
            contentDescription = null
        )
        Text(
            modifier = Modifier
                .padding(top = 32.dp)
                .fillMaxWidth(),
            text = title,
            textAlign = TextAlign.Center,
            style = RamadhanTheme.typography.headlineLarge,
            color = RamadhanTheme.colors.textPrimary
        )
        Text(
            modifier = Modifier
                .padding(top = 6.dp)
                .fillMaxWidth(),
            text = subtitle,
            textAlign = TextAlign.Center,
            style = RamadhanTheme.typography.headlineSmall,
            color = RamadhanTheme.colors.textPrimary
        )
        Spacer(Modifier.weight(1f))
        if (buttonSlot != null) {
            buttonSlot.invoke()
            Spacer(Modifier.size(8.dp))
        }
    }
}