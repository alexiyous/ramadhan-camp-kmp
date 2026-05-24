package com.iqbalwork.ramadhancamp.feature.quran.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "audio_checkpoint")
data class AudioCheckpointEntity(
    @PrimaryKey
    @ColumnInfo(name = "surah_id")
    val surahId: Int,
    @ColumnInfo(name = "ayat_number")
    val ayatNumber: Int,
    @ColumnInfo(name = "seek_position_ms")
    val seekPositionMs: Long,
    @ColumnInfo(name = "timestamp")
    val timestamp: Long
)
