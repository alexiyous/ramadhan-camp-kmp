package com.iqbalwork.ramadhancamp.feature.home.presentation.model

data class HomeScreenUiModel(
    val city: String = "",
    val province: String = "",
    val nextPrayerData: NextPrayerUiModel = NextPrayerUiModel.EMPTY,
    val lastSurahReadData: LastSurahReadUiModel? = null,
)
