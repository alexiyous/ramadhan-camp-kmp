package com.iqbalwork.ramadhancamp.feature.home.presentation

import androidx.lifecycle.viewModelScope
import com.iqbalwork.ramadhancamp.feature.home.domain.repository.HomeRepository
import com.iqbalwork.ramadhancamp.feature.home.presentation.mapper.toErrorEmptyState
import com.iqbalwork.ramadhancamp.feature.home.presentation.mapper.toUiModel
import com.iqbalwork.ramadhancamp.feature.home.presentation.model.HomeEffect
import com.iqbalwork.ramadhancamp.feature.home.presentation.model.HomeEvent
import com.iqbalwork.ramadhancamp.feature.home.presentation.model.HomeState
import com.iqbalwork.ramadhancamp.shared.common.navigation.AppNavigationController
import com.iqbalwork.ramadhancamp.shared.common.ui.BaseViewModel
import com.iqbalwork.ramadhancamp.shared.common.utils.toAppError
import dev.jordond.compass.geolocation.GeolocatorResult
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

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
            initData()
            launch { observeNextPrayer() }
            launch { observeLastSurahRead() }
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
                            getShalatSchedule(province, city)
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

    private suspend fun observeNextPrayer() {
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
        updateState { copy(isLoading = false) }
        homeRepository.getShalatSchedule(province, city).onFailure {
            updateState {
                copy(
                    emptyErrorState = null,
                    appError = it.toAppError()
                )
            }
        }
    }

    override fun handleEvent(event: HomeEvent) {
        when (event) {
            HomeEvent.LoadInitialData -> viewModelScope.launch { initData() }
        }
    }
}
