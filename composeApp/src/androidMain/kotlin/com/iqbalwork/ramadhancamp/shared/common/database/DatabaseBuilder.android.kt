package com.iqbalwork.ramadhancamp.shared.common.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import org.koin.java.KoinJavaComponent.getKoin

actual fun getDatabaseBuilder(): RoomDatabase.Builder<AppDatabase> {
    val context = getKoin().get<Context>()
    val dbFile = context.getDatabasePath("ramadhan.db")
    return Room.databaseBuilder<AppDatabase>(
        context = context,
        name = dbFile.absolutePath
    )
}
