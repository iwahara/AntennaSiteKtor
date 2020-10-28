package com.iwahara.antenna.ktor.usecase.by_site

import com.iwahara.antenna.ktor.Clock
import com.iwahara.antenna.ktor.database.DataBaseSettings
import org.jetbrains.exposed.sql.transactions.transaction
import org.joda.time.DateTime

class BySiteUseCase(private val dbSettings: DataBaseSettings,
                    private val articleListBySite: ArticleListBySite,
                    private val siteDataById: SiteDataById,
                    private val clock: Clock) {
    data class SiteData(
            val id: Int,
            val name: String,
            val url: String,
            val feedUrl: String,
            val articleCount: Int,
            val updatedAt: DateTime?
    )

    data class ArticleData(
            val id: Int,
            val name: String,
            val url: String,
            val postDatetime: DateTime,
            val sortingOrder: String,
            val viewCount: Int
    )

    data class Data(val siteData: SiteData, val articleList: List<ArticleData>)

    fun get(siteId: Int, count: Int): Data {
        return transaction(dbSettings.db) {
            val siteData = siteDataById.get(siteId)
            val articleList = articleListBySite.get(siteId, clock.now(), count).map {
                convert(it)
            }
            Data(convert(siteData), articleList)
        }
    }

    fun get(siteId: Int, count: Int, offset: String): Data {
        return transaction(dbSettings.db) {
            val siteData = siteDataById.get(siteId)
            val articleList = articleListBySite.get(siteId, clock.now(), count, offset).map {
                convert(it)
            }
            Data(convert(siteData), articleList)
        }
    }

    private fun convert(origin: SiteDataById.Data): SiteData {
        return SiteData(origin.id, origin.name, origin.url, origin.feedUrl, origin.articleCount, origin.updatedAt)
    }

    private fun convert(origin: ArticleListBySite.Data): ArticleData {
        return ArticleData(origin.id, origin.name, origin.url, origin.postDatetime, origin.sortingOrder, origin.viewCount)
    }
}