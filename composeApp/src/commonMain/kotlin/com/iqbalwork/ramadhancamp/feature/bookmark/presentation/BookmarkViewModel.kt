package com.iqbalwork.ramadhancamp.feature.bookmark.presentation

import androidx.lifecycle.viewModelScope
import com.iqbalwork.ramadhancamp.feature.bookmark.domain.repository.BookmarkRepository
import com.iqbalwork.ramadhancamp.feature.bookmark.presentation.model.BookmarkEffect
import com.iqbalwork.ramadhancamp.feature.bookmark.presentation.model.BookmarkEvent
import com.iqbalwork.ramadhancamp.feature.bookmark.presentation.model.BookmarkState
import com.iqbalwork.ramadhancamp.feature.home.presentation.route.HomeTab
import com.iqbalwork.ramadhancamp.shared.common.navigation.DialogDestination
import com.iqbalwork.ramadhancamp.shared.common.navigation.NavigationManager
import com.iqbalwork.ramadhancamp.shared.common.navigation.NavigationResult
import com.iqbalwork.ramadhancamp.shared.common.navigation.NavigationResultData
import com.iqbalwork.ramadhancamp.shared.common.navigation.TabDestination
import com.iqbalwork.ramadhancamp.shared.common.navigation.TextResult
import com.iqbalwork.ramadhancamp.shared.common.ui.BaseViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch

@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
class BookmarkViewModel(
    navigationManager: NavigationManager,
    private val bookmarkRepository: BookmarkRepository
) : BaseViewModel<Unit, BookmarkState, BookmarkEvent, BookmarkEffect>(
    Unit, BookmarkState(), navigationManager,
    resultKeys = arrayOf("bookmark_result")
) {
    private val searchQueryFlow = MutableStateFlow("")

    private val _lastResult = MutableStateFlow<String?>(null)
    val lastResult: StateFlow<String?> = _lastResult.asStateFlow()

    init {
        loadCategories()
        observeBookmarks()
    }

    override fun navigationResultSuccess(key: String, data: NavigationResultData?) {
        super.navigationResultSuccess(key, data)
        if (key == "bookmark_result") {
            _lastResult.value = (data as? TextResult)?.text
        }
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

    fun navigateToDetail() {
        navigationManager.navigateToInsideTab(TabDestination.BookmarkDetail)
    }

    fun replaceWithDetail() {
        navigationManager.navigateToInsideTab(TabDestination.BookmarkDetail, withReplace = true)
    }

    fun switchToHome() {
        navigationManager.switchTab(HomeTab)
    }

    fun showBookmarkSheet() {
        navigationManager.showDialog(DialogDestination.BookmarkSheet)
    }

    fun navigateToSubDetail() {
        navigationManager.navigateToInsideTab(TabDestination.BookmarkSubDetail)
    }

    fun back() {
        navigationManager.back()
    }

    fun backWithResult() {
        navigationManager.back(NavigationResult.Success("bookmark_result", TextResult("Hello from Detail!")))
    }

    fun backToMain() {
        navigationManager.backToScreen(TabDestination.BookmarkMain)
    }

    fun backToMainWithResult() {
        navigationManager.backToScreen(TabDestination.BookmarkMain, NavigationResult.Success("bookmark_result", TextResult("Hello from SubDetail!")))
    }
}
