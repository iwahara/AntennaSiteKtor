package com.iwahara.antenna.ktor

import com.iwahara.antenna.ktor.test.MySQLTest
import io.ktor.config.*
import io.ktor.http.*
import io.ktor.server.testing.*
import io.ktor.util.*
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class ApplicationTest : MySQLTest() {

    @BeforeTest
    fun setUp() {
        setUpDataBase("jdbc:mysql://localhost:3306", "root", "root", "test")
    }

    @AfterTest
    fun tearDown() {
        cleanUpDataBase("jdbc:mysql://localhost:3306", "root", "root", "test")
    }

    @KtorExperimentalAPI
    @Test
    fun testRoot() {
        withTestApplication({
            (environment.config as MapApplicationConfig).apply {
                put("antenna.database.url", "jdbc:mysql://localhost:3306/test")
                put("antenna.database.driver", "com.mysql.jdbc.Driver")
                put("antenna.database.user", "root")
                put("antenna.database.password", "root")
            }
            module(testing = true)
        }) {
            handleRequest(HttpMethod.Get, "/").apply {
                assertEquals(HttpStatusCode.OK, response.status())
                assertEquals("HELLO WORLD!", response.content)
            }
        }
    }

    override fun fixture() {

    }
}
