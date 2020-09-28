package com.iwahara.antenna.ktor.test

import com.iwahara.antenna.ktor.entity.Article
import com.iwahara.antenna.ktor.entity.Site
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

abstract class DataBaseTest {

    private fun setUpDataBase(driver: String, url: String, user: String, password: String, database: String) {
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

    protected fun setUpMySQL(url: String, user: String, password: String, database: String) {
        setUpDataBase("com.mysql.jdbc.Driver", url, user, password, database)
    }

    private fun cleanUpDataBase(driver: String, url: String, user: String, password: String, database: String) {
        Database.connect(url, driver, user, password)
        transaction {
            SchemaUtils.dropDatabase(database)
        }
    }

    protected fun cleanUpMySQL(url: String, user: String, password: String, database: String) {
        cleanUpDataBase("com.mysql.jdbc.Driver", url, user, password, database)
    }


    abstract fun fixture()
}