package com.iwahara.antenna.ktor.repository

import com.iwahara.antenna.ktor.entity.Article
import com.iwahara.antenna.ktor.entity.Site
import com.iwahara.antenna.ktor.test.DataBaseTest
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction
import org.joda.time.DateTime
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class ArticleRepositoryImplTest : DataBaseTest() {

    @BeforeTest
    fun setUp() {
        setUpMySQL("jdbc:mysql://localhost:3306", "root", "root", "test")
    }

    @AfterTest
    fun tearDown() {
        cleanUpMySQL("jdbc:mysql://localhost:3306", "root", "root", "test")
    }

    override fun fixture() {
        Site.insert {
            it[name] = "サイト名"
            it[url] = "http://example.com/url"
            it[feedUrl] = "http://example.com/feed"
            it[articleCount] = 10
            it[updatedAt] = DateTime(2020, 12, 12, 10, 10, 1)
        }
        for (i in 0 until 10) {
            Article.insert {
                it[name] = "記事${i}"
                it[url] = "http://example.com/url${i}"
                it[postDatetime] = DateTime(2020, 12, 12, 10, 10, i)
                it[sortingOrder] = "$i".padStart(32, '0')
                it[viewCount] = i
                it[siteId] = 1
            }
        }
    }

    @Test
    fun test_findBySite() {
        val db = Database.connect("jdbc:mysql://localhost:3306/test", "com.mysql.jdbc.Driver", "root", "root")
        transaction(db) {
            val repository = ArticleRepositoryImpl()
            val actual = repository.findBySite(1, DateTime(2020, 12, 12, 10, 10, 8), 5)
            assertEquals(5, actual.size)

            for (i in 0 until 5) {
                val article = actual[i]
                assertEquals("${8 - i}".padStart(32, '0'), article.sortingOrder)
            }
        }
    }

    @Test
    fun test_findBySiteOffset() {
        val db = Database.connect("jdbc:mysql://localhost:3306/test", "com.mysql.jdbc.Driver", "root", "root")
        transaction(db) {
            val repository = ArticleRepositoryImpl()
            val actual = repository.findBySite(1, DateTime(2020, 12, 12, 10, 10, 8), 5, "6".padStart(32, '0'))
            assertEquals(5, actual.size)

            for (i in 0 until 5) {
                val article = actual[i]
                assertEquals("${5 - i}".padStart(32, '0'), article.sortingOrder)
            }
        }
    }
}