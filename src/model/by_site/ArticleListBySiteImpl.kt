package com.iwahara.antenna.ktor.model.by_site

import com.iwahara.antenna.ktor.model.ArticleRepository
import com.iwahara.antenna.ktor.usecase.ArticleListBySite
import org.joda.time.DateTime

class ArticleListBySiteImpl(private val articleRepository: ArticleRepository) : ArticleListBySite {
    override fun get(siteId: Int, targetDatetime: DateTime, count: Int): List<ArticleRepository.Data> {
        return this.articleRepository.findBySite(siteId, targetDatetime, count)
    }

    override fun get(siteId: Int, targetDatetime: DateTime, count: Int, offset: String): List<ArticleRepository.Data> {
        return this.articleRepository.findBySite(siteId, targetDatetime, count, offset)
    }
}