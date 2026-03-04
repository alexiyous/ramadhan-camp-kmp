package com.iqbalwork.ramadhancamp.feature.home.presentation.mapper

import com.iqbalwork.ramadhancamp.feature.home.domain.model.LastSurahRead
import com.iqbalwork.ramadhancamp.feature.home.domain.model.NextPrayer
import com.iqbalwork.ramadhancamp.feature.home.presentation.model.LastSurahReadUiModel
import com.iqbalwork.ramadhancamp.feature.home.presentation.model.NextPrayerUiModel

fun NextPrayer.toUiModel(): NextPrayerUiModel {
    return NextPrayerUiModel(
        nextPrayerName = this.name,
        nextPrayerTime = this.time,
        remainingMinutesToNextPrayer = this.remainingMinutes
    )
}

fun LastSurahRead.toUiModel(): LastSurahReadUiModel {
    return LastSurahReadUiModel(
        surahName = this.surahName,
        ayatNumber = this.ayatNumber,
        readDate = this.readDate
  )
}