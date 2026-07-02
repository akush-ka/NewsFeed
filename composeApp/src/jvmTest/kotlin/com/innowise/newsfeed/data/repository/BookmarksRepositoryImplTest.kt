package com.innowise.newsfeed.data.repository

import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.innowise.newsfeed.data.local.NewsDatabase
import com.innowise.newsfeed.domain.model.Article
import com.innowise.newsfeed.domain.repository.BookmarksRepository
import com.innowise.newsfeed.domain.repository.LocalNewsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class BookmarksRepositoryImplTest {

    private lateinit var database: NewsDatabase
    private lateinit var localNewsRepository: LocalNewsRepository
    private lateinit var repository: BookmarksRepository

    @BeforeTest
    fun setUp() {
        database = Room.inMemoryDatabaseBuilder<NewsDatabase>()
            .setDriver(BundledSQLiteDriver())
            .setQueryCoroutineContext(Dispatchers.IO)
            .build()
        localNewsRepository = LocalNewsRepositoryImpl(database.articleDao())
        repository = BookmarksRepositoryImpl(database.bookmarkDao())
    }

    @AfterTest
    fun tearDown() {
        database.close()
    }

    @Test
    fun setBookmarkedAddsAndRemovesBookmark() = runBlocking {
        localNewsRepository.saveArticles(listOf(article(1), article(2)))

        repository.setBookmarked(1, true)
        assertEquals(setOf(1L), repository.getBookmarkedIds().first())
        assertEquals(listOf(article(1)), repository.getBookmarkedArticles().first())

        repository.setBookmarked(1, false)
        assertEquals(emptySet(), repository.getBookmarkedIds().first())
    }

    @Test
    fun setBookmarkedTwiceKeepsSingleBookmark() = runBlocking {
        localNewsRepository.saveArticles(listOf(article(1)))

        repository.setBookmarked(1, true)
        repository.setBookmarked(1, true)

        assertEquals(setOf(1L), repository.getBookmarkedIds().first())
        assertEquals(1, repository.getBookmarkedArticles().first().size)
    }

    @Test
    fun bookmarkSurvivesArticleRefresh() = runBlocking {
        localNewsRepository.saveArticles(listOf(article(1)))
        repository.setBookmarked(1, true)

        val refreshed = article(1).copy(title = "Updated title")
        localNewsRepository.saveArticles(listOf(refreshed, article(2)))

        assertEquals(setOf(1L), repository.getBookmarkedIds().first())
        assertEquals(listOf(refreshed), repository.getBookmarkedArticles().first())
    }

    @Test
    fun getBookmarkedArticlesOrdersByLatestBookmarkedFirst() = runBlocking {
        localNewsRepository.saveArticles(listOf(article(1), article(2)))

        repository.setBookmarked(1, true)
        repository.setBookmarked(2, true)

        assertEquals(listOf(article(2), article(1)), repository.getBookmarkedArticles().first())
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
