package com.iwahara.antenna.ktor.usecase.site.by_site

import com.iwahara.antenna.ktor.Clock
import com.iwahara.antenna.ktor.database.DataBaseSettings
import com.iwahara.antenna.ktor.model.by_site.ArticleBySiteRepository
import com.iwahara.antenna.ktor.model.site_list.SiteListRepository
import org.jetbrains.exposed.sql.transactions.transaction

class BySiteUseCase(private val dbSettings: DataBaseSettings,
                    private val articleListBySite: ArticleListBySite,
                    private val siteDataById: SiteDataById,
                    private val clock: Clock) {
    data class Data(val siteData: SiteListRepository.Data, val articleList: List<ArticleBySiteRepository.Data>)

    fun get(siteId: Int, count: Int): Data {
        return transaction(dbSettings.db) {
            val siteData = siteDataById.get(siteId)
            val articleList = articleListBySite.get(siteId, clock.now(), count)
            Data(siteData, articleList)
        }
    }

    fun get(siteId: Int, count: Int, offset: String): Data {
        return transaction(dbSettings.db) {
            val siteData = siteDataById.get(siteId)
            val articleList = articleListBySite.get(siteId, clock.now(), count, offset)
            Data(siteData, articleList)
        }
    }
}