package com.iqbalwork.ramadhancamp.feature.home.presentation.locationpicker

import androidx.lifecycle.viewModelScope
import com.iqbalwork.ramadhancamp.feature.home.domain.repository.HomeRepository
import com.iqbalwork.ramadhancamp.feature.home.presentation.locationpicker.model.LocationPickerEffect
import com.iqbalwork.ramadhancamp.feature.home.presentation.locationpicker.model.LocationPickerEvent
import com.iqbalwork.ramadhancamp.feature.home.presentation.locationpicker.model.LocationPickerState
import com.iqbalwork.ramadhancamp.feature.home.presentation.locationpicker.model.LocationResult
import com.iqbalwork.ramadhancamp.feature.home.presentation.locationpicker.model.canConfirm
import com.iqbalwork.ramadhancamp.shared.common.navigation.NavigationManager
import com.iqbalwork.ramadhancamp.shared.common.navigation.NavigationResult
import com.iqbalwork.ramadhancamp.shared.common.ui.BaseViewModel
import com.iqbalwork.ramadhancamp.shared.common.utils.toAppError
import kotlinx.coroutines.launch

const val LOCATION_PICKER_RESULT_KEY = "home_location_picker_result_key"

class LocationPickerViewModel(
    navController: NavigationManager,
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
        updateState { copy(isLoading = true, error = null) }
        homeRepository.getProvinces().fold(
            onSuccess = { provinces ->
                updateState { copy(isLoading = false, provinces = provinces) }
            },
            onFailure = { error ->
                updateState { copy(isLoading = false, error = error.toAppError()) }
            },
        )
    }

    private suspend fun loadKabKota(provinsi: String) {
        updateState { copy(isLoading = true, cities = emptyList(), selectedCity = null, error = null) }
        homeRepository.getKabKota(provinsi).fold(
            onSuccess = { cities ->
                updateState { copy(isLoading = false, cities = cities) }
            },
            onFailure = { error ->
                updateState { copy(isLoading = false, error = error.toAppError()) }
            },
        )
    }

    override fun handleEvent(event: LocationPickerEvent) {
        when (event) {
            is LocationPickerEvent.LoadProvinces -> {
                viewModelScope.launch { loadProvinces() }
            }

            is LocationPickerEvent.SelectProvince -> {
                updateState { copy(selectedProvince = event.province, selectedCity = null, cities = emptyList()) }
                viewModelScope.launch { loadKabKota(event.province) }
            }
            is LocationPickerEvent.SelectCity -> {
                updateState { copy(selectedCity = event.city) }
            }
            LocationPickerEvent.Confirm -> {
                if (!state.value.canConfirm) return
                val city = state.value.selectedCity
                val province = state.value.selectedProvince
                navigationManager.back(
                    NavigationResult.Success(
                        key = LOCATION_PICKER_RESULT_KEY,
                        value = LocationResult(country = "Indonesia", province = province!!, city = city!!),
                    )
                )
            }
            LocationPickerEvent.Cancel -> updateState { copy(selectedProvince = null, selectedCity = null) }
        }
    }
}
