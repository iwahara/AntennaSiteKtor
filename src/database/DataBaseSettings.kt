package com.iwahara.antenna.ktor.database

import org.jetbrains.exposed.sql.Database

class DataBaseSettings(info: DataBaseConnectionInfo) {
    val db by lazy {
        Database.connect(
            driver = info.driver,
            url = info.url,
            user = info.user,
            password = info.password
        )
    }
}