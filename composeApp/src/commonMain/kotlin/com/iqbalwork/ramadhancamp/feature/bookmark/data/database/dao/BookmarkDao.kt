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

    @Query("SELECT b.* FROM bookmark b JOIN bookmark_fts fts ON b.id = fts.rowid WHERE fts.ayah_details MATCH :query")
    fun searchBookmarks(query: String): Flow<List<BookmarkEntity>>

    @Query("DELETE FROM bookmark WHERE id = :id")
    suspend fun deleteBookmark(id: Long)
}
