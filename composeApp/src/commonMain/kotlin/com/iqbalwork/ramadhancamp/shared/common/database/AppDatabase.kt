package com.iqbalwork.ramadhancamp.shared.common.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.iqbalwork.ramadhancamp.feature.bookmark.data.database.entity.BookmarkEntity
import com.iqbalwork.ramadhancamp.feature.bookmark.data.database.entity.BookmarkFtsEntity
import com.iqbalwork.ramadhancamp.feature.bookmark.data.database.entity.CategoryEntity
import com.iqbalwork.ramadhancamp.feature.bookmark.data.database.dao.BookmarkDao

@Database(
    entities = [
        BookmarkEntity::class,
        BookmarkFtsEntity::class,
        CategoryEntity::class
    ],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun bookmarkDao(): BookmarkDao
}
