package com.iqbalwork.ramadhancamp.feature.home.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.iqbalwork.ramadhancamp.feature.home.presentation.model.HomeEvent
import com.iqbalwork.ramadhancamp.feature.home.presentation.model.HomeScreenUiModel
import org.jetbrains.compose.resources.stringResource
import ramadhancamp.composeapp.generated.resources.Res
import ramadhancamp.composeapp.generated.resources.home_main_header_greeting

@Composable
fun HomeMainSuccessContent(
    homeMainData: HomeScreenUiModel,
    action: (HomeEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .padding(horizontal = 24.dp)
            .padding(top = 16.dp)
    ) {
        Column(
            modifier = modifier.fillMaxSize(),
        ) {
            HomeMainHeader(
                modifier = Modifier.padding(bottom = 24.dp),
                greetingText = stringResource(Res.string.home_main_header_greeting),
                city = homeMainData.city,
                country = homeMainData.country,
            )
        }
    }
}