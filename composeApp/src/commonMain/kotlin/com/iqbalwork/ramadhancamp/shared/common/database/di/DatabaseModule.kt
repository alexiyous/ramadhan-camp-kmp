package com.iqbalwork.ramadhancamp.shared.common.database.di

import com.iqbalwork.ramadhancamp.shared.common.database.AppDatabase
import com.iqbalwork.ramadhancamp.shared.common.database.getDatabaseBuilder
import com.iqbalwork.ramadhancamp.shared.common.database.Migrations.MIGRATION_3_4
import org.koin.dsl.module

val databaseModule = module {
    single<AppDatabase> {
        getDatabaseBuilder()
            .addMigrations(MIGRATION_3_4)
            .build()
    }
}
