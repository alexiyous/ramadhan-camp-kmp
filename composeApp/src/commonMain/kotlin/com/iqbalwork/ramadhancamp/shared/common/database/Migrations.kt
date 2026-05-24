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

    val MIGRATION_4_5 = object : Migration(4, 5) {
        override fun migrate(connection: SQLiteConnection) {
            connection.execSQL(
                """
                CREATE TABLE IF NOT EXISTS audio_checkpoint (
                    surah_id INTEGER NOT NULL,
                    ayat_number INTEGER NOT NULL,
                    seek_position_ms INTEGER NOT NULL,
                    timestamp INTEGER NOT NULL,
                    PRIMARY KEY (surah_id)
                )
                """.trimIndent()
            )
        }
    }
}
