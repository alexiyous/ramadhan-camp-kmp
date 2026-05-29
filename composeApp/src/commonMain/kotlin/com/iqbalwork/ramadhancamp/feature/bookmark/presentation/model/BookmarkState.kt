package com.iqbalwork.ramadhancamp.feature.bookmark.presentation.model

import com.iqbalwork.ramadhancamp.feature.bookmark.domain.model.Bookmark
import com.iqbalwork.ramadhancamp.feature.bookmark.domain.model.Category

data class BookmarkState(
    val searchQuery: String = "",
    val selectedCategoryId: Long? = null,
    val categories: List<Category> = emptyList(),
    val bookmarks: List<Bookmark> = emptyList(),
    val isLoading: Boolean = false,
    val categoryToDelete: Category? = null,
    val bookmarkToDelete: Bookmark? = null,
    val isSearchActive: Boolean = false,
    val newestBookmarkId: Long? = null
)
