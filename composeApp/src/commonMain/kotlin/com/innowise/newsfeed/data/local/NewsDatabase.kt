package com.innowise.newsfeed.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.innowise.newsfeed.data.local.dao.ArticleDao
import com.innowise.newsfeed.data.local.entity.ArticleEntity

@Database(entities = [ArticleEntity::class], version = 1, exportSchema = false)
abstract class NewsDatabase : RoomDatabase() {
    abstract fun articleDao(): ArticleDao

    companion object {
        const val DATABASE_NAME = "news.db"
    }
}
