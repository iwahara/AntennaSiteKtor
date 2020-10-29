package com.iwahara.antenna.ktor.usecase.by_site

import org.joda.time.DateTime

interface ArticleListBySite {
    data class Data(
            val id: Int,
            val name: String,
            val url: String,
            val postDatetime: DateTime,
            val sortingOrder: String,
            val viewCount: Int
    )

    fun get(siteId: Int, targetDatetime: DateTime, count: Int): List<Data>
    fun get(siteId: Int, targetDatetime: DateTime, count: Int, offset: String): List<Data>
}