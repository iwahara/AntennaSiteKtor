package com.iwahara.antenna.ktor.model.site.list

import org.joda.time.DateTime

class ArticleListImpl(private val articleRepository: ArticleRepository) {

    fun get(siteId: Int, targetDatetime: DateTime, count: Int): List<ArticleRepository.Data> {
        return articleRepository.findBySite(siteId, targetDatetime, count)
    }
}