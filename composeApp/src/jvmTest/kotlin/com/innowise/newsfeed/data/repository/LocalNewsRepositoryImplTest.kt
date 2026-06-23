package com.innowise.newsfeed.data.repository

import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.innowise.newsfeed.data.local.NewsDatabase
import com.innowise.newsfeed.domain.model.Article
import com.innowise.newsfeed.domain.repository.LocalNewsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class LocalNewsRepositoryImplTest {

    private lateinit var database: NewsDatabase
    private lateinit var repository: LocalNewsRepository

    @BeforeTest
    fun setUp() {
        database = Room.inMemoryDatabaseBuilder<NewsDatabase>()
            .setDriver(BundledSQLiteDriver())
            .setQueryCoroutineContext(Dispatchers.IO)
            .build()
        repository = LocalNewsRepositoryImpl(database.articleDao())
    }

    @AfterTest
    fun tearDown() {
        database.close()
    }

    @Test
    fun saveArticlesThenGetArticlesEmitsSaved() = runBlocking {
        val articles = listOf(article(1), article(2))

        repository.saveArticles(articles)

        assertEquals(articles, repository.getArticles().first())
    }

    @Test
    fun getArticleReturnsSavedArticle() = runBlocking {
        repository.saveArticles(listOf(article(7)))

        assertEquals(article(7), repository.getArticle(7))
    }

    @Test
    fun getArticleMissingIdReturnsNull() = runBlocking {
        assertNull(repository.getArticle(1))
    }

    private fun article(id: Long) = Article(
        id = id,
        title = "Title $id",
        description = "Description $id",
        url = "https://dev.to/$id",
        coverImageUrl = "",
        publishedTimestamp = "",
        readingTimeMinutes = 0,
        authorName = "Author $id",
        tags = "kotlin",
    )
}
