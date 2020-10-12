package com.iwahara.antenna.ktor.repository

import com.iwahara.antenna.ktor.entity.Article
import com.iwahara.antenna.ktor.model.site_list.ArticleRepository
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.select
import org.joda.time.DateTime

class ArticleRepositoryImpl : ArticleRepository {

    override fun findBySite(siteId: Int, targetDatetime: DateTime, count: Int): List<ArticleRepository.Data> {
        val ret = mutableListOf<ArticleRepository.Data>()
        Article.select { Article.siteId eq siteId and (Article.postDatetime lessEq targetDatetime) }
                .orderBy(Article.sortingOrder, SortOrder.DESC).limit(count).forEach {
                    val data = ArticleRepository.Data(
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