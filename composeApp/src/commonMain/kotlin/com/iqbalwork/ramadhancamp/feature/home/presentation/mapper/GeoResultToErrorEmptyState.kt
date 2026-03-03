package com.iqbalwork.ramadhancamp.feature.home.presentation.mapper

import com.iqbalwork.ramadhancamp.shared.common.ui.components.error.ErrorEmptyState
import com.iqbalwork.ramadhancamp.shared.common.ui.utils.TextResource
import dev.jordond.compass.geolocation.GeolocatorResult
import ramadhancamp.composeapp.generated.resources.Res
import ramadhancamp.composeapp.generated.resources.error_location_message
import ramadhancamp.composeapp.generated.resources.error_location_not_found_message
import ramadhancamp.composeapp.generated.resources.error_location_not_supported_message
import ramadhancamp.composeapp.generated.resources.error_location_permission_denied_message
import ramadhancamp.composeapp.generated.resources.error_location_permission_denied_title
import ramadhancamp.composeapp.generated.resources.error_location_title
import ramadhancamp.composeapp.generated.resources.image_danger_error
import ramadhancamp.composeapp.generated.resources.retry

fun GeolocatorResult.toErrorEmptyState(): ErrorEmptyState {
    return when (this) {
        is GeolocatorResult.Success -> ErrorEmptyState(
            icon = Res.drawable.image_danger_error,
            title = TextResource.StringResource(Res.string.error_location_title),
            message = TextResource.StringResource(Res.string.error_location_message),
            buttonText = TextResource.StringResource(Res.string.retry)
        )
        is GeolocatorResult.Error -> when (this) {
            is GeolocatorResult.NotSupported -> ErrorEmptyState(
                icon = Res.drawable.image_danger_error,
                title = TextResource.StringResource(Res.string.error_location_title),
                message = TextResource.StringResource(Res.string.error_location_not_supported_message),
                buttonText = TextResource.StringResource(Res.string.retry)
            )
            is GeolocatorResult.NotFound -> ErrorEmptyState(
                icon = Res.drawable.image_danger_error,
                title = TextResource.StringResource(Res.string.error_location_title),
                message = TextResource.StringResource(Res.string.error_location_not_found_message),
                buttonText = TextResource.StringResource(Res.string.retry)
            )
            is GeolocatorResult.PermissionDenied -> ErrorEmptyState(
                icon = Res.drawable.image_danger_error,
                title = TextResource.StringResource(Res.string.error_location_permission_denied_title),
                message = TextResource.StringResource(Res.string.error_location_permission_denied_message),
                buttonText = TextResource.StringResource(Res.string.retry)
            )
            is GeolocatorResult.GeolocationFailed -> ErrorEmptyState(
                icon = Res.drawable.image_danger_error,
                title = TextResource.StringResource(Res.string.error_location_title),
                message = TextResource.StringResource(Res.string.error_location_message),
                buttonText = TextResource.StringResource(Res.string.retry)
            )
            else -> ErrorEmptyState(
                icon = Res.drawable.image_danger_error,
                title = TextResource.StringResource(Res.string.error_location_title),
                message = TextResource.StringResource(Res.string.error_location_message),
                buttonText = TextResource.StringResource(Res.string.retry)
            )
        }
    }
}