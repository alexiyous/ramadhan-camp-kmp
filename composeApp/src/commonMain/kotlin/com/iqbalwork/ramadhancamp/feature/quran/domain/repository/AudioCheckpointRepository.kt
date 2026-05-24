package com.iqbalwork.ramadhancamp.feature.quran.domain.repository

data class AudioCheckpoint(
    val surahId: Int,
    val ayatNumber: Int,
    val seekPositionMs: Long,
    val timestamp: Long
)

interface AudioCheckpointRepository {
    fun getCheckpoint(surahId: Int): kotlinx.coroutines.flow.Flow<AudioCheckpoint?>
    suspend fun saveCheckpoint(checkpoint: AudioCheckpoint)
    suspend fun deleteCheckpoint(surahId: Int)
}
