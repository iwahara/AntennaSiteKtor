package com.iwahara.antenna.ktor

import com.iwahara.antenna.ktor.database.DataBaseConnectionInfo
import com.iwahara.antenna.ktor.entity.Article
import com.iwahara.antenna.ktor.entity.Site
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.transaction

class Migration(
    private val info: DataBaseConnectionInfo
) {
    fun migrate() {
        Database.connect(driver = info.driver, url = info.url, user = info.user, password = info.password)
        transaction {
            addLogger(StdOutSqlLogger)
            SchemaUtils.createMissingTablesAndColumns(Site, Article)
        }
    }
}