package com.iqbalwork.ramadhancamp.feature.quran.presentation

import androidx.lifecycle.viewModelScope
import com.iqbalwork.ramadhancamp.feature.bookmark.domain.model.Bookmark
import com.iqbalwork.ramadhancamp.feature.bookmark.domain.model.Category
import com.iqbalwork.ramadhancamp.feature.bookmark.domain.repository.BookmarkRepository
import com.iqbalwork.ramadhancamp.feature.quran.presentation.model.QuranSheetEffect
import com.iqbalwork.ramadhancamp.feature.quran.presentation.model.QuranSheetEvent
import com.iqbalwork.ramadhancamp.feature.quran.presentation.model.QuranSheetState
import com.iqbalwork.ramadhancamp.feature.quran.presentation.model.SheetStep
import com.iqbalwork.ramadhancamp.shared.common.navigation.AyatNumberResult
import com.iqbalwork.ramadhancamp.shared.common.navigation.NavigationManager
import com.iqbalwork.ramadhancamp.shared.common.navigation.NavigationResult
import com.iqbalwork.ramadhancamp.shared.common.ui.BaseViewModel
import com.iqbalwork.ramadhancamp.shared.common.utils.ShareManager
import com.iqbalwork.ramadhancamp.shared.utils.TAG_BOOKMARK_FTS
import io.github.aakira.napier.Napier
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock

@OptIn(FlowPreview::class)
class QuranSheetViewModel(
    params: QuranSheetScreenParameters,
    navigationManager: NavigationManager,
    private val shareManager: ShareManager,
    private val bookmarkRepository: BookmarkRepository,
) : BaseViewModel<QuranSheetScreenParameters, QuranSheetState, QuranSheetEvent, QuranSheetEffect>(
    params, QuranSheetState(), navigationManager
) {
    private val searchQueryFlow = MutableStateFlow("")

    private val ayatBookmarksMap = mutableMapOf<Long, Long>() // categoryId ? bookmarkId

    init {
        searchQueryFlow
            .debounce(300)
            .distinctUntilChanged()
            .combine(bookmarkRepository.getAllCategories()) { query, allCategories ->
                if (query.isBlank()) allCategories
                else {
                    val lowerQuery = query.lowercase()
                    // Relevance sorting: starts-with first, then word-boundary, then contains
                    val startsWith = mutableListOf<Category>()
                    val wordBoundary = mutableListOf<Category>()
                    val contains = mutableListOf<Category>()
                    for (category in allCategories) {
                        val lowerName = category.name.lowercase()
                        when {
                            lowerName.startsWith(lowerQuery) -> startsWith.add(category)
                            lowerName.contains(" $lowerQuery") -> wordBoundary.add(category)
                            lowerName.contains(lowerQuery) -> contains.add(category)
                        }
                    }
                    startsWith + wordBoundary + contains
                }
            }
            .onEach { filtered ->
                updateState { copy(categories = filtered) }
            }
            .launchIn(viewModelScope)

        observeAyatBookmarks()
    }

    private fun observeAyatBookmarks() {
        combine(
            bookmarkRepository.getAllBookmarks(),
            bookmarkRepository.getAllCategories()
        ) { bookmarks, categories ->
            val ayatBookmarks = bookmarks.filter {
                it.surahId == params.surahId && it.ayatNumber == params.ayatNumber
            }
            ayatBookmarksMap.clear()
            ayatBookmarks.forEach { ayatBookmarksMap[it.categoryId] = it.id }
            val ayatCategoryIds = ayatBookmarks.map { it.categoryId }.toSet()
            val ayatCategories = categories.filter { it.id in ayatCategoryIds }
            Pair(ayatBookmarks.isNotEmpty(), ayatCategories)
        }.onEach { (isBookmarked, bookmarkCategories) ->
            Napier.d(tag = TAG_BOOKMARK_FTS) {
                "Ayat ${params.surahId}:${params.ayatNumber} bookmarked=$isBookmarked categories=$bookmarkCategories"
            }
            updateState { copy(isBookmarked = isBookmarked, bookmarkCategories = bookmarkCategories) }
        }.launchIn(viewModelScope)
    }

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
                if (state.value.isBookmarked) {
                    val bookmarkCategories = state.value.bookmarkCategories
                    if (bookmarkCategories.size <= 1) {
                        val categoryId = bookmarkCategories.firstOrNull()?.id ?: return
                        handleEvent(QuranSheetEvent.MarkForDeletion(categoryId))
                    } else {
                        updateState { copy(step = SheetStep.RemoveFromPlaylist) }
                    }
                } else {
                    updateState { copy(step = SheetStep.PlaylistPicker) }
                }
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
            is QuranSheetEvent.OpenPlaylistPicker -> {
                updateState { copy(step = SheetStep.PlaylistPicker) }
            }
            is QuranSheetEvent.OpenCreatePlaylist -> {
                updateState { copy(step = SheetStep.CreatePlaylist, newCategoryName = "") }
            }
            is QuranSheetEvent.OnSearchQueryChanged -> {
                val trimmed = event.query.trimStart()
                updateState { copy(searchQuery = trimmed) }
                searchQueryFlow.value = trimmed
            }
            is QuranSheetEvent.SelectCategory -> {
                val categoryId = event.categoryId
                updateState { copy(isSaving = true) }
                viewModelScope.launch {
                    val bookmark = Bookmark(
                        id = 0,
                        ayahDetails = "${params.surahName}: ${params.ayatNumber}",
                        categoryId = categoryId,
                        timestamp = Clock.System.now().toEpochMilliseconds(),
                        surahId = params.surahId,
                        ayatNumber = params.ayatNumber,
                        surahName = params.surahName,
                        audioUrl = params.audioUrl, teksArab = params.teksArab, teksIndonesia = params.teksIndonesia
                    )
                    bookmarkRepository.addBookmark(bookmark)
                    updateState { copy(isSaving = false) }
                    sendEffect(QuranSheetEffect.DismissSheet)
                }
            }
            is QuranSheetEvent.CreateNewPlaylist -> {
                val name = event.name.trim()
                if (name.isEmpty()) return
                updateState { copy(isSaving = true) }
                viewModelScope.launch {
                    val newCategoryId = bookmarkRepository.addCategory(
                        Category(id = 0, name = name)
                    )
                    val bookmark = Bookmark(
                        id = 0,
                        ayahDetails = "${params.surahName}: ${params.ayatNumber}",
                        categoryId = newCategoryId,
                        timestamp = Clock.System.now().toEpochMilliseconds(),
                        surahId = params.surahId,
                        ayatNumber = params.ayatNumber,
                        surahName = params.surahName,
                        audioUrl = params.audioUrl, teksArab = params.teksArab, teksIndonesia = params.teksIndonesia
                    )
                    bookmarkRepository.addBookmark(bookmark)
                    updateState { copy(isSaving = false) }
                    sendEffect(QuranSheetEffect.DismissSheet)
                }
            }
            is QuranSheetEvent.BackFromStep -> {
                val currentStep = state.value.step
                when (currentStep) {
                    is SheetStep.MainActions -> {
                        navigationManager.back()
                    }
                    is SheetStep.PlaylistPicker -> {
                        updateState { copy(step = SheetStep.MainActions) }
                    }
                    is SheetStep.CreatePlaylist -> {
                        updateState { copy(step = SheetStep.PlaylistPicker) }
                    }
                    is SheetStep.RemoveFromPlaylist -> {
                        updateState { copy(step = SheetStep.MainActions) }
                    }
                }
            }
            is QuranSheetEvent.MarkForDeletion -> {
                val bookmarkId = ayatBookmarksMap[event.categoryId]
                if (bookmarkId != null) {
                    Napier.d(tag = TAG_BOOKMARK_FTS) { "Marking bookmark $bookmarkId for deletion" }
                    updateState { copy(showDeleteConfirmDialog = true, bookmarkToDelete = bookmarkId) }
                }
            }
            is QuranSheetEvent.ConfirmDelete -> {
                val bookmarkId = state.value.bookmarkToDelete ?: return
                viewModelScope.launch {
                    Napier.d(tag = TAG_BOOKMARK_FTS) { "Deleting bookmark $bookmarkId" }
                    bookmarkRepository.deleteBookmark(bookmarkId)
                    updateState { copy(showDeleteConfirmDialog = false, bookmarkToDelete = null) }
                    sendEffect(QuranSheetEffect.DismissSheet)
                }
            }
            is QuranSheetEvent.CancelDelete -> {
                updateState { copy(showDeleteConfirmDialog = false, bookmarkToDelete = null) }
            }
            is QuranSheetEvent.OpenDeletePlaylist -> {
                updateState { copy(step = SheetStep.RemoveFromPlaylist) }
            }
        }
    }
}
