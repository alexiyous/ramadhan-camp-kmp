package com.iqbalwork.ramadhancamp.shared.common.ui.components.dialog

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.iqbalwork.ramadhancamp.shared.common.ui.components.button.RamadhanButton
import com.iqbalwork.ramadhancamp.shared.common.ui.components.button.RamadhanButtonProps
import com.iqbalwork.ramadhancamp.shared.common.ui.theme.RamadhanTheme

@Composable
fun RamadhanAlertDialog(
    text: String,
    title: String,
    confirmButtonText: String,
    dismissButtonText: String? = null,
    confirmButtonVariant: RamadhanButtonProps.Variant = RamadhanButtonProps.Variant.Tertiary,
    dismissButtonVariant: RamadhanButtonProps.Variant = RamadhanButtonProps.Variant.Tertiary,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    modifier: Modifier = Modifier,
) {
    AlertDialog(
        modifier = modifier,
        onDismissRequest = onDismiss,
        containerColor = RamadhanTheme.colors.bgSurface,
        textContentColor = RamadhanTheme.colors.textPrimary,
        titleContentColor = RamadhanTheme.colors.textPrimary,
        confirmButton = {
            RamadhanButton(
                variant = confirmButtonVariant,
                text = confirmButtonText,
                onClick = onConfirm
            )
        },
        dismissButton = {
            dismissButtonText?.let {
                RamadhanButton(
                    variant = dismissButtonVariant,
                    text = it,
                    onClick = onDismiss
                )
            }
        },
        title = {
            Text(
                text = title,
                style = RamadhanTheme.typography.headlineMedium,
                color = RamadhanTheme.colors.textPrimary
            )
        },
        text = {
            Text(
                text = text,
                style = RamadhanTheme.typography.titleMedium,
                color = RamadhanTheme.colors.textPrimary
            )
        }
    )
}

@Preview
@Composable
fun RamadhanAlertDialogPreview() {
    RamadhanTheme {
        RamadhanAlertDialog(
            title = "Title",
            text = "This is a message for the alert dialog.",
            confirmButtonText = "Confirm",
            dismissButtonText = "Cancel",
            onDismiss = {},
            onConfirm = {}
        )
    }
}
