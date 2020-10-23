package com.iwahara.antenna.ktor.model.site_list

import com.iwahara.antenna.ktor.usecase.site.list.ArticleList
import org.joda.time.DateTime

class ArticleListImpl(private val articleRepository: ArticleBySiteRepository) : ArticleList {

    override fun get(siteId: Int, targetDatetime: DateTime, count: Int): List<ArticleBySiteRepository.Data> {
        return articleRepository.findBySite(siteId, targetDatetime, count)
    }
}