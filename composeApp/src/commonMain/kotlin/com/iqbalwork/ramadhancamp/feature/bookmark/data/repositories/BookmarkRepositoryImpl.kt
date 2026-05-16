package com.iqbalwork.ramadhancamp.feature.bookmark.data.repositories

import com.iqbalwork.ramadhancamp.feature.bookmark.data.database.dao.BookmarkDao
import com.iqbalwork.ramadhancamp.feature.bookmark.data.database.entity.BookmarkEntity
import com.iqbalwork.ramadhancamp.feature.bookmark.data.database.entity.CategoryEntity
import com.iqbalwork.ramadhancamp.feature.bookmark.domain.model.Bookmark
import com.iqbalwork.ramadhancamp.feature.bookmark.domain.model.Category
import com.iqbalwork.ramadhancamp.feature.bookmark.domain.repository.BookmarkRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class BookmarkRepositoryImpl(
    private val dao: BookmarkDao
) : BookmarkRepository {

    override fun getAllBookmarks(): Flow<List<Bookmark>> {
        return dao.getAllBookmarks().map { list ->
            list.map { it.toDomain() }
        }
    }

    override fun getAllCategories(): Flow<List<Category>> {
        return dao.getAllCategories().map { list ->
            list.map { it.toDomain() }
        }
    }

    override fun searchBookmarks(query: String): Flow<List<Bookmark>> {
        return dao.searchBookmarks(query).map { list ->
            list.map { it.toDomain() }
        }
    }

    override suspend fun addBookmark(bookmark: Bookmark): Long {
        return dao.insertBookmark(bookmark.toEntity())
    }

    override suspend fun addCategory(category: Category): Long {
        return dao.insertCategory(category.toEntity())
    }

    override suspend fun deleteBookmark(id: Long) {
        dao.deleteBookmark(id)
    }

    private fun BookmarkEntity.toDomain() = Bookmark(
        id = id,
        ayahDetails = ayahDetails,
        categoryId = categoryId,
        timestamp = timestamp
    )

    private fun Bookmark.toEntity() = BookmarkEntity(
        id = id,
        ayahDetails = ayahDetails,
        categoryId = categoryId,
        timestamp = timestamp
    )

    private fun CategoryEntity.toDomain() = Category(
        id = id,
        name = name
    )

    private fun Category.toEntity() = CategoryEntity(
        id = id,
        name = name
    )
}
