package com.iwahara.antenna.ktor.controller

import com.iwahara.antenna.ktor.Clock
import com.iwahara.antenna.ktor.ClockSpecify
import com.iwahara.antenna.ktor.entity.Article
import com.iwahara.antenna.ktor.entity.Site
import com.iwahara.antenna.ktor.module
import com.iwahara.antenna.ktor.test.MySQLTest
import io.ktor.config.*
import io.ktor.http.*
import io.ktor.server.testing.*
import io.ktor.util.*
import org.jetbrains.exposed.sql.insert
import org.joda.time.DateTime

import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class BySiteControllerTest : MySQLTest() {
    private val baseDateTime = DateTime(2020, 10, 10, 13, 55, 33)

    @BeforeTest
    fun setUp() {
        setUpDataBase("jdbc:mysql://localhost:3306", "root", "root", "test")
    }

    @AfterTest
    fun tearDown() {
        cleanUpDataBase("jdbc:mysql://localhost:3306", "root", "root", "test")
    }

    override fun fixture() {
        Site.insert {
            it[name] = "サイト名"
            it[url] = "http://example.com/url"
            it[feedUrl] = "http://example.com/feed"
            it[articleCount] = 10
            it[updatedAt] = baseDateTime
        }
        for (i in 0 until 100) {
            Article.insert {
                it[name] = "記事${i}"
                it[url] = "http://example.com/url${i}"
                it[postDatetime] = baseDateTime.plusMinutes(i)
                it[sortingOrder] = "$i".padStart(32, '0')
                it[viewCount] = i
                it[siteId] = 1
            }
        }
    }

    @KtorExperimentalAPI
    @Test
    fun test() {
        withTestApplication({
            (environment.config as MapApplicationConfig).apply {
                put("antenna.database.url", "jdbc:mysql://localhost:3306/test")
                put("antenna.database.driver", "com.mysql.jdbc.Driver")
                put("antenna.database.user", "root")
                put("antenna.database.password", "root")
            }
            val testModule = org.koin.dsl.module(override = true) {
                factory { ClockSpecify(baseDateTime) as Clock }
            }
            module(testing = true, testModule = testModule)

        }) {

            handleRequest(HttpMethod.Get, "/site/1").apply {
                assertEquals(HttpStatusCode.OK, response.status())
                //TODO: そのうちパースしてテストしたい(JSoupを使うかな…)
                //assertEquals("HELLO WORLD!", response.content)
            }
        }
    }
}