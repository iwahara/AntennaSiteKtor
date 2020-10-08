package com.iwahara.antenna.ktor

import org.jetbrains.exposed.sql.Database

class DataBaseSettings(info: DataBaseConnectionInfo) {
    val db by lazy {
        Database.connect(
            "${info.url}:${info.port}/${info.name}",
            driver = "com.mysql.jdbc.Driver",
            user = info.user,
            password = info.password
        )
    }
}