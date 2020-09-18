package com.iwahara.antenna.ktor

import com.iwahara.antenna.ktor.entity.Article
import com.iwahara.antenna.ktor.entity.Site
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.transaction

class Migration(private val url: String, private val port: String, private val name: String, private val user: String, private val password: String) {
    fun migrate() {
        Database.connect("$url:$port/$name", driver = "com.mysql.jdbc.Driver", user = user, password = password)
        transaction {
            addLogger(StdOutSqlLogger)
            SchemaUtils.createMissingTablesAndColumns(Site, Article)
        }
    }
}