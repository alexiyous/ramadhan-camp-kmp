package com.iqbalwork.ramadhancamp.feature.pray.presentation

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.iqbalwork.ramadhancamp.feature.pray.presentation.components.NoLocationPlaceholder
import com.iqbalwork.ramadhancamp.feature.pray.presentation.components.PrayScreenSuccessContent
import com.iqbalwork.ramadhancamp.feature.pray.presentation.model.PrayEvent
import com.iqbalwork.ramadhancamp.feature.pray.presentation.model.PrayState
import com.iqbalwork.ramadhancamp.shared.common.extension.rememberViewModel
import com.iqbalwork.ramadhancamp.shared.common.ui.ScreenUiParams
import com.iqbalwork.ramadhancamp.shared.common.ui.components.error.ErrorEmptyState
import com.iqbalwork.ramadhancamp.shared.common.ui.components.error.RamadhanErrorEmptyState
import com.iqbalwork.ramadhancamp.shared.common.ui.components.error.toErrorEmptyState
import com.iqbalwork.ramadhancamp.shared.common.ui.components.loading.Loader
import com.iqbalwork.ramadhancamp.shared.common.ui.rememberDispatch
import com.iqbalwork.ramadhancamp.shared.common.ui.theme.RamadhanTheme
import com.iqbalwork.ramadhancamp.shared.common.ui.utils.TextResource
import com.iqbalwork.ramadhancamp.shared.common.utils.AppError
import dev.icerock.moko.permissions.PermissionsController
import dev.icerock.moko.permissions.compose.BindEffect
import dev.icerock.moko.permissions.compose.rememberPermissionsControllerFactory
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import org.koin.core.parameter.parametersOf
import ramadhancamp.composeapp.generated.resources.Res
import ramadhancamp.composeapp.generated.resources.error_notification_permission_denied_message
import ramadhancamp.composeapp.generated.resources.error_notification_permission_denied_title
import ramadhancamp.composeapp.generated.resources.image_danger_error
import ramadhancamp.composeapp.generated.resources.open_app_settings

private sealed interface AnimateContentState {
    data object Loading : AnimateContentState
    class Error(val error: AppError) : AnimateContentState
    data object NoLocation : AnimateContentState
    data object NotificationPermissionDenied : AnimateContentState
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
    val dispatch = viewModel.rememberDispatch()

    val lifecycleOwner = LocalLifecycleOwner.current
    LifecycleEventEffect(
        Lifecycle.Event.ON_RESUME,
        lifecycleOwner
    ) {
        if (!state.isNotificationPermissionDenied) return@LifecycleEventEffect
        dispatch(PrayEvent.CheckNotificationPermission)
    }

    PrayContent(
        modifier = Modifier.fillMaxSize(),
        state = state,
        action = dispatch
    )

    viewModel.permissionController?.let { permissionsController ->
        BindEffect(permissionsController)
    }
}

@Composable
fun PrayContent(
    modifier: Modifier,
    state: PrayState,
    action: (PrayEvent) -> Unit
) {
    val colors = RamadhanTheme.colors

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
                state.isNotificationPermissionDenied -> AnimateContentState.NotificationPermissionDenied
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

                is AnimateContentState.NotificationPermissionDenied -> RamadhanErrorEmptyState(
                    modifier = Modifier.fillMaxSize(),
                    errorEmptyState = ErrorEmptyState(
                        icon = Res.drawable.image_danger_error,
                        title = TextResource.StringResource(Res.string.error_notification_permission_denied_title),
                        message = TextResource.StringResource(Res.string.error_notification_permission_denied_message),
                        buttonText = TextResource.StringResource(Res.string.open_app_settings)
                    ),
                    onButtonClick = { action(PrayEvent.GoToSetting) }
                )

                is AnimateContentState.Success -> {
                    PrayScreenSuccessContent(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 16.dp),
                        currentCity = state.city,
                        currentCountry = state.country,
                        selectedDate = state.selectedDate,
                        countdown = state.countdown,
                        prayers = state.prayers,
                        onAlarmClicked = { key, enabled -> action(PrayEvent.ToggleAlarm(key, enabled)) },
                        onDateSelect = { date -> action(PrayEvent.DateSelected(date)) },
                    )
                }
            }
        }
    }
}
