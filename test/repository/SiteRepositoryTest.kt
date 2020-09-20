package com.iwahara.antenna.ktor.repository

import com.iwahara.antenna.ktor.entity.Article
import com.iwahara.antenna.ktor.entity.Site
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction
import org.joda.time.DateTime
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class SiteRepositoryTest {

    @BeforeTest
    fun setUp() {
        Database.connect("jdbc:mysql://localhost:3306/", "com.mysql.jdbc.Driver", "root", "root")
        transaction {
            SchemaUtils.createDatabase("test")
        }
        val db = Database.connect("jdbc:mysql://localhost:3306/test", "com.mysql.jdbc.Driver", "root", "root")
        transaction(db) {
            SchemaUtils.create(Site, Article)
            fixture()
            commit()
        }
    }

    private fun fixture() {
        for (i in 0 until 10) {
            Site.insert {
                it[name] = "サイト名${i}"
                it[url] = "http://example.com/url${i}"
                it[feedUrl] = "http://example.com/feed${i}"
                it[articleCount] = i
                it[updatedAt] = DateTime(2020, 12, 12, 10, 10, 10)
            }
        }


    }

    @AfterTest
    fun tearDown() {
        Database.connect("jdbc:mysql://localhost:3306/", "com.mysql.jdbc.Driver", "root", "root")
        transaction {
            SchemaUtils.dropDatabase("test")
        }
    }

    @Test
    fun test_find() {
        val db = Database.connect("jdbc:mysql://localhost:3306/test", "com.mysql.jdbc.Driver", "root", "root")
        transaction {
            val repository = SiteRepository()
            val actual = repository.find(5)
            assertEquals(5, actual.size)
        }
    }
}