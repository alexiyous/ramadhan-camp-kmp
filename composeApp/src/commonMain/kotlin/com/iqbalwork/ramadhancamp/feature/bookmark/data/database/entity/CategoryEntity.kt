package com.iqbalwork.ramadhancamp.feature.bookmark.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "category")
data class CategoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    @ColumnInfo(name = "color", defaultValue = "4294938496")
    val color: Long = 0xFF4ADE80L
)
