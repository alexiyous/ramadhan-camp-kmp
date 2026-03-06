package com.iqbalwork.ramadhancamp.feature.home.presentation.locationpicker

import androidx.lifecycle.viewModelScope
import com.iqbalwork.ramadhancamp.feature.home.domain.repository.HomeRepository
import com.iqbalwork.ramadhancamp.feature.home.presentation.HomeViewModel
import com.iqbalwork.ramadhancamp.feature.home.presentation.locationpicker.model.LocationPickerEffect
import com.iqbalwork.ramadhancamp.feature.home.presentation.locationpicker.model.LocationPickerEvent
import com.iqbalwork.ramadhancamp.feature.home.presentation.locationpicker.model.LocationPickerState
import com.iqbalwork.ramadhancamp.feature.home.presentation.locationpicker.model.LocationResult
import com.iqbalwork.ramadhancamp.shared.common.navigation.AppNavigationController
import com.iqbalwork.ramadhancamp.shared.common.navigation.NavigationResult
import com.iqbalwork.ramadhancamp.shared.common.ui.BaseViewModel
import com.iqbalwork.ramadhancamp.shared.common.utils.toAppError
import kotlinx.coroutines.launch

class LocationPickerViewModel(
    navController: AppNavigationController,
    private val homeRepository: HomeRepository,
) : BaseViewModel<Unit, LocationPickerState, LocationPickerEvent, LocationPickerEffect>(
    params = Unit,
    initialState = LocationPickerState(),
    navigationManager = navController,
) {
    init {
        viewModelScope.launch { loadProvinces() }
    }

    private suspend fun loadProvinces() {
        updateState { copy(isLoadingProvinces = true, error = null) }
        homeRepository.getProvinces().fold(
            onSuccess = { provinces ->
                updateState { copy(isLoadingProvinces = false, provinces = provinces) }
            },
            onFailure = { error ->
                updateState { copy(isLoadingProvinces = false, error = error.toAppError()) }
            },
        )
    }

    private suspend fun loadKabKota(provinsi: String) {
        updateState { copy(isLoadingCities = true, cities = emptyList(), selectedCity = null, error = null) }
        homeRepository.getKabKota(provinsi).fold(
            onSuccess = { cities ->
                updateState { copy(isLoadingCities = false, cities = cities) }
            },
            onFailure = { error ->
                updateState { copy(isLoadingCities = false, error = error.toAppError()) }
            },
        )
    }

    override fun handleEvent(event: LocationPickerEvent) {
        when (event) {
            is LocationPickerEvent.SelectProvince -> {
                updateState { copy(selectedProvince = event.province, selectedCity = null, cities = emptyList()) }
                viewModelScope.launch { loadKabKota(event.province) }
            }
            is LocationPickerEvent.SelectCity -> {
                updateState { copy(selectedCity = event.city) }
            }
            LocationPickerEvent.Confirm -> {
                val province = state.value.selectedProvince ?: return
                val city = state.value.selectedCity ?: return
                navigationManager.back(
                    NavigationResult.Success(
                        key = HomeViewModel.LOCATION_PICKER_RESULT_KEY,
                        value = LocationResult(province = province, city = city),
                    )
                )
            }
            LocationPickerEvent.Cancel -> navigationManager.back()
        }
    }
}
