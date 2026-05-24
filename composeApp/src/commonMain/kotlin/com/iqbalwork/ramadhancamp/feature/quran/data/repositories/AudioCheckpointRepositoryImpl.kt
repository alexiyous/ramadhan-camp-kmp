package com.iqbalwork.ramadhancamp.feature.quran.data.repositories

import com.iqbalwork.ramadhancamp.feature.quran.data.database.dao.AudioCheckpointDao
import com.iqbalwork.ramadhancamp.feature.quran.data.mapper.toDomain
import com.iqbalwork.ramadhancamp.feature.quran.data.mapper.toEntity
import com.iqbalwork.ramadhancamp.feature.quran.domain.repository.AudioCheckpoint
import com.iqbalwork.ramadhancamp.feature.quran.domain.repository.AudioCheckpointRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AudioCheckpointRepositoryImpl(
    private val dao: AudioCheckpointDao
) : AudioCheckpointRepository {

    override fun getCheckpoint(surahId: Int): Flow<AudioCheckpoint?> {
        return dao.getCheckpoint(surahId).map { it?.toDomain() }
    }

    override suspend fun saveCheckpoint(checkpoint: AudioCheckpoint) {
        dao.saveCheckpoint(checkpoint.toEntity())
    }

    override suspend fun deleteCheckpoint(surahId: Int) {
        dao.deleteCheckpoint(surahId)
    }
}
