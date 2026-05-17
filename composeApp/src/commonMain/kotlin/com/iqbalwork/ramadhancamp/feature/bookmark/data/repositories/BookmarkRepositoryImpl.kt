package com.iqbalwork.ramadhancamp.feature.bookmark.data.repositories

import com.iqbalwork.ramadhancamp.feature.bookmark.data.database.dao.BookmarkDao
import com.iqbalwork.ramadhancamp.feature.bookmark.data.database.entity.BookmarkEntity
import com.iqbalwork.ramadhancamp.feature.bookmark.data.database.entity.CategoryEntity
import com.iqbalwork.ramadhancamp.feature.bookmark.domain.model.Bookmark
import com.iqbalwork.ramadhancamp.feature.bookmark.domain.model.Category
import com.iqbalwork.ramadhancamp.feature.bookmark.domain.model.CategoryColorPalette
import com.iqbalwork.ramadhancamp.feature.bookmark.domain.repository.BookmarkRepository
import com.iqbalwork.ramadhancamp.shared.utils.TAG_BOOKMARK_FTS
import io.github.aakira.napier.Napier
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class BookmarkRepositoryImpl(
    private val dao: BookmarkDao
) : BookmarkRepository {

    override fun getAllBookmarks(): Flow<List<Bookmark>> {
        Napier.d(tag = TAG_BOOKMARK_FTS) { "getAllBookmarks() — fetching all bookmarks" }
        return dao.getAllBookmarks().map { list ->
            Napier.d(tag = TAG_BOOKMARK_FTS) { "getAllBookmarks() — found ${list.size} bookmarks" }
            list.map { it.toDomain() }
        }
    }

    override fun getAllCategories(): Flow<List<Category>> {
        Napier.d(tag = TAG_BOOKMARK_FTS) { "getAllCategories() — fetching all categories" }
        return dao.getAllCategories().map { list ->
            Napier.d(tag = TAG_BOOKMARK_FTS) { "getAllCategories() — found ${list.size} categories" }
            list.map { it.toDomain() }
        }
    }

    override fun searchBookmarks(query: String): Flow<List<Bookmark>> {
        Napier.d(tag = TAG_BOOKMARK_FTS) { "searchBookmarks() called — query=\"$query\"" }
        if (query.isBlank()) {
            Napier.d(tag = TAG_BOOKMARK_FTS) { "searchBookmarks() — query is blank, falling back to getAllBookmarks()" }
            return getAllBookmarks()
        }
        Napier.d(tag = TAG_BOOKMARK_FTS) { "searchBookmarks() — delegating to dao.searchBookmarks(\"$query\")" }
        return dao.searchBookmarks(query).map { list ->
            Napier.d(tag = TAG_BOOKMARK_FTS) { "searchBookmarks(\"$query\") — FTS returned ${list.size} results" }
            list.map { it.toDomain() }
        }
    }

    override fun getBookmarksByCategory(categoryId: Long): Flow<List<Bookmark>> {
        Napier.d(tag = TAG_BOOKMARK_FTS) { "getBookmarksByCategory() called — categoryId=$categoryId" }
        return dao.getBookmarksByCategory(categoryId).map { list ->
            Napier.d(tag = TAG_BOOKMARK_FTS) { "getBookmarksByCategory($categoryId) — found ${list.size} bookmarks" }
            list.map { it.toDomain() }
        }
    }

    override fun searchBookmarksByCategory(query: String, categoryId: Long): Flow<List<Bookmark>> {
        Napier.d(tag = TAG_BOOKMARK_FTS) { "searchBookmarksByCategory() called — query=\"$query\", categoryId=$categoryId" }
        return dao.searchBookmarksByCategory(query, categoryId).map { list ->
            Napier.d(tag = TAG_BOOKMARK_FTS) { "searchBookmarksByCategory(\"$query\", $categoryId) — FTS returned ${list.size} results" }
            list.map { it.toDomain() }
        }
    }

    override fun getBookmarksBySurahAndAyat(surahId: Int, ayatNumber: Int): Flow<List<Bookmark>> {
        Napier.d(tag = TAG_BOOKMARK_FTS) { "getBookmarksBySurahAndAyat() — surahId=$surahId, ayatNumber=$ayatNumber" }
        return dao.getBookmarksBySurahAndAyat(surahId, ayatNumber).map { list ->
            Napier.d(tag = TAG_BOOKMARK_FTS) { "getBookmarksBySurahAndAyat($surahId, $ayatNumber) — found ${list.size} bookmarks" }
            list.map { it.toDomain() }
        }
    }

    override fun isAyatBookmarked(surahId: Int, ayatNumber: Int): Flow<Boolean> {
        Napier.d(tag = TAG_BOOKMARK_FTS) { "isAyatBookmarked() — surahId=$surahId, ayatNumber=$ayatNumber" }
        return dao.isAyatBookmarked(surahId, ayatNumber)
    }

    override suspend fun addBookmark(bookmark: Bookmark): Long {
        Napier.d(tag = TAG_BOOKMARK_FTS) { "addBookmark() called — id=${bookmark.id}, surahName=${bookmark.surahName}, ayatNumber=${bookmark.ayatNumber}" }
        val result = dao.insertBookmark(bookmark.toEntity())
        Napier.d(tag = TAG_BOOKMARK_FTS) { "addBookmark() — inserted with id=$result" }
        return result
    }

    override suspend fun addCategory(category: Category): Long {
        Napier.d(tag = TAG_BOOKMARK_FTS) { "addCategory() called — name=\"${category.name}\"" }
        val existingCount = dao.getAllCategoriesList().size
        val assignedColor = if (category.color == 0L || category.color == 0xFF4ADE80L) {
            // Auto-assign color for new categories unless a specific non-default color was chosen
            CategoryColorPalette.colorForIndex(existingCount)
        } else {
            category.color
        }
        val categoryWithColor = category.copy(color = assignedColor)
        val result = dao.insertCategory(categoryWithColor.toEntity())
        Napier.d(tag = TAG_BOOKMARK_FTS) { "addCategory() — inserted with id=$result, color=${assignedColor.toString(16)}" }
        return result
    }

    override suspend fun deleteBookmark(id: Long) {
        Napier.d(tag = TAG_BOOKMARK_FTS) { "deleteBookmark() called — id=$id" }
        dao.deleteBookmark(id)
        Napier.d(tag = TAG_BOOKMARK_FTS) { "deleteBookmark($id) — completed" }
    }

    override suspend fun deleteCategory(id: Long) {
        Napier.d(tag = TAG_BOOKMARK_FTS) { "deleteCategory() called — id=$id" }
        dao.deleteCategory(id)
        Napier.d(tag = TAG_BOOKMARK_FTS) { "deleteCategory($id) — completed" }
    }

    private fun BookmarkEntity.toDomain() = Bookmark(
        id = id,
        ayahDetails = ayahDetails,
        categoryId = categoryId,
        timestamp = timestamp,
        surahId = surahId,
        ayatNumber = ayatNumber,
        surahName = surahName,
        audioUrl = audioUrl,
        teksArab = teksArab,
        teksIndonesia = teksIndonesia
    )

    private fun Bookmark.toEntity() = BookmarkEntity(
        id = id,
        ayahDetails = ayahDetails,
        categoryId = categoryId,
        timestamp = timestamp,
        surahId = surahId,
        ayatNumber = ayatNumber,
        surahName = surahName,
        audioUrl = audioUrl,
        teksArab = teksArab,
        teksIndonesia = teksIndonesia
    )

    private fun CategoryEntity.toDomain() = Category(
        id = id,
        name = name,
        color = color
    )

    private fun Category.toEntity() = CategoryEntity(
        id = id,
        name = name,
        color = color
    )
}
