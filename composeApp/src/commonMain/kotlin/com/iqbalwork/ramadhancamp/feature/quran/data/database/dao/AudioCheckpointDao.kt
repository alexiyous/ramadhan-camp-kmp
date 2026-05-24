package com.iqbalwork.ramadhancamp.feature.quran.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.iqbalwork.ramadhancamp.feature.quran.data.database.entity.AudioCheckpointEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AudioCheckpointDao {

    @Query("SELECT * FROM audio_checkpoint WHERE surah_id = :surahId")
    fun getCheckpoint(surahId: Int): Flow<AudioCheckpointEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveCheckpoint(entity: AudioCheckpointEntity)

    @Query("DELETE FROM audio_checkpoint WHERE surah_id = :surahId")
    suspend fun deleteCheckpoint(surahId: Int)

    @Query("DELETE FROM audio_checkpoint")
    suspend fun deleteAllCheckpoints()
}
