package com.iwahara.antenna.ktor.model.by_site

import com.iwahara.antenna.ktor.usecase.by_site.ArticleListBySite
import org.joda.time.DateTime

class ArticleListBySiteImpl(private val articleRepository: ArticleBySiteRepository) : ArticleListBySite {
    override fun get(siteId: Int, targetDatetime: DateTime, count: Int): List<ArticleListBySite.Data> {
        return this.articleRepository.findBySite(siteId, targetDatetime, count).map { convert(it) }
    }

    override fun get(siteId: Int, targetDatetime: DateTime, count: Int, offset: String): List<ArticleListBySite.Data> {
        return this.articleRepository.findBySite(siteId, targetDatetime, count, offset).map { convert(it) }
    }

    private fun convert(origin: ArticleBySiteRepository.Data): ArticleListBySite.Data {
        return ArticleListBySite.Data(origin.id, origin.name, origin.url, origin.postDatetime, origin.sortingOrder, origin.viewCount)
    }
}