package com.iwahara.antenna.ktor.controller

import com.iwahara.antenna.ktor.Clock
import com.iwahara.antenna.ktor.ClockSpecify
import com.iwahara.antenna.ktor.entity.Article
import com.iwahara.antenna.ktor.entity.Site
import com.iwahara.antenna.ktor.module
import com.iwahara.antenna.ktor.test.DataBaseTest
import io.ktor.config.*
import io.ktor.http.*
import io.ktor.server.testing.*
import io.ktor.util.*
import org.jetbrains.exposed.sql.insert
import org.joda.time.DateTime
import org.koin.dsl.module
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class SiteListControllerTest : DataBaseTest() {
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
            it[updatedAt] = DateTime.now()//DateTime(2020, 12, 12, 10, 10, 1)
        }
        for (i in 0 until 10) {
            Article.insert {
                it[name] = "記事${i}"
                it[url] = "http://example.com/url${i}"
                it[postDatetime] = DateTime.now()//DateTime(2020, 12, 12, 10, 10, i)
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
            val testModule = module(override = true) {
                factory { ClockSpecify(DateTime(2020, 1, 1, 12, 12, 12)) as Clock }
            }
            module(testing = true, testModule = testModule)

        }) {

            handleRequest(HttpMethod.Get, "/site.html").apply {
                assertEquals(HttpStatusCode.OK, response.status())
                assertEquals("HELLO WORLD!", response.content)
            }
        }
    }
}