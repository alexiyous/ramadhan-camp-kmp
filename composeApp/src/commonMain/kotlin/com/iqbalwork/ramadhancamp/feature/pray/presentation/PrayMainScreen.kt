package com.iqbalwork.ramadhancamp.feature.pray.presentation

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.iqbalwork.ramadhancamp.feature.pray.presentation.components.NextPrayerCard
import com.iqbalwork.ramadhancamp.feature.pray.presentation.components.NoLocationPlaceholder
import com.iqbalwork.ramadhancamp.feature.pray.presentation.components.PrayHeader
import com.iqbalwork.ramadhancamp.feature.pray.presentation.components.PrayerRowItem
import com.iqbalwork.ramadhancamp.feature.pray.presentation.model.PrayEffect
import com.iqbalwork.ramadhancamp.feature.pray.presentation.model.PrayEvent
import com.iqbalwork.ramadhancamp.feature.pray.presentation.model.PrayState
import com.iqbalwork.ramadhancamp.shared.common.extension.rememberViewModel
import com.iqbalwork.ramadhancamp.shared.common.ui.ScreenUiParams
import com.iqbalwork.ramadhancamp.shared.common.ui.components.error.RamadhanErrorEmptyState
import com.iqbalwork.ramadhancamp.shared.common.ui.components.error.toErrorEmptyState
import com.iqbalwork.ramadhancamp.shared.common.ui.components.loading.Loader
import com.iqbalwork.ramadhancamp.shared.common.ui.rememberDispatch
import com.iqbalwork.ramadhancamp.shared.common.ui.theme.RamadhanTheme
import com.iqbalwork.ramadhancamp.shared.common.utils.AppError
import dev.icerock.moko.permissions.PermissionsController
import dev.icerock.moko.permissions.compose.BindEffect
import dev.icerock.moko.permissions.compose.rememberPermissionsControllerFactory
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import org.koin.core.parameter.parametersOf

private sealed interface AnimateContentState {
    data object Loading : AnimateContentState
    class Error(val error: AppError) : AnimateContentState
    data object NoLocation : AnimateContentState
    data object Success : AnimateContentState
}

@Serializable
data class PrayMainScreenParameters(
    @Transient
    val permissionController: PermissionsController? = null
): ScreenUiParams()

@Composable
fun PrayMainScreen(
    parameters: PrayMainScreenParameters
) {
    val permissionController = rememberPermissionsControllerFactory().createPermissionsController()
    val params = parameters.copy(permissionController = permissionController)
    val viewModel: PrayViewModel = rememberViewModel(parameters = { parametersOf(params) })
    val state by viewModel.state.collectAsStateWithLifecycle()
    PrayContent(
        modifier = Modifier.fillMaxSize(),
        state = state,
        action = viewModel.rememberDispatch(),
        effects = viewModel.effects
    )

    viewModel.permissionController?.let { permissionsController ->
        BindEffect(permissionsController)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrayContent(
    modifier: Modifier,
    state: PrayState,
    action: (PrayEvent) -> Unit,
    effects: Flow<PrayEffect>
) {
    val colors = RamadhanTheme.colors
    var showPermissionAlertDialog by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(effects) {
        effects.collect { effect ->
            when(effect) {
                is PrayEffect.ShowPermissionsDeniedDialog -> showPermissionAlertDialog = true
            }
        }
    }

    Box(
        modifier = modifier
            .background(colors.bgPrimary)
    ) {

        AnimatedContent(
            modifier = Modifier.fillMaxSize(),
            targetState = when {
                state.isLoading -> AnimateContentState.Loading
                state.error != null -> AnimateContentState.Error(state.error)
                !state.hasLocation -> AnimateContentState.NoLocation
                else -> AnimateContentState.Success
            }
        ) { targetState ->
            when(targetState) {
                is AnimateContentState.NoLocation -> NoLocationPlaceholder()

                is AnimateContentState.Loading -> Loader(modifier = Modifier.fillMaxSize())

                is AnimateContentState.Error -> RamadhanErrorEmptyState(
                    modifier = Modifier.fillMaxSize(),
                    errorEmptyState = targetState.error.toErrorEmptyState(),
                    onButtonClick = { action(PrayEvent.RetryLoadSchedule) }
                )

                is AnimateContentState.Success -> {

                }
            }
        }

        if (state.isDatePickerVisible) {
            val datePickerState = rememberDatePickerState()
            DatePickerDialog(
                onDismissRequest = { action(PrayEvent.CloseDatePicker) },
                confirmButton = {
                    TextButton(onClick = {
                        datePickerState.selectedDateMillis?.let { millis ->
                            val date = Instant.fromEpochMilliseconds(millis)
                                .toLocalDateTime(TimeZone.currentSystemDefault()).date
                            action(PrayEvent.DateSelected(date))
                        }
                    }) { Text("OK") }
                },
                dismissButton = {
                    TextButton(onClick = { action(PrayEvent.CloseDatePicker) }) {
                        Text("Cancel")
                    }
                }
            ) {
                DatePicker(state = datePickerState)
            }
        }
    }
}
