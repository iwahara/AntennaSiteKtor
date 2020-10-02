package com.iwahara.antenna.ktor.model.site.list

import com.iwahara.antenna.ktor.usecase.site.list.ArticleList
import org.joda.time.DateTime

class ArticleListImpl(private val articleRepository: ArticleRepository) : ArticleList {

    override fun get(siteId: Int, targetDatetime: DateTime, count: Int): List<ArticleRepository.Data> {
        return articleRepository.findBySite(siteId, targetDatetime, count)
    }
}