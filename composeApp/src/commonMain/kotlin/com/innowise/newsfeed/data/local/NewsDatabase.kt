package com.innowise.newsfeed.data.local

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.innowise.newsfeed.data.local.dao.ArticleDao
import com.innowise.newsfeed.data.local.dao.BookmarkDao
import com.innowise.newsfeed.data.local.entity.ArticleEntity
import com.innowise.newsfeed.data.local.entity.BookmarkEntity
import kotlinx.coroutines.Dispatchers

@Database(entities = [ArticleEntity::class, BookmarkEntity::class], version = 1, exportSchema = false)
@ConstructedBy(NewsDatabaseConstructor::class)
abstract class NewsDatabase : RoomDatabase() {
    abstract fun articleDao(): ArticleDao

    abstract fun bookmarkDao(): BookmarkDao

    companion object {
        const val DATABASE_NAME = "news.db"
    }
}

expect object NewsDatabaseConstructor : RoomDatabaseConstructor<NewsDatabase>

fun getNewsDatabase(builder: RoomDatabase.Builder<NewsDatabase>): NewsDatabase =
    builder
        .setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(Dispatchers.IO)
        .build()
