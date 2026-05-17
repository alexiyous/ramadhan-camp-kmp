package com.iqbalwork.ramadhancamp.feature.bookmark.presentation

import androidx.lifecycle.viewModelScope
import com.iqbalwork.ramadhancamp.feature.bookmark.domain.repository.BookmarkRepository
import com.iqbalwork.ramadhancamp.feature.bookmark.presentation.model.BookmarkEffect
import com.iqbalwork.ramadhancamp.feature.bookmark.presentation.model.BookmarkEvent
import com.iqbalwork.ramadhancamp.feature.bookmark.presentation.model.BookmarkState
import com.iqbalwork.ramadhancamp.feature.quran.presentation.QuranDetailScreenParameters
import com.iqbalwork.ramadhancamp.feature.quran.presentation.route.QuranTab
import com.iqbalwork.ramadhancamp.shared.common.navigation.DialogDestination
import com.iqbalwork.ramadhancamp.shared.common.navigation.NavigationManager
import com.iqbalwork.ramadhancamp.shared.common.navigation.TabDestination
import com.iqbalwork.ramadhancamp.shared.common.ui.BaseViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import com.iqbalwork.ramadhancamp.shared.utils.TAG_BOOKMARK_FTS
import io.github.aakira.napier.Napier

@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
class BookmarkViewModel(
    navigationManager: NavigationManager,
    private val bookmarkRepository: BookmarkRepository,
) : BaseViewModel<Unit, BookmarkState, BookmarkEvent, BookmarkEffect>(
    Unit, BookmarkState(), navigationManager
) {
    private val searchQueryFlow = MutableStateFlow("")

    init {
        loadCategories()
        observeBookmarks()
    }

    private fun loadCategories() {
        bookmarkRepository.getAllCategories()
            .onEach { categories ->
                updateState { copy(categories = categories) }
            }
            .launchIn(viewModelScope)
    }

    private fun observeBookmarks() {
        combine(
            searchQueryFlow.debounce(300),
            state.map { it.selectedCategoryId }.distinctUntilChanged()
        ) { query, categoryId ->
            Napier.d(tag = TAG_BOOKMARK_FTS) { "combine emitted — query=\"$query\", categoryId=$categoryId" }
            query to categoryId
        }
            .flatMapLatest { (query, categoryId) ->
                val route = when {
                    query.isNotBlank() && categoryId != null -> "searchBookmarksByCategory"
                    query.isNotBlank() -> "searchBookmarks"
                    categoryId != null -> "getBookmarksByCategory"
                    else -> "getAllBookmarks"
                }
                Napier.d(tag = TAG_BOOKMARK_FTS) { "flatMapLatest routing to: $route" }
                when {
                    query.isNotBlank() && categoryId != null ->
                        bookmarkRepository.searchBookmarksByCategory(query, categoryId)
                    query.isNotBlank() ->
                        bookmarkRepository.searchBookmarks(query)
                    categoryId != null ->
                        bookmarkRepository.getBookmarksByCategory(categoryId)
                    else -> bookmarkRepository.getAllBookmarks()
                }
            }
            .onEach { bookmarks ->
                Napier.d(tag = TAG_BOOKMARK_FTS) { "onEach received ${bookmarks.size} bookmarks" }
                updateState { copy(bookmarks = bookmarks) }
            }
            .launchIn(viewModelScope)
    }

    override fun handleEvent(event: BookmarkEvent) {
        when (event) {
            is BookmarkEvent.OnSearchQueryChanged -> {
                Napier.d(tag = TAG_BOOKMARK_FTS) { "OnSearchQueryChanged — query=\"${event.query}\"" }
                updateState { copy(searchQuery = event.query) }
                searchQueryFlow.value = event.query
            }
            is BookmarkEvent.OnCategorySelected -> {
                Napier.d(tag = TAG_BOOKMARK_FTS) { "OnCategorySelected — categoryId=${event.categoryId}" }
                updateState { copy(selectedCategoryId = event.categoryId) }
            }
            is BookmarkEvent.OnAddBookmarkClick -> {
                navigationManager.showDialog(DialogDestination.BookmarkCreateCategory)
            }
            is BookmarkEvent.OnDeleteBookmarkClicked -> {
                Napier.d(tag = TAG_BOOKMARK_FTS) { "OnDeleteBookmarkClicked — bookmark id=${event.bookmark.id}" }
                updateState { copy(bookmarkToDelete = event.bookmark) }
            }
            is BookmarkEvent.ConfirmDeleteBookmark -> {
                val bookmark = state.value.bookmarkToDelete ?: return
                Napier.d(tag = TAG_BOOKMARK_FTS) { "ConfirmDeleteBookmark — deleting bookmark id=${bookmark.id}" }
                viewModelScope.launch {
                    bookmarkRepository.deleteBookmark(bookmark.id)
                    updateState { copy(bookmarkToDelete = null) }
                }
            }
            is BookmarkEvent.DismissDeleteBookmark -> {
                updateState { copy(bookmarkToDelete = null) }
            }
            is BookmarkEvent.OnDeleteCategoryClicked -> {
                updateState { copy(categoryToDelete = event.category) }
            }
            is BookmarkEvent.ConfirmDeleteCategory -> {
                val category = state.value.categoryToDelete ?: return
                viewModelScope.launch {
                    bookmarkRepository.deleteCategory(category.id)
                    if (state.value.selectedCategoryId == category.id) {
                        updateState { copy(selectedCategoryId = null) }
                    }
                    updateState { copy(categoryToDelete = null) }
                }
            }
            is BookmarkEvent.DismissDeleteCategory -> {
                updateState { copy(categoryToDelete = null) }
            }
            is BookmarkEvent.OnBookmarkClick -> {
                val bookmark = event.bookmark
                navigationManager.switchTab(QuranTab)
                navigationManager.navigateToInsideTab(
                    TabDestination.QuranDetail(
                        QuranDetailScreenParameters(
                            surahId = bookmark.surahId,
                            scrollToAyat = bookmark.ayatNumber
                        )
                    )
                )
            }
            is BookmarkEvent.OnBackClicked -> {
                navigationManager.back()
            }
            is BookmarkEvent.OnSearchClicked -> {
                Napier.d(tag = TAG_BOOKMARK_FTS) { "OnSearchClicked — entering search mode" }
                updateState { copy(isSearchActive = true) }
            }
            is BookmarkEvent.OnSearchCloseClicked -> {
                Napier.d(tag = TAG_BOOKMARK_FTS) { "OnSearchCloseClicked — clearing search, exiting search mode" }
                updateState { copy(searchQuery = "", isSearchActive = false) }
                searchQueryFlow.value = ""
            }
        }
    }
}