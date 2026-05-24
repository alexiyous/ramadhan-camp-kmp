package com.iqbalwork.ramadhancamp.feature.quran.data.mapper

import com.iqbalwork.ramadhancamp.feature.quran.data.database.entity.AudioCheckpointEntity
import com.iqbalwork.ramadhancamp.feature.quran.domain.repository.AudioCheckpoint

fun AudioCheckpointEntity.toDomain() = AudioCheckpoint(
    surahId = surahId,
    ayatNumber = ayatNumber,
    seekPositionMs = seekPositionMs,
    timestamp = timestamp
)

fun AudioCheckpoint.toEntity() = AudioCheckpointEntity(
    surahId = surahId,
    ayatNumber = ayatNumber,
    seekPositionMs = seekPositionMs,
    timestamp = timestamp
)
