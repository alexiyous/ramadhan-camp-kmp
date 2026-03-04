package com.iqbalwork.ramadhancamp.feature.home.domain.usecase

import com.iqbalwork.ramadhancamp.feature.home.domain.model.LastSurahRead
import com.iqbalwork.ramadhancamp.feature.home.domain.repository.HomeRepository

fun interface UpdateLastSurahRead {
    suspend operator fun invoke(lastSurahRead: LastSurahRead)
}

private class UpdateLastSurahReadImpl(
    private val homeRepository: HomeRepository
): UpdateLastSurahRead {
    override suspend fun invoke(lastSurahRead: LastSurahRead) {
        homeRepository.saveLastReadSurah(lastSurahRead)
    }
}