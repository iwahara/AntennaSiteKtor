package com.iwahara.antenna.ktor.repository

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

class SiteRepositoryTest : DataBaseTest() {

    @BeforeTest
    fun setUp() {
        setUpMySQL("jdbc:mysql://localhost:3306", "root", "root", "test")
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
        cleanUpMySQL("jdbc:mysql://localhost:3306", "root", "root", "test")
    }

    @Test
    fun test_find() {
        val db = Database.connect("jdbc:mysql://localhost:3306/test", "com.mysql.jdbc.Driver", "root", "root")
        transaction {
            val repository = SiteRepository()
            val actual = repository.findAll()
            assertEquals(10, actual.size)
            for (i in 0 until 10) {
                val expected = DateTime(2020, 12, 12, 10, 10, 9 - i)
                assertEquals(expected, actual[i].updatedAt)
            }
        }
    }
}