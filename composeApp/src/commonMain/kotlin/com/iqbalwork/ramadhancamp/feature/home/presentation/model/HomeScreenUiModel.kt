package com.iqbalwork.ramadhancamp.feature.home.presentation.model

data class HomeScreenUiModel(
    val city: String = "",
    val country: String = "",
    val currentDate: String = "",
    val nextPrayerData: NextPrayerUiModel = NextPrayerUiModel.EMPTY,
    val lastSurahReadData: LastSurahReadUiModel? = null,
)
