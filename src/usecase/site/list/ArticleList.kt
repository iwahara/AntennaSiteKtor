package com.iwahara.antenna.ktor.usecase.site.list

import com.iwahara.antenna.ktor.model.by_site.ArticleBySiteRepository
import org.joda.time.DateTime

interface ArticleList {
    fun get(siteId: Int, targetDatetime: DateTime, count: Int): List<ArticleBySiteRepository.Data>
}