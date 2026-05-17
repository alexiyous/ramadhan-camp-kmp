package com.iqbalwork.ramadhancamp.feature.bookmark.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.iqbalwork.ramadhancamp.feature.bookmark.data.database.entity.BookmarkEntity
import com.iqbalwork.ramadhancamp.feature.bookmark.data.database.entity.CategoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BookmarkDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBookmark(bookmark: BookmarkEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory(category: CategoryEntity): Long

    @Query("SELECT * FROM bookmark ORDER BY timestamp DESC")
    fun getAllBookmarks(): Flow<List<BookmarkEntity>>

    @Query("SELECT * FROM category")
    fun getAllCategories(): Flow<List<CategoryEntity>>

    @Query("SELECT * FROM category")
    suspend fun getAllCategoriesList(): List<CategoryEntity>

    @Query("SELECT b.* FROM bookmark b WHERE b.id IN (SELECT rowid FROM bookmark_fts WHERE bookmark_fts MATCH :query)")
    fun searchBookmarks(query: String): Flow<List<BookmarkEntity>>

    @Query("SELECT * FROM bookmark WHERE category_id = :categoryId ORDER BY timestamp DESC")
    fun getBookmarksByCategory(categoryId: Long): Flow<List<BookmarkEntity>>

    @Query("SELECT b.* FROM bookmark b WHERE b.id IN (SELECT rowid FROM bookmark_fts WHERE bookmark_fts MATCH :query) AND b.category_id = :categoryId")
    fun searchBookmarksByCategory(query: String, categoryId: Long): Flow<List<BookmarkEntity>>

    @Query("SELECT * FROM bookmark WHERE surah_id = :surahId AND ayat_number = :ayatNumber")
    fun getBookmarksBySurahAndAyat(surahId: Int, ayatNumber: Int): Flow<List<BookmarkEntity>>

    @Query("SELECT EXISTS(SELECT 1 FROM bookmark WHERE surah_id = :surahId AND ayat_number = :ayatNumber)")
    fun isAyatBookmarked(surahId: Int, ayatNumber: Int): Flow<Boolean>

    @Query("DELETE FROM bookmark WHERE id = :id")
    suspend fun deleteBookmark(id: Long)

    @Query("DELETE FROM category WHERE id = :id")
    suspend fun deleteCategory(id: Long)
}
