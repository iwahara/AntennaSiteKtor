package com.iwahara.antenna.ktor.model.by_site

import org.joda.time.DateTime

interface SiteDataRepository {
    data class Data(
            val id: Int,
            val name: String,
            val url: String,
            val feedUrl: String,
            val articleCount: Int,
            val updatedAt: DateTime?
    )

    fun findById(siteId: Int): Data
}