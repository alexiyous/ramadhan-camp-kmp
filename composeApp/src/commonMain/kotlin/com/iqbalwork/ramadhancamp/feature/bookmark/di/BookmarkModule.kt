package com.iqbalwork.ramadhancamp.feature.bookmark.di

import com.iqbalwork.ramadhancamp.feature.bookmark.data.repositories.BookmarkRepositoryImpl
import com.iqbalwork.ramadhancamp.feature.bookmark.domain.repository.BookmarkRepository
import com.iqbalwork.ramadhancamp.shared.common.database.AppDatabase
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module

val bookmarkModule = module {
    // Assuming AppDatabase is provided elsewhere (e.g. platform module), we just resolve the DAO here.
    // If we need to provide it here, we would need expect/actual builder, but we leave it to platform agent.
    factory { get<AppDatabase>().bookmarkDao() }
    factoryOf(::BookmarkRepositoryImpl) bind BookmarkRepository::class
}
