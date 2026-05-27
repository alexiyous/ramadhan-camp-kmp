package com.iqbalwork.ramadhancamp.shared.common.ui.components.button

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement.Absolute.spacedBy
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.iqbalwork.ramadhancamp.shared.common.extension.debounced
import com.iqbalwork.ramadhancamp.shared.common.ui.theme.RamadhanTheme
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@Composable
fun RamadhanButton(
    modifier: Modifier = Modifier,
    variant: RamadhanButtonProps.Variant,
    text: String? = null,
    size: RamadhanButtonProps.Size = RamadhanButtonProps.Size.Large,
    hasContentPadding: Boolean = true,
    enabled: Boolean = true,
    isProgress: Boolean = false,
    icon: DrawableResource? = null,
    iconAlignment: RamadhanButtonProps.IconAlignment = RamadhanButtonProps.IconAlignment.End,
    onClick: () -> Unit,
) {
    Button(
        modifier = modifier.height(size.height),
        enabled = enabled,
        shape = size.shape,
        border = if (variant == RamadhanButtonProps.Variant.Outline) BorderStroke(1.dp, RamadhanTheme.colors.divider) else null,
        onClick = debounced {
            if (!isProgress) { onClick.invoke() }
        },
        colors = buttonColors(variant),
        contentPadding = if (hasContentPadding) {
            val vertical = if (iconAlignment ==RamadhanButtonProps.IconAlignment.Top) {
                5.dp
            } else {
                8.dp
            }
            PaddingValues(horizontal = 16.dp, vertical = vertical)
        } else {
            PaddingValues()
        }
    ) {
        AnimatedContent(isProgress, label = "progress indicator") { isProgress ->
            when (isProgress) {
                true -> CircularProgressIndicator(
                    color = loaderColor(variant),
                    strokeWidth = 2.dp,
                    modifier = Modifier.size(20.dp)
                )
                false -> {
                    when(iconAlignment) {
                        RamadhanButtonProps.IconAlignment.End -> {
                            Row(
                                horizontalArrangement = spacedBy(8.dp),
                            ) {
                                text?.let {
                                    Text(
                                        text = text,
                                        textAlign = TextAlign.Center,
                                        style = RamadhanTheme.typography.headlineSmall
                                    )
                                }

                                icon?.let {
                                    Icon(
                                        modifier = Modifier
                                            .size(20.dp),
                                        painter = painterResource(icon),
                                        contentDescription = null,
                                    )
                                }
                            }
                        }
                        RamadhanButtonProps.IconAlignment.Start -> {
                            Row(
                                horizontalArrangement = spacedBy(8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                icon?.let {
                                    Icon(
                                        modifier = Modifier
                                            .size(20.dp),
                                        painter = painterResource(icon),
                                        contentDescription = null,
                                    )
                                }

                                text?.let {
                                    Text(
                                        text = text,
                                        textAlign = TextAlign.Center,
                                        style = RamadhanTheme.typography.headlineSmall
                                    )
                                }
                            }
                        }
                        RamadhanButtonProps.IconAlignment.Top -> {
                            Column(
                                verticalArrangement = spacedBy(4.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                icon?.let {
                                    Icon(
                                        modifier = Modifier.size(16.dp),
                                        painter = painterResource(icon),
                                        contentDescription = null,
                                    )
                                }
                                text?.let {
                                    Text(
                                        text = text,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis,
                                        textAlign = TextAlign.Center,
                                        style = RamadhanTheme.typography.bodyLarge
                                    )
                                }

                            }
                        }
                    }
                }
            }
        }
    }
}


@Composable
private fun buttonColors(variant: RamadhanButtonProps.Variant): ButtonColors {
    return when (variant) {
        RamadhanButtonProps.Variant.Primary -> ButtonColors(
            containerColor = RamadhanTheme.colors.accentPrimary,
            contentColor = RamadhanTheme.colors.textPrimary,
            disabledContentColor = RamadhanTheme.colors.textMuted,
            disabledContainerColor = RamadhanTheme.colors.bgSurface,
        )
        RamadhanButtonProps.Variant.Secondary -> ButtonColors(
            containerColor = RamadhanTheme.colors.bgContent,
            contentColor = RamadhanTheme.colors.accentPrimary,
            disabledContentColor = RamadhanTheme.colors.textMuted,
            disabledContainerColor = RamadhanTheme.colors.bgContentSecondary,
        )
        RamadhanButtonProps.Variant.Tertiary -> ButtonColors(
            containerColor = RamadhanTheme.colors.bgAccentLight,
            contentColor = RamadhanTheme.colors.accentPrimary,
            disabledContentColor = RamadhanTheme.colors.textMuted,
            disabledContainerColor = RamadhanTheme.colors.bgContentSecondary,
        )
        RamadhanButtonProps.Variant.Danger -> ButtonColors(
            containerColor = RamadhanTheme.colors.colorDanger.copy(alpha = 0.12f),
            contentColor = RamadhanTheme.colors.colorDanger,
            disabledContentColor = RamadhanTheme.colors.textMuted,
            disabledContainerColor = RamadhanTheme.colors.bgContentSecondary,
        )
        RamadhanButtonProps.Variant.Ghost -> ButtonColors(
            containerColor = Color.Transparent,
            contentColor = RamadhanTheme.colors.textMuted,
            disabledContentColor = RamadhanTheme.colors.textMuted.copy(alpha = 0.5f),
            disabledContainerColor = Color.Transparent,
        )
        RamadhanButtonProps.Variant.Outline -> ButtonColors(
            containerColor = Color.Transparent,
            contentColor = RamadhanTheme.colors.textPrimary,
            disabledContentColor = RamadhanTheme.colors.textMuted,
            disabledContainerColor = Color.Transparent,
        )
    }
}

@Composable
private fun loaderColor(variant: RamadhanButtonProps.Variant): Color {
    return when (variant) {
        RamadhanButtonProps.Variant.Primary -> RamadhanTheme.colors.textPrimary
        RamadhanButtonProps.Variant.Secondary -> RamadhanTheme.colors.accentPrimary
        RamadhanButtonProps.Variant.Tertiary -> RamadhanTheme.colors.accentPrimary
        RamadhanButtonProps.Variant.Danger -> RamadhanTheme.colors.colorDanger
        RamadhanButtonProps.Variant.Ghost -> RamadhanTheme.colors.textMuted
        RamadhanButtonProps.Variant.Outline -> RamadhanTheme.colors.textPrimary
    }
}

object RamadhanButtonProps {

    private const val BUTTON_HEIGHT = 52
    private const val BUTTON_HEIGHT_MIDDLE = 44
    private const val BUTTON_HEIGHT_MIN = 38

    enum class Variant {
        Primary,
        Secondary,
        Tertiary,
        Danger,
        Ghost,
        Outline
    }

    enum class Size(
        val height: Dp,
        val shape: Shape
    ) {
        Small(
            height = BUTTON_HEIGHT_MIN.dp,
            shape = RoundedCornerShape(12.dp)
        ),
        Middle(
            height = BUTTON_HEIGHT_MIDDLE.dp,
            shape = RoundedCornerShape(16.dp)
        ),
        Large(
            height = BUTTON_HEIGHT.dp,
            shape = RoundedCornerShape(20.dp)
        )
    }

    enum class IconAlignment {
        End, Top, Start
    }
}