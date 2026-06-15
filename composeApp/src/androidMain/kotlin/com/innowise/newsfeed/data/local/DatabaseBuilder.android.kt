package com.innowise.newsfeed.data.local

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

fun getDatabaseBuilder(context: Context): RoomDatabase.Builder<NewsDatabase> {
    val appContext = context.applicationContext
    val dbFile = appContext.getDatabasePath(NewsDatabase.DATABASE_NAME)
    return Room.databaseBuilder<NewsDatabase>(
        context = appContext,
        name = dbFile.absolutePath,
    )
}
