package com.iqbalwork.ramadhancamp.feature.about.presentation

import com.iqbalwork.ramadhancamp.feature.about.domain.repository.OssLicenseRepository
import com.iqbalwork.ramadhancamp.feature.about.presentation.model.OssLicensesEffect
import com.iqbalwork.ramadhancamp.feature.about.presentation.model.OssLicensesEvent
import com.iqbalwork.ramadhancamp.feature.about.presentation.model.OssLicensesScreenParameters
import com.iqbalwork.ramadhancamp.feature.about.presentation.model.OssLicensesState
import com.iqbalwork.ramadhancamp.shared.common.navigation.NavigationManager
import com.iqbalwork.ramadhancamp.shared.common.ui.BaseViewModel

class OssLicensesViewModel(
    navController: NavigationManager,
    params: OssLicensesScreenParameters,
    private val repository: OssLicenseRepository,
) : BaseViewModel<OssLicensesScreenParameters, OssLicensesState, OssLicensesEvent, OssLicensesEffect>(
    params = params,
    initialState = OssLicensesState(licenses = repository.getOssLicenses()),
    navigationManager = navController,
) {
    override fun handleEvent(event: OssLicensesEvent) {
        when (event) {
            OssLicensesEvent.LoadLicenses -> {
                updateState { copy(licenses = repository.getOssLicenses()) }
            }
            OssLicensesEvent.NavigateBack -> {
                navigationManager.back()
            }
        }
    }
}
