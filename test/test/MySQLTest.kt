package com.iwahara.antenna.ktor.test

abstract class MySQLTest : DataBaseTest {

    protected fun setUpDataBase(url: String, user: String, password: String, database: String) {
        setUpDataBase("com.mysql.jdbc.Driver", url, user, password, database)
    }

    protected fun cleanUpDataBase(url: String, user: String, password: String, database: String) {
        cleanUpDataBase("com.mysql.jdbc.Driver", url, user, password, database)
    }
}