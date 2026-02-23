package com.iqbalwork.ramadhancamp.shared.common.ui.utils

import androidx.compose.runtime.Composable
import org.jetbrains.compose.resources.PluralStringResource
import org.jetbrains.compose.resources.pluralStringResource
import org.jetbrains.compose.resources.stringResource
import kotlin.jvm.Transient

sealed interface TextResource {
    data class PlainText(val text: String) : TextResource

    data class StringResource(
        @Transient
        val resId: org.jetbrains.compose.resources.StringResource,
        @Transient
        val formatArgs: List<Any> = emptyList(),
    ) : TextResource {
        constructor(
            resId: org.jetbrains.compose.resources.StringResource,
            vararg formatArgs: Any,
        ) : this (resId = resId, formatArgs = formatArgs.toList())
    }

    data class PluralResource(
        @Transient
        val resId: PluralStringResource,
        @Transient
        val quantity: Int,
        @Transient
        val formatArgs: List<Any> = emptyList(),
    ) : TextResource {
        constructor(
            resId: PluralStringResource,
            quantity: Int,
            vararg formatArgs: Any,
        ) : this (
            resId = resId,
            quantity = quantity,
            formatArgs = formatArgs.toList(),
        )
    }
}

@Composable
fun TextResource.asString(): String {
    return when (this) {
        is TextResource.PlainText -> text
        is TextResource.PluralResource -> {
            if (formatArgs.isNotEmpty()) {
                pluralStringResource(resource = resId, quantity = quantity, *formatArgs.toTypedArray())
            } else {
                pluralStringResource(resource = resId, quantity = quantity)
            }
        }
        is TextResource.StringResource -> {
            if (formatArgs.isNotEmpty()) {
                stringResource(resource = resId, formatArgs = formatArgs.toTypedArray())
            } else {
                stringResource(resource = resId)
            }
        }
    }
}