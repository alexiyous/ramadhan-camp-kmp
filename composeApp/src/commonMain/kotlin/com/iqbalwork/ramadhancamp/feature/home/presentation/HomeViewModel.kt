package com.iqbalwork.ramadhancamp.feature.home.presentation

import androidx.lifecycle.viewModelScope
import com.iqbalwork.ramadhancamp.feature.home.domain.repository.HomeRepository
import com.iqbalwork.ramadhancamp.feature.home.presentation.mapper.toErrorEmptyState
import com.iqbalwork.ramadhancamp.feature.home.presentation.model.HomeEffect
import com.iqbalwork.ramadhancamp.feature.home.presentation.model.HomeEvent
import com.iqbalwork.ramadhancamp.feature.home.presentation.model.HomeState
import com.iqbalwork.ramadhancamp.shared.common.navigation.AppNavigationController
import com.iqbalwork.ramadhancamp.shared.common.ui.BaseViewModel
import com.iqbalwork.ramadhancamp.shared.common.ui.components.error.ErrorEmptyState
import com.iqbalwork.ramadhancamp.shared.common.ui.utils.TextResource
import dev.jordond.compass.Coordinates
import dev.jordond.compass.geolocation.GeolocatorResult
import kotlinx.coroutines.launch
import ramadhancamp.composeapp.generated.resources.Res
import ramadhancamp.composeapp.generated.resources.error_location_message
import ramadhancamp.composeapp.generated.resources.error_location_title
import ramadhancamp.composeapp.generated.resources.image_danger_error
import ramadhancamp.composeapp.generated.resources.retry

class HomeViewModel(
    navController: AppNavigationController,
    private val homeRepository: HomeRepository
) : BaseViewModel<Unit, HomeState, HomeEvent, HomeEffect>(
    params = Unit,
    initialState = HomeState(),
    navigationManager = navController,
) {

    init {
        viewModelScope.launch {

        }
    }

    private suspend fun initData() {
        updateState { copy(isLoading = true) }
        val result = homeRepository.getCurrentLocation().getOrNull()
        result?.let { geoResult ->
            when(geoResult) {
                is GeolocatorResult.Success -> {
                    homeRepository.getCurrentCityAndProvince(geoResult.data.coordinates).fold(
                        onSuccess = { (city, province) ->
                            updateState {
                                copy(
                                    screenData = screenData.copy(
                                        city = city,
                                        province = province
                                    ),
                                )
                            }
                        },
                        onFailure = { handleGeoError(geoResult) }
                    )
                }
                is GeolocatorResult.Error -> when(geoResult) {
                    is GeolocatorResult.NotSupported -> handleGeoError(geoResult)
                    is GeolocatorResult.NotFound -> handleGeoError(geoResult)
                    is GeolocatorResult.PermissionDenied -> handleGeoError(geoResult)
                    is GeolocatorResult.GeolocationFailed -> handleGeoError(geoResult)
                }
            }
        }
    }

    private fun handleGeoError(result: GeolocatorResult) {
        updateState {
            copy(
                isLoading = false,
                emptyErrorState = result.toErrorEmptyState(),
                appError = null
            )
        }
    }


    override fun handleEvent(event: HomeEvent) {
        TODO("Not yet implemented")
    }
}
