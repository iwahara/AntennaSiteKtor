package com.iwahara.antenna.ktor.usecase.site_list

import com.iwahara.antenna.ktor.Clock
import com.iwahara.antenna.ktor.database.DataBaseSettings
import com.iwahara.antenna.ktor.model.by_site.ArticleBySiteRepository
import com.iwahara.antenna.ktor.model.site_list.SiteListRepository
import org.jetbrains.exposed.sql.transactions.transaction

class SiteListUseCase(
        private val dbSettings: DataBaseSettings,
        private val siteList: SiteList,
        private val articleList: ArticleList,
        private val clock: Clock
) {
    data class Data(val siteData: SiteListRepository.Data, val articleList: List<ArticleBySiteRepository.Data>)

    fun get(count: Int): List<Data> {
        val ret = mutableListOf<Data>()
        transaction(dbSettings.db) {
            val siteList = siteList.get()
            for (site in siteList) {
                val articleList = articleList.get(site.id, clock.now(), count)
                ret.add(Data(site, articleList))
            }
        }
        return ret
    }
}