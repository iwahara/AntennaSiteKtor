package com.iwahara.antenna.ktor.repository

import com.iwahara.antenna.ktor.entity.Article
import com.iwahara.antenna.ktor.model.by_site.ArticleBySiteRepository
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.select
import org.joda.time.DateTime

class ArticleRepositoryImpl : ArticleBySiteRepository {

    override fun findBySite(siteId: Int, targetDatetime: DateTime, count: Int): List<ArticleBySiteRepository.Data> {
        val ret = mutableListOf<ArticleBySiteRepository.Data>()
        Article.select { Article.siteId eq siteId and (Article.postDatetime lessEq targetDatetime) }
                .orderBy(Article.sortingOrder, SortOrder.DESC).limit(count).forEach {
                    val data = ArticleBySiteRepository.Data(
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

    override fun findBySite(siteId: Int, targetDatetime: DateTime, count: Int, offset: String): List<ArticleBySiteRepository.Data> {
        val ret = mutableListOf<ArticleBySiteRepository.Data>()
        Article.select { Article.siteId eq siteId and (Article.postDatetime lessEq targetDatetime) and (Article.sortingOrder less offset) }
                .orderBy(Article.sortingOrder, SortOrder.DESC).limit(count).forEach {
                    val data = ArticleBySiteRepository.Data(
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