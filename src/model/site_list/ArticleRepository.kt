package com.iwahara.antenna.ktor.model.site_list

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
    fun findBySite(siteId: Int, targetDatetime: DateTime, count: Int, offset: String): List<Data>
}