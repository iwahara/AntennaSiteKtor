package com.iwahara.antenna.ktor.usecase

import com.iwahara.antenna.ktor.model.ArticleRepository
import org.joda.time.DateTime

interface ArticleListBySite {
    fun get(siteId: Int, targetDatetime: DateTime, count: Int, offset: String): List<ArticleRepository.Data>
}