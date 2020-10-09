package com.iwahara.antenna.ktor.database

data class DataBaseConnectionInfo(
    val driver: String,
    val url: String,
    val user: String,
    val password: String
)