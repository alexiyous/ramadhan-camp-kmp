package com.iqbalwork.ramadhancamp.feature.home.presentation

import androidx.lifecycle.viewModelScope
import com.iqbalwork.ramadhancamp.feature.home.domain.repository.HomeRepository
import com.iqbalwork.ramadhancamp.feature.home.presentation.locationpicker.model.LocationResult
import com.iqbalwork.ramadhancamp.feature.home.presentation.mapper.toErrorEmptyState
import com.iqbalwork.ramadhancamp.feature.home.presentation.mapper.toUiModel
import com.iqbalwork.ramadhancamp.feature.home.presentation.model.HomeEffect
import com.iqbalwork.ramadhancamp.feature.home.presentation.model.HomeEvent
import com.iqbalwork.ramadhancamp.feature.home.presentation.model.HomeState
import com.iqbalwork.ramadhancamp.shared.common.navigation.AppNavigationController
import com.iqbalwork.ramadhancamp.shared.common.navigation.NavigationResultData
import com.iqbalwork.ramadhancamp.shared.common.navigation.TabDestination
import com.iqbalwork.ramadhancamp.shared.common.ui.BaseViewModel
import com.iqbalwork.ramadhancamp.shared.common.utils.goToDeviceSettings
import com.iqbalwork.ramadhancamp.shared.common.utils.toAppError
import dev.jordond.compass.geolocation.GeolocatorResult
import io.github.aakira.napier.log
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

class HomeViewModel(
    navController: AppNavigationController,
    private val homeRepository: HomeRepository,
) : BaseViewModel<Unit, HomeState, HomeEvent, HomeEffect>(
    params = Unit,
    initialState = HomeState(),
    navigationManager = navController,
    resultKeys = arrayOf(LOCATION_PICKER_RESULT_KEY),
) {
    companion object {
        const val LOCATION_PICKER_RESULT_KEY = "home_location_picker"
    }

    init {
        viewModelScope.launch {
            initData()
            launch { homeRepository.observerNextPrayer() }
            launch { subscribeNextPrayer() }
            launch { observeLastSurahRead() }
        }
    }

    private suspend fun initData() {
        updateState { copy(isLoading = true, appError = null, emptyErrorState = null) }
        val result = homeRepository.getCurrentLocation().getOrNull()
        result?.let { geoResult ->
            when(geoResult) {
                is GeolocatorResult.Success -> {
                    homeRepository.getCurrentCityAndProvince(geoResult.data.coordinates).fold(
                        onSuccess = { (city, province, country) ->
                            log { "Got city and province from coordinates: ${geoResult.data.coordinates}, city: $city, province: $province, country: $country" }

                            updateState {
                                copy(
                                    screenData = screenData.copy(
                                        city = city,
                                        province = province,
                                        country = country
                                    ),
                                )
                            }
                            getShalatSchedule(province, city)
                        },
                        onFailure = {
                            handleGeoError(geoResult) }
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
                appError = null,
                isPermissionDenied = result is GeolocatorResult.PermissionDenied
            )
        }
    }

    private suspend fun subscribeNextPrayer() {
        homeRepository.nextPrayer
            .distinctUntilChanged()
            .collectLatest {
                updateState {
                    copy(
                        screenData = screenData.copy(
                            nextPrayerData = it.toUiModel()
                        )
                    )
                }
            }
    }

    private suspend fun observeLastSurahRead() {
        homeRepository.lastSurahRead
            .distinctUntilChanged()
            .collectLatest {
                updateState {
                    copy(
                        screenData = screenData.copy(
                            lastSurahReadData = it?.toUiModel()
                        )
                    )
                }
            }
    }

    private suspend fun getShalatSchedule(province: String, city: String) {
        homeRepository.getShalatSchedule(province, city)
            .onFailure {
                // City/province not recognised by the API — let the user pick manually
                navigationManager.navigateToInsideTab(TabDestination.HomeLocationPicker)
            }
            .onSuccess {
                updateState {
                    copy(isLoading = false, appError = null, emptyErrorState = null)
                }
            }
    }

    override fun navigationResultSuccess(key: String, data: NavigationResultData?) {
        if (key == LOCATION_PICKER_RESULT_KEY) {
            val result = data as? LocationResult ?: return
            viewModelScope.launch {
                homeRepository.saveManualLocation(result.province, result.city)
                updateState {
                    copy(
                        screenData = screenData.copy(
                            city = result.city,
                            province = result.province,
                        )
                    )
                }
                getShalatSchedule(result.province, result.city)
            }
        }
    }

    override fun handleEvent(event: HomeEvent) {
        when (event) {
            HomeEvent.LoadInitialData -> viewModelScope.launch { initData() }
            HomeEvent.GoToSetting -> goToDeviceSettings()
        }
    }
}
