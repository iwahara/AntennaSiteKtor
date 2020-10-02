package com.iwahara.antenna.ktor.usecase.site.list

import com.iwahara.antenna.ktor.model.site.list.ArticleRepository
import com.iwahara.antenna.ktor.model.site.list.SiteRepository
import org.joda.time.DateTime

class SiteListUseCase(private val siteList: SiteList, private val articleList: ArticleList) {
    data class Data(val siteData: SiteRepository.Data, val articleList: List<ArticleRepository.Data>)

    fun get(targetDatetime: DateTime, count: Int): List<Data> {
        val ret = mutableListOf<Data>()
        val siteList = siteList.get()
        for (site in siteList) {
            val articleList = articleList.get(site.id, targetDatetime, count)
            ret.add(Data(site, articleList))
        }
        return ret
    }
}