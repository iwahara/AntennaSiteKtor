package com.iwahara.antenna.ktor

import io.ktor.config.*
import io.ktor.http.*
import io.ktor.server.testing.*
import io.ktor.util.*
import kotlin.test.Test
import kotlin.test.assertEquals

class ApplicationTest {
    @KtorExperimentalAPI
    @Test
    fun testRoot() {
        withTestApplication({
            (environment.config as MapApplicationConfig).apply {
                put("antenna.database.url", "jdbc:mysql://localhost")
                put("antenna.database.port", "3306")
                put("antenna.database.name", "test")
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
}
