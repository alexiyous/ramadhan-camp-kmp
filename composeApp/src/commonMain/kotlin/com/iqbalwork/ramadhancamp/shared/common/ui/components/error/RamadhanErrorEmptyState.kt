package com.iqbalwork.ramadhancamp.shared.common.ui.components.error

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.iqbalwork.ramadhancamp.shared.common.ui.theme.RamadhanTheme
import com.iqbalwork.ramadhancamp.shared.common.ui.utils.TextResource
import com.iqbalwork.ramadhancamp.shared.common.ui.utils.asString
import com.iqbalwork.ramadhancamp.shared.common.utils.AppError
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import ramadhancamp.composeapp.generated.resources.Res
import ramadhancamp.composeapp.generated.resources.error_critical_message
import ramadhancamp.composeapp.generated.resources.error_critical_title
import ramadhancamp.composeapp.generated.resources.error_network_message
import ramadhancamp.composeapp.generated.resources.error_network_title
import ramadhancamp.composeapp.generated.resources.image_danger_error
import ramadhancamp.composeapp.generated.resources.image_network_error
import ramadhancamp.composeapp.generated.resources.image_server_error
import ramadhancamp.composeapp.generated.resources.retry

data class ErrorEmptyState(
    val icon: DrawableResource,
    val title: TextResource,
    val message: TextResource,
    val buttonText: TextResource?
)

@Composable
fun RamadhanErrorEmptyState(
    modifier: Modifier = Modifier,
    errorEmptyState: ErrorEmptyState,
    onButtonClick: () -> Unit ,
) {
    EmptyContent(
        modifier = modifier,
        title = errorEmptyState.title.asString(),
        subtitle = errorEmptyState.message.asString(),
        image = painterResource(errorEmptyState.icon),
        buttonText = errorEmptyState.buttonText?.asString(),
        buttonAction = onButtonClick
    )
}

fun AppError.toErrorEmptyState(
    customTitle: TextResource? = null,
    customMessage: TextResource? = null,
    customNetworkErrorIcon: ((Int) -> DrawableResource?)? = null,
) : ErrorEmptyState {
    return when(this) {
        is AppError.NetworkError -> ErrorEmptyState(
            icon = Res.drawable.image_network_error,
            title = customTitle ?: TextResource.StringResource(Res.string.error_network_title),
            message = customMessage ?: TextResource.StringResource(Res.string.error_network_message),
            buttonText = TextResource.StringResource(Res.string.retry)
        )
        is AppError.ServerError -> ErrorEmptyState(
            icon = if (this.isServerError) {
                Res.drawable.image_danger_error
            } else {
                customNetworkErrorIcon?.invoke(httpCode) ?: Res.drawable.image_server_error
            },
            title = TextResource.PlainText(this.httpCode.toString()),
            message = TextResource.PlainText(this.message),
            buttonText = TextResource.StringResource(Res.string.retry)
        )
        is AppError.UnexpectedError -> ErrorEmptyState(
            icon = Res.drawable.image_danger_error,
            title = TextResource.StringResource(Res.string.error_critical_title),
            message = TextResource.StringResource(Res.string.error_critical_message),
            buttonText = TextResource.StringResource(Res.string.retry)
        )
    }
}

@Preview
@Composable
fun RamadhanErrorEmptyStatePreview() {
    RamadhanTheme {
        RamadhanErrorEmptyState(
            errorEmptyState = AppError.NetworkError(
                message = "No Internet Connection",
                cause = Exception()
            ).toErrorEmptyState(),
            onButtonClick = {}
        )
    }
}
