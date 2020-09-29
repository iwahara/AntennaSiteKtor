package com.iwahara.antenna.ktor.repository

import com.iwahara.antenna.ktor.entity.Article
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.select
import org.joda.time.DateTime

class ArticleRepositoryImpl {
    data class Data(
            val id: Int,
            val name: String,
            val url: String,
            val postDatetime: DateTime,
            val sortingOrder: String,
            val viewCount: Int
    )

    fun findBySite(siteId: Int, targetDatetime: DateTime, count: Int): List<Data> {
        val ret = mutableListOf<Data>()
        Article.select { Article.siteId eq siteId and (Article.postDatetime lessEq targetDatetime) }
                .orderBy(Article.sortingOrder, SortOrder.DESC).limit(count).forEach {
                    val data = Data(
                            it[Article.id],
                            it[Article.name],
                            it[Article.url],
                            it[Article.postDatetime],
                            it[Article.sortingOrder],
                            it[Article.viewCount]
                    )
                    ret.add(data)
                }
        return ret
    }
}