package com.iqbalwork.ramadhancamp.feature.bookmark.presentation

import androidx.lifecycle.viewModelScope
import com.iqbalwork.ramadhancamp.feature.bookmark.domain.repository.BookmarkRepository
import com.iqbalwork.ramadhancamp.feature.bookmark.presentation.model.BookmarkEffect
import com.iqbalwork.ramadhancamp.feature.bookmark.presentation.model.BookmarkEvent
import com.iqbalwork.ramadhancamp.feature.bookmark.presentation.model.BookmarkState
import com.iqbalwork.ramadhancamp.shared.common.navigation.AppNavigationController
import com.iqbalwork.ramadhancamp.shared.common.ui.BaseViewModel
import com.iqbalwork.ramadhancamp.shared.common.ui.UiParams
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch

@OptIn(FlowPreview::class)
class BookmarkViewModel(
    navController: AppNavigationController,
    private val bookmarkRepository: BookmarkRepository
) : BaseViewModel<UiParams.None, BookmarkState, BookmarkEvent, BookmarkEffect>(
    params = UiParams.None,
    initialState = BookmarkState(),
    navigationManager = navController
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
        searchQueryFlow
            .debounce(300)
            .distinctUntilChanged()
            .flatMapLatest { query ->
                bookmarkRepository.searchBookmarks(query)
            }
            .onEach { allBookmarks ->
                val selectedCategoryId = state.value.selectedCategoryId
                val filtered = if (selectedCategoryId != null) {
                    allBookmarks.filter { it.categoryId == selectedCategoryId }
                } else {
                    allBookmarks
                }
                updateState { copy(bookmarks = filtered) }
            }
            .launchIn(viewModelScope)
    }

    override fun handleEvent(event: BookmarkEvent) {
        when (event) {
            is BookmarkEvent.OnSearchQueryChanged -> {
                updateState { copy(searchQuery = event.query) }
                searchQueryFlow.value = event.query
            }
            is BookmarkEvent.OnCategorySelected -> {
                updateState { copy(selectedCategoryId = event.categoryId) }
                // Trigger re-filtering by re-emitting the search query
                searchQueryFlow.value = state.value.searchQuery
            }
            is BookmarkEvent.OnAddBookmarkClick -> {
                // Handle add
            }
            is BookmarkEvent.OnBookmarkClick -> {
                // Handle bookmark click
            }
            is BookmarkEvent.OnPlayClick -> {
                // Handle play click
            }
        }
    }
}
