package com.innowise.newsfeed.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.innowise.newsfeed.data.local.entity.ArticleEntity
import com.innowise.newsfeed.data.local.entity.BookmarkEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BookmarkDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertBookmark(bookmark: BookmarkEntity)

    @Query("DELETE FROM bookmarks WHERE articleId = :articleId")
    suspend fun deleteBookmark(articleId: Long)

    @Query(
        "SELECT articles.* FROM articles " +
            "INNER JOIN bookmarks ON articles.id = bookmarks.articleId " +
            "ORDER BY bookmarks.id DESC",
    )
    fun getBookmarkedArticles(): Flow<List<ArticleEntity>>

    @Query("SELECT articleId FROM bookmarks")
    fun getBookmarkedIds(): Flow<List<Long>>
}
