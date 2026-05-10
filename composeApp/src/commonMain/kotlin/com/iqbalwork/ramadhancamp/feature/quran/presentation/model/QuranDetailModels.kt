package com.iqbalwork.ramadhancamp.feature.quran.presentation.model

import com.iqbalwork.ramadhancamp.feature.quran.domain.model.Ayat
import com.iqbalwork.ramadhancamp.feature.quran.domain.model.SurahDetail
import com.iqbalwork.ramadhancamp.shared.common.ui.UiEffect
import com.iqbalwork.ramadhancamp.shared.common.ui.UiEvent
import com.iqbalwork.ramadhancamp.shared.common.utils.AppError

data class QuranDetailState(
    val isLoading: Boolean = true,
    val appError: AppError? = null,
    val surahDetail: SurahDetail? = null,
    val selectedAyatForOptions: Ayat? = null,
    val playingAyat: Ayat? = null,
    val nextAyatAudioUrl: String? = null
)

sealed interface QuranDetailEvent : UiEvent {
    data class PlayAudio(val ayat: Ayat) : QuranDetailEvent
    data object StopAudio : QuranDetailEvent
    data object PlayNextAyat : QuranDetailEvent
    data object PlayPrevAyat : QuranDetailEvent
    data class ShareAyat(val text: String) : QuranDetailEvent
    data class AyatRead(val surahId: Int, val surahName: String, val ayatNumber: Int) : QuranDetailEvent
    data object Back : QuranDetailEvent
    data class OnAyatClicked(val ayat: Ayat) : QuranDetailEvent
    data object OnCloseOptionsSheet : QuranDetailEvent
    data class OnBookmarkClicked(val ayat: Ayat) : QuranDetailEvent
    data class OnShareClicked(val ayat: Ayat) : QuranDetailEvent
    data class OnCopyClicked(val ayat: Ayat) : QuranDetailEvent
    data class AudioError(val message: String) : QuranDetailEvent
    data object Retry : QuranDetailEvent
}

sealed interface QuranDetailEffect : UiEffect
