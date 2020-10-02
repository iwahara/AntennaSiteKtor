package com.iwahara.antenna.ktor.usecase.site.list

import com.iwahara.antenna.ktor.model.site.list.ArticleRepository
import org.joda.time.DateTime

interface ArticleList {
    fun get(siteId: Int, targetDatetime: DateTime, count: Int): List<ArticleRepository.Data>
}