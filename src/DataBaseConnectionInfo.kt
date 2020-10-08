package com.iwahara.antenna.ktor

data class DataBaseConnectionInfo(
    val url: String,
    val port: String,
    val name: String,
    val user: String,
    val password: String
)