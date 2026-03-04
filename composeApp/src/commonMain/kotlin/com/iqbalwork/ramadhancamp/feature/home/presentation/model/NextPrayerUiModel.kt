package com.iqbalwork.ramadhancamp.feature.home.presentation.model

data class NextPrayerUiModel(
    val nextPrayerName: String,
    val nextPrayerTime: String,
    val remainingMinutesToNextPrayer: Int,
) {
    companion object {
        val EMPTY = NextPrayerUiModel(
            nextPrayerName = "",
            nextPrayerTime = "",
            remainingMinutesToNextPrayer = 0,
        )
    }
}
