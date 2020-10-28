package com.iwahara.antenna.ktor.usecase.by_site

import org.joda.time.DateTime

interface SiteDataById {
    data class Data(
            val id: Int,
            val name: String,
            val url: String,
            val feedUrl: String,
            val articleCount: Int,
            val updatedAt: DateTime?
    )

    fun get(siteId: Int): Data
}