package com.iwahara.antenna.ktor.database

data class DataBaseConnectionInfo(
    val driver: String,
    val url: String,
    val port: String,
    val name: String,
    val user: String,
    val password: String
)