package com.iqbalwork.ramadhancamp.feature.quran.presentation

import com.iqbalwork.ramadhancamp.shared.common.ui.ScreenUiParams
import kotlinx.serialization.Serializable

@Serializable
data class QuranSheetScreenParameters(
    val surahId: Int,
    val ayatNumber: Int,
    val surahName: String,
    val teksArab: String,
    val teksLatin: String,
    val teksIndonesia: String
) : ScreenUiParams()