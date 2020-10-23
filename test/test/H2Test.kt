package com.iwahara.antenna.ktor.test

abstract class H2Test : DataBaseTest() {
    protected fun setUpDataBase(url: String, database: String) {
        setUpDataBase("org.h2.Driver", url, "", "", database)
    }

    protected fun cleanUpDataBase(url: String, database: String) {
        cleanUpDataBase("org.h2.Driver", url, "", "", database)
    }
}