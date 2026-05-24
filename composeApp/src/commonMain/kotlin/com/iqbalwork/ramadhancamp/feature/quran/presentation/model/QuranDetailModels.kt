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
    val playingAyat: Ayat? = null,
    val nextAyatAudioUrl: String? = null,
    val isPlaying: Boolean = false,
    val isBuffering: Boolean = false,
    val totalTimeMs: Long = 0L,
    val currentTimeMs: Long = 0L,
    val autoScrolledToPlayingAyat: Boolean = false,
    val hasScrolledToInitialAyah: Boolean = false,
    val bookmarkedAyatNumbers: Set<Int> = emptySet()
)

sealed interface QuranDetailEvent : UiEvent {
    data class OpenAyatSheet(val ayat: Ayat) : QuranDetailEvent
    data class PlayAudio(val ayat: Ayat) : QuranDetailEvent
    data object StopAudio : QuranDetailEvent
    data object TogglePlayPause : QuranDetailEvent
    data object PlayNextAyat : QuranDetailEvent
    data object PlayPrevAyat : QuranDetailEvent
    data class ShareAyat(val text: String) : QuranDetailEvent
    data class AyatRead(val surahId: Int, val surahName: String, val ayatNumber: Int) : QuranDetailEvent
    data object Back : QuranDetailEvent
    data class AudioError(val message: String) : QuranDetailEvent
    data object Retry : QuranDetailEvent
    data object OnAppPause : QuranDetailEvent
    data object OnAppResume : QuranDetailEvent
    data object OnScreenDispose : QuranDetailEvent
    data object OnScreenResume : QuranDetailEvent
    data class OnSeekAudio(val positionMs: Long) : QuranDetailEvent
    data object AutoScrollToPlayingAyatConsumed : QuranDetailEvent
    data object InitialScrollConsumed : QuranDetailEvent
}

sealed interface QuranDetailEffect : UiEffect
