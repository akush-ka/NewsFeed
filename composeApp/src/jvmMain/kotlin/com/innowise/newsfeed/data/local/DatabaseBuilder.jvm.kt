package com.innowise.newsfeed.data.local

import androidx.room.Room
import androidx.room.RoomDatabase
import java.io.File

fun getDatabaseBuilder(): RoomDatabase.Builder<NewsDatabase> {
    val userHome = System.getProperty("user.home") ?: "."
    val appDir = File(userHome, ".newsfeed").apply { mkdirs() }
    val dbFile = File(appDir, NewsDatabase.DATABASE_NAME)
    return Room.databaseBuilder<NewsDatabase>(
        name = dbFile.absolutePath,
    )
}
