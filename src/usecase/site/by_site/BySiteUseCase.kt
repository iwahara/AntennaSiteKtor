package com.iwahara.antenna.ktor.usecase.site.by_site

import com.iwahara.antenna.ktor.Clock
import com.iwahara.antenna.ktor.database.DataBaseSettings
import com.iwahara.antenna.ktor.model.ArticleRepository
import com.iwahara.antenna.ktor.model.SiteRepository
import com.iwahara.antenna.ktor.usecase.ArticleListBySite
import com.iwahara.antenna.ktor.usecase.site.SiteDataById
import org.jetbrains.exposed.sql.transactions.transaction

class BySiteUseCase(private val dbSettings: DataBaseSettings,
                    private val articleListBySite: ArticleListBySite,
                    private val siteDataById: SiteDataById,
                    private val clock: Clock) {
    data class Data(val siteData: SiteRepository.Data, val articleList: List<ArticleRepository.Data>)

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