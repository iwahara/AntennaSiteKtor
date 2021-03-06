package com.iwahara.antenna.ktor.repository

import com.iwahara.antenna.ktor.entity.Site
import com.iwahara.antenna.ktor.test.MySQLTest
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction
import org.joda.time.DateTime
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class SiteRepositoryImplTest : MySQLTest() {

    @BeforeTest
    fun setUp() {
        setUpDataBase("jdbc:mysql://localhost:3306", "root", "root", "test")
    }

    override fun fixture() {
        for (i in 0 until 10) {
            Site.insert {
                it[name] = "サイト名${i}"
                it[url] = "http://example.com/url${i}"
                it[feedUrl] = "http://example.com/feed${i}"
                it[articleCount] = i
                it[updatedAt] = DateTime(2020, 12, 12, 10, 10, i)
            }
        }
    }

    @AfterTest
    fun tearDown() {
        cleanUpDataBase("jdbc:mysql://localhost:3306", "root", "root", "test")
    }

    @Test
    fun test_find() {
        val db = Database.connect("jdbc:mysql://localhost:3306/test", "com.mysql.jdbc.Driver", "root", "root")
        transaction {
            val repository = SiteRepositoryImpl()
            val actual = repository.findAll()
            assertEquals(10, actual.size)
            for (i in 0 until 10) {
                val expected = DateTime(2020, 12, 12, 10, 10, 9 - i)
                assertEquals(expected, actual[i].updatedAt)
            }
        }
    }

    @Test
    fun test_findById() {
        val db = Database.connect("jdbc:mysql://localhost:3306/test", "com.mysql.jdbc.Driver", "root", "root")
        transaction(db) {
            val repository = SiteRepositoryImpl()
            val actual = repository.findById(1)

            assertEquals("サイト名0", actual.name)
            assertEquals("http://example.com/url0", actual.url)
            assertEquals("http://example.com/feed0", actual.feedUrl)
            assertEquals(0, actual.articleCount)
            assertEquals(DateTime(2020, 12, 12, 10, 10, 0), actual.updatedAt)
        }
    }
}