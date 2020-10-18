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
    private val baseDateTime = DateTime.now()

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
            it[updatedAt] = DateTime.now()
        }
        for (i in 0 until 10) {
            Article.insert {
                it[name] = "記事${i}"
                it[url] = "http://example.com/url${i}"
                it[postDatetime] = DateTime(
                        baseDateTime.year,
                        baseDateTime.monthOfYear,
                        baseDateTime.dayOfMonth,
                        baseDateTime.hourOfDay,
                        baseDateTime.minuteOfHour - i
                )
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
                factory { ClockSpecify(baseDateTime) as Clock }
            }
            module(testing = true, testModule = testModule)

        }) {

            handleRequest(HttpMethod.Get, "/site/list").apply {
                assertEquals(HttpStatusCode.OK, response.status())
                //TODO: そのうちパースしてテストしたい(JSoupを使うかな…)
                //assertEquals("HELLO WORLD!", response.content)
            }
        }
    }
}