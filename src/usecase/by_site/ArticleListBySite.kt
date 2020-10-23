package com.iwahara.antenna.ktor.usecase.by_site

import com.iwahara.antenna.ktor.model.by_site.ArticleBySiteRepository
import org.joda.time.DateTime

interface ArticleListBySite {
    fun get(siteId: Int, targetDatetime: DateTime, count: Int): List<ArticleBySiteRepository.Data>
    fun get(siteId: Int, targetDatetime: DateTime, count: Int, offset: String): List<ArticleBySiteRepository.Data>
}