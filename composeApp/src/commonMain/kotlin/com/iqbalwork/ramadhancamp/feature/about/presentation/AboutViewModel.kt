package com.iqbalwork.ramadhancamp.feature.about.presentation

import com.iqbalwork.ramadhancamp.feature.about.domain.repository.OssLicenseRepository
import com.iqbalwork.ramadhancamp.feature.about.presentation.model.AboutEffect
import com.iqbalwork.ramadhancamp.feature.about.presentation.model.AboutEvent
import com.iqbalwork.ramadhancamp.feature.about.presentation.model.AboutScreenParameters
import com.iqbalwork.ramadhancamp.feature.about.presentation.model.AboutState
import com.iqbalwork.ramadhancamp.shared.common.navigation.NavigationManager
import com.iqbalwork.ramadhancamp.shared.common.navigation.TabDestination
import com.iqbalwork.ramadhancamp.shared.common.ui.BaseViewModel

class AboutViewModel(
    navController: NavigationManager,
    params: AboutScreenParameters,
    private val repository: OssLicenseRepository,
) : BaseViewModel<AboutScreenParameters, AboutState, AboutEvent, AboutEffect>(
    params = params,
    initialState = AboutState(appVersion = repository.getAppVersion()),
    navigationManager = navController,
) {
    override fun handleEvent(event: AboutEvent) {
        when (event) {
            AboutEvent.NavigateToOssLicenses -> {
                navigationManager.navigateToInsideTab(TabDestination.OssLicenses)
            }
        }
    }
}
