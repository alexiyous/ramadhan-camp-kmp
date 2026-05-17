package com.iqbalwork.ramadhancamp.shared.common.database

import androidx.room.migration.Migration
import androidx.sqlite.SQLiteConnection
import androidx.sqlite.execSQL

object Migrations {
    val MIGRATION_3_4 = object : Migration(3, 4) {
        override fun migrate(connection: SQLiteConnection) {
            connection.execSQL("ALTER TABLE category ADD COLUMN color INTEGER NOT NULL DEFAULT 4294938496")
        }
    }
}
