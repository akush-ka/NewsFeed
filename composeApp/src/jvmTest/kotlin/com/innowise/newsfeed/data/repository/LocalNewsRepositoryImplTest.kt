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
        val older = article(1, publishedTimestamp = "2026-01-01T10:00:00Z")
        val newer = article(2, publishedTimestamp = "2026-02-01T10:00:00Z")

        repository.saveArticles(listOf(older, newer))

        assertEquals(listOf(older, newer), repository.getArticles().first().sortedBy { it.id })
    }

    @Test
    fun getArticlesOrdersByPublishedTimestampDesc() = runBlocking {
        val older = article(1, publishedTimestamp = "2026-01-01T10:00:00Z")
        val newest = article(2, publishedTimestamp = "2026-03-01T10:00:00Z")
        val middle = article(3, publishedTimestamp = "2026-02-01T10:00:00Z")

        repository.saveArticles(listOf(older, newest, middle))

        assertEquals(listOf(newest, middle, older), repository.getArticles().first())
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

    private fun article(
        id: Long,
        publishedTimestamp: String = "",
    ) = Article(
        id = id,
        title = "Title $id",
        description = "Description $id",
        url = "https://dev.to/$id",
        coverImageUrl = "",
        publishedTimestamp = publishedTimestamp,
        readingTimeMinutes = 0,
        authorName = "Author $id",
        tags = "kotlin",
    )
}
