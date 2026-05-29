package com.iqbalwork.ramadhancamp.feature.about.presentation.model

import com.iqbalwork.ramadhancamp.feature.about.domain.model.OssLicense
import com.iqbalwork.ramadhancamp.shared.common.ui.ScreenUiParams

data class OssLicensesState(
    val licenses: List<OssLicense> = emptyList(),
)

class OssLicensesScreenParameters : ScreenUiParams()
