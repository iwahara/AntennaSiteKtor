package com.iwahara.antenna.ktor.model.site_list

import org.joda.time.DateTime

interface SiteListRepository {
    data class Data(
            val id: Int,
            val name: String,
            val url: String,
            val feedUrl: String,
            val articleCount: Int,
            val updatedAt: DateTime?
    )

    fun findAll(): List<Data>
}