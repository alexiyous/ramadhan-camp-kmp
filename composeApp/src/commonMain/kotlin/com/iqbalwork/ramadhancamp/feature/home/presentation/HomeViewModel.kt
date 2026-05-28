package com.iqbalwork.ramadhancamp.feature.home.presentation

import androidx.lifecycle.viewModelScope
import com.iqbalwork.ramadhancamp.feature.home.domain.repository.HomeRepository
import com.iqbalwork.ramadhancamp.feature.home.presentation.mapper.toErrorEmptyState
import com.iqbalwork.ramadhancamp.feature.home.presentation.mapper.toSurahUi
import com.iqbalwork.ramadhancamp.feature.home.presentation.mapper.toUiModel
import com.iqbalwork.ramadhancamp.feature.home.presentation.model.HomeEffect
import com.iqbalwork.ramadhancamp.feature.home.presentation.model.HomeEvent
import com.iqbalwork.ramadhancamp.feature.home.presentation.model.HomeState
import com.iqbalwork.ramadhancamp.feature.quran.presentation.route.QuranTab
import com.iqbalwork.ramadhancamp.shared.common.navigation.LastSurahNavigationData
import com.iqbalwork.ramadhancamp.shared.common.navigation.NavigationManager
import com.iqbalwork.ramadhancamp.shared.common.navigation.NavigationResult
import com.iqbalwork.ramadhancamp.shared.common.navigation.NavigationResultData
import com.iqbalwork.ramadhancamp.shared.common.navigation.SurahNavigationData
import com.iqbalwork.ramadhancamp.shared.common.navigation.TabDestination
import com.iqbalwork.ramadhancamp.shared.common.ui.BaseViewModel
import com.iqbalwork.ramadhancamp.shared.common.utils.date.getCurrentDateLocalized
import com.iqbalwork.ramadhancamp.shared.common.utils.goToDeviceSettings
import com.iqbalwork.ramadhancamp.shared.common.utils.toAppError
import dev.jordond.compass.geolocation.GeolocatorResult
import io.github.aakira.napier.log
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

class HomeViewModel(
    navController: NavigationManager,
    params: HomeMainScreenParameters,
    private val homeRepository: HomeRepository,
) : BaseViewModel<HomeMainScreenParameters, HomeState, HomeEvent, HomeEffect>(
    params = params,
    initialState = HomeState(),
    navigationManager = navController,
    resultKeys = arrayOf(),
) {

    init {
        viewModelScope.launch {
            initData()
            launch { subscribeNextPrayer() }
            launch { subscribeLastSurahRead() }
            launch { getPopularSurah() }
            updateState { copy(screenData = screenData.copy(haveInitialized = true)) }
        }
    }

    private suspend fun getPopularSurah() {
        homeRepository.getPopularSurah()
            .onFailure {
                updateState { copy(
                    appError = it.toAppError(),
                ) }
            }
            .onSuccess {
                updateState { copy(
                    screenData = screenData.copy(
                        popularSurahList = it.map { surah -> surah.toSurahUi() }
                    ),
                    appError = null,
                ) }
            }
    }

    private suspend fun initData() {
        updateState { copy(isLoading = true, appError = null, emptyErrorState = null) }

        params.locationData?.let {
            homeRepository.saveManualLocation(it.province, it.city)

            updateState {
                copy(
                    screenData = screenData.copy(
                        city = it.city,
                        country = it.country,
                        currentDate = getCurrentDateLocalized(),
                    ),
                    pickedThroughPicker = true
                )
            }

            getShalatSchedule(it.province, it.city)
            return
        }

        val result = homeRepository.getCurrentCoordinate().getOrNull()
        result?.let { geoResult ->
            when(geoResult) {
                is GeolocatorResult.Success -> {
                    homeRepository.getCurrentPlace(geoResult.data.coordinates).fold(
                        onSuccess = { (city, province, country) ->
                            updateState {
                                copy(
                                    screenData = screenData.copy(
                                        city = city,
                                        country = country,
                                        currentDate = getCurrentDateLocalized()
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

    private suspend fun subscribeLastSurahRead() {
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
            .onSuccess {
                updateState {
                    copy(isLoading = false, appError = null, emptyErrorState = null)
                }
            }
            .onFailure {
                navigationManager.navigateTo(TabDestination.HomeLocationPicker, withReplace = true)
                updateState {
                    copy(isLoading = false)
                }
            }
    }

    override fun handleEvent(event: HomeEvent) {
        when (event) {
            HomeEvent.LoadInitialData -> viewModelScope.launch { initData() }
            HomeEvent.GoToSetting -> goToDeviceSettings()
            HomeEvent.NavigateToLocationPicker -> navigationManager.navigateTo(TabDestination.HomeLocationPicker)
            HomeEvent.OnSearchBoxClicked -> {
                navigationManager.switchTab(QuranTab)
                viewModelScope.launch {
                    delay(300) // Wait for Quran tab to compose and subscribe
                    navigationManager.sendResult(NavigationResult.Success("focus_search", NavigationResultData.EMPTY))
                }
            }
            HomeEvent.OnLastSurahClicked -> {
                val lastSurahData = state.value.screenData.lastSurahReadData ?: return
                navigationManager.switchTab(QuranTab)
                viewModelScope.launch {
                    delay(300)
                    navigationManager.sendResult(
                        NavigationResult.Success(
                            "navigate_to_last_ayah",
                            LastSurahNavigationData(lastSurahData.surahId, lastSurahData.ayatNumber)
                        )
                    )
                }
            }
            is HomeEvent.OnPopularSurahClicked -> {
                navigationManager.switchTab(QuranTab)
                viewModelScope.launch {
                    delay(300)
                    navigationManager.sendResult(
                        NavigationResult.Success(
                            "navigate_to_surah",
                            SurahNavigationData(event.surahId)
                        )
                    )
                }
            }
        }
    }
}
