package com.iqbalwork.ramadhancamp.feature.bookmark.domain.repository

import com.iqbalwork.ramadhancamp.feature.bookmark.domain.model.Bookmark
import com.iqbalwork.ramadhancamp.feature.bookmark.domain.model.Category
import kotlinx.coroutines.flow.Flow

interface BookmarkRepository {
    fun getAllBookmarks(): Flow<List<Bookmark>>
    fun getAllCategories(): Flow<List<Category>>
    fun searchBookmarks(query: String): Flow<List<Bookmark>>
    fun getBookmarksByCategory(categoryId: Long): Flow<List<Bookmark>>
    fun searchBookmarksByCategory(query: String, categoryId: Long): Flow<List<Bookmark>>
    suspend fun addBookmark(bookmark: Bookmark): Long
    suspend fun addCategory(category: Category): Long
    suspend fun deleteBookmark(id: Long)
    suspend fun deleteCategory(id: Long)
}