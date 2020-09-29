package com.iwahara.antenna.ktor.model.site.list

import com.iwahara.antenna.ktor.repository.ArticleRepositoryImpl
import org.joda.time.DateTime

class GetArticleList(private val articleRepository: ArticleRepositoryImpl) {

    fun get(siteId: Int, targetDatetime: DateTime, count: Int): List<ArticleRepositoryImpl.Data> {
        return articleRepository.findBySite(siteId, targetDatetime, count)
    }
}