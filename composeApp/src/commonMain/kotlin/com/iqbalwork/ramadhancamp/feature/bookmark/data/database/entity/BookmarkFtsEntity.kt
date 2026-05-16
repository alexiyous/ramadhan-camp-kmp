package com.iqbalwork.ramadhancamp.feature.bookmark.data.database.entity

import androidx.room.Entity
import androidx.room.Fts4
import androidx.room.ColumnInfo

@Fts4(contentEntity = BookmarkEntity::class)
@Entity(tableName = "bookmark_fts")
data class BookmarkFtsEntity(
    @ColumnInfo(name = "ayah_details")
    val ayahDetails: String
)
