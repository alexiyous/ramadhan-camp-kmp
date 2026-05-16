package com.iqbalwork.ramadhancamp.feature.quran.presentation

import com.iqbalwork.ramadhancamp.feature.quran.presentation.model.QuranSheetEffect
import com.iqbalwork.ramadhancamp.feature.quran.presentation.model.QuranSheetEvent
import com.iqbalwork.ramadhancamp.feature.quran.presentation.model.QuranSheetState
import com.iqbalwork.ramadhancamp.shared.common.navigation.AyatNumberResult
import com.iqbalwork.ramadhancamp.shared.common.navigation.NavigationManager
import com.iqbalwork.ramadhancamp.shared.common.navigation.NavigationResult
import com.iqbalwork.ramadhancamp.shared.common.ui.BaseViewModel
import com.iqbalwork.ramadhancamp.shared.common.utils.ShareManager
import kotlinx.coroutines.launch

class QuranSheetViewModel(
    params: QuranSheetScreenParameters,
    navigationManager: NavigationManager,
    private val shareManager: ShareManager,
) : BaseViewModel<QuranSheetScreenParameters, QuranSheetState, QuranSheetEvent, QuranSheetEffect>(
    params, QuranSheetState(), navigationManager
) {
    override fun handleEvent(event: QuranSheetEvent) {
        when (event) {
            is QuranSheetEvent.PlayAudio -> {
                navigationManager.back(
                    NavigationResult.Success(
                        key = "quran_sheet_play",
                        value = AyatNumberResult(params.ayatNumber)
                    )
                )
            }
            is QuranSheetEvent.Bookmark -> {
                // Bookmark functionality can be implemented later
                // This would use BookmarkRepository when ready
                updateState { copy(bookmarkMessage = "Bookmark feature coming soon") }
            }
            is QuranSheetEvent.Share -> {
                val shareText = buildString {
                    append(params.teksArab)
                    append("\n\n")
                    append(params.teksLatin)
                    append("\n\n")
                    append(params.teksIndonesia)
                }
                shareManager.shareText(shareText)
            }
            is QuranSheetEvent.Copy -> {
                val copyText = buildString {
                    append(params.teksArab)
                    append("\n\n")
                    append(params.teksLatin)
                    append("\n\n")
                    append(params.teksIndonesia)
                }
                sendEffect(QuranSheetEffect.CopyToClipboard(copyText))
            }
            is QuranSheetEvent.Dismiss -> {
                navigationManager.back()
            }
            is QuranSheetEvent.BookmarkSuccessHandled -> {
                updateState { copy(bookmarkMessage = null) }
            }
        }
    }
}
