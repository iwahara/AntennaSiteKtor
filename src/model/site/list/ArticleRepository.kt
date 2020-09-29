package com.iwahara.antenna.ktor.model.site.list

import org.joda.time.DateTime

interface ArticleRepository {
    data class Data(
            val id: Int,
            val name: String,
            val url: String,
            val postDatetime: DateTime,
            val sortingOrder: String,
            val viewCount: Int
    )

    fun findBySite(siteId: Int, targetDatetime: DateTime, count: Int): List<Data>
}