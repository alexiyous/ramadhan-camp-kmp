package com.iqbalwork.ramadhancamp.feature.pray.presentation.components

import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import com.iqbalwork.ramadhancamp.shared.common.ui.components.button.RamadhanButton
import com.iqbalwork.ramadhancamp.shared.common.ui.components.button.RamadhanButtonProps
import com.iqbalwork.ramadhancamp.shared.common.ui.theme.RamadhanTheme
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Instant
import org.jetbrains.compose.resources.stringResource
import ramadhancamp.composeapp.generated.resources.Res
import ramadhancamp.composeapp.generated.resources.batal
import ramadhancamp.composeapp.generated.resources.pilih


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrayDatePickerDialog(
    onDismissRequest: () -> Unit,
    onDateSelect: (date: LocalDate) -> Unit,
) {
    val datePickerState = rememberDatePickerState()

    DatePickerDialog(
        onDismissRequest = onDismissRequest,
        confirmButton = {
            RamadhanButton(
                text = stringResource(Res.string.pilih),
                variant = RamadhanButtonProps.Variant.Primary,
                size = RamadhanButtonProps.Size.Small,
                onClick = {
                    datePickerState.selectedDateMillis?.let { millis ->
                        val date = Instant.fromEpochMilliseconds(millis)
                            .toLocalDateTime(TimeZone.currentSystemDefault()).date
                        onDateSelect(date)
                    }
                }
            )
        },
        dismissButton = {
            RamadhanButton(
                text = stringResource(Res.string.batal),
                variant = RamadhanButtonProps.Variant.Tertiary,
                size = RamadhanButtonProps.Size.Small,
                onClick = onDismissRequest
            )
        },
        colors = DatePickerDefaults.colors(
            containerColor = RamadhanTheme.colors.bgSecondary,
        )
    ) {
        DatePicker(
            state = datePickerState,
            colors = DatePickerDefaults.colors(
                containerColor = RamadhanTheme.colors.bgSecondary,
                titleContentColor = RamadhanTheme.colors.textSecondary,
                headlineContentColor = RamadhanTheme.colors.textPrimary,
                weekdayContentColor = RamadhanTheme.colors.textSecondary,
                subheadContentColor = RamadhanTheme.colors.textPrimary,
                navigationContentColor = RamadhanTheme.colors.textPrimary,
                yearContentColor = RamadhanTheme.colors.textPrimary,
                currentYearContentColor = RamadhanTheme.colors.accentSecondary,
                selectedYearContentColor = RamadhanTheme.colors.textPrimary,
                selectedYearContainerColor = RamadhanTheme.colors.accentPrimary,
                dayContentColor = RamadhanTheme.colors.textPrimary,
                selectedDayContentColor = RamadhanTheme.colors.textPrimary,
                selectedDayContainerColor = RamadhanTheme.colors.accentSecondary,
                todayContentColor = RamadhanTheme.colors.accentSecondary,
                todayDateBorderColor = RamadhanTheme.colors.accentSecondary,
                dateTextFieldColors = TextFieldColors(
                    focusedTextColor = RamadhanTheme.colors.textPrimary,
                    unfocusedTextColor = RamadhanTheme.colors.textPrimary,
                    disabledTextColor = RamadhanTheme.colors.textMuted,
                    errorTextColor = RamadhanTheme.colors.accentGold,
                    focusedContainerColor = androidx.compose.ui.graphics.Color.Transparent,
                    unfocusedContainerColor = androidx.compose.ui.graphics.Color.Transparent,
                    disabledContainerColor = androidx.compose.ui.graphics.Color.Transparent,
                    errorContainerColor = androidx.compose.ui.graphics.Color.Transparent,
                    cursorColor = RamadhanTheme.colors.accentPrimary,
                    errorCursorColor = RamadhanTheme.colors.accentGold,
                    textSelectionColors = TextSelectionColors(
                        handleColor = RamadhanTheme.colors.accentPrimary,
                        backgroundColor = RamadhanTheme.colors.accentPrimary.copy(alpha = 0.4f)
                    ),
                    focusedIndicatorColor = RamadhanTheme.colors.accentPrimary,
                    unfocusedIndicatorColor = RamadhanTheme.colors.divider,
                    disabledIndicatorColor = RamadhanTheme.colors.textMuted,
                    errorIndicatorColor = RamadhanTheme.colors.accentGold,
                    focusedLeadingIconColor = RamadhanTheme.colors.accentPrimary,
                    unfocusedLeadingIconColor = RamadhanTheme.colors.textSecondary,
                    disabledLeadingIconColor = RamadhanTheme.colors.textMuted,
                    errorLeadingIconColor = RamadhanTheme.colors.accentGold,
                    focusedTrailingIconColor = RamadhanTheme.colors.accentPrimary,
                    unfocusedTrailingIconColor = RamadhanTheme.colors.textSecondary,
                    disabledTrailingIconColor = RamadhanTheme.colors.textMuted,
                    errorTrailingIconColor = RamadhanTheme.colors.accentGold,
                    focusedLabelColor = RamadhanTheme.colors.accentPrimary,
                    unfocusedLabelColor = RamadhanTheme.colors.textSecondary,
                    disabledLabelColor = RamadhanTheme.colors.textMuted,
                    errorLabelColor = RamadhanTheme.colors.accentGold,
                    focusedPlaceholderColor = RamadhanTheme.colors.textMuted,
                    unfocusedPlaceholderColor = RamadhanTheme.colors.textMuted,
                    disabledPlaceholderColor = RamadhanTheme.colors.textMuted,
                    errorPlaceholderColor = RamadhanTheme.colors.textMuted,
                    focusedSupportingTextColor = RamadhanTheme.colors.textSecondary,
                    unfocusedSupportingTextColor = RamadhanTheme.colors.textSecondary,
                    disabledSupportingTextColor = RamadhanTheme.colors.textMuted,
                    errorSupportingTextColor = RamadhanTheme.colors.accentGold,
                    focusedPrefixColor = RamadhanTheme.colors.textPrimary,
                    unfocusedPrefixColor = RamadhanTheme.colors.textSecondary,
                    disabledPrefixColor = RamadhanTheme.colors.textMuted,
                    errorPrefixColor = RamadhanTheme.colors.accentGold,
                    focusedSuffixColor = RamadhanTheme.colors.textPrimary,
                    unfocusedSuffixColor = RamadhanTheme.colors.textSecondary,
                    disabledSuffixColor = RamadhanTheme.colors.textMuted,
                    errorSuffixColor = RamadhanTheme.colors.accentGold
                )
            )
        )
    }
}