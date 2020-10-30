package com.iwahara.antenna.ktor.test

import com.iwahara.antenna.ktor.entity.Article
import com.iwahara.antenna.ktor.entity.Site
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

interface DataBaseTest {

    fun setUpDataBase(driver: String, url: String, user: String, password: String, database: String) {
        Database.connect(url, driver, user, password)
        transaction {
            SchemaUtils.createDatabase(database)
        }
        val db = Database.connect("$url/$database", driver, user, password)
        transaction(db) {
            SchemaUtils.create(Site, Article)
            fixture()
            commit()
        }
    }

    fun cleanUpDataBase(driver: String, url: String, user: String, password: String, database: String) {
        Database.connect(url, driver, user, password)
        transaction {
            SchemaUtils.dropDatabase(database)
        }
    }


    fun fixture()
}