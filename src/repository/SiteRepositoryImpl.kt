package com.iwahara.antenna.ktor.repository

import com.iwahara.antenna.ktor.entity.Site
import com.iwahara.antenna.ktor.model.by_site.SiteDataRepository
import com.iwahara.antenna.ktor.model.site_list.SiteListRepository
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll

class SiteRepositoryImpl() : SiteListRepository, SiteDataRepository {

    override fun findAll(): List<SiteListRepository.Data> {
        val ret = mutableListOf<SiteListRepository.Data>()
        Site.selectAll().orderBy(Site.updatedAt, SortOrder.DESC).forEach {
            val data = SiteListRepository.Data(
                    it[Site.id],
                    it[Site.name],
                    it[Site.url],
                    it[Site.feedUrl],
                    it[Site.articleCount],
                    it[Site.updatedAt]
            )
            ret.add(data)

        }

        return ret
    }

    override fun findById(siteId: Int): SiteDataRepository.Data {
        return Site.select { Site.id eq siteId }.single().let {
            SiteDataRepository.Data(
                    it[Site.id],
                    it[Site.name],
                    it[Site.url],
                    it[Site.feedUrl],
                    it[Site.articleCount],
                    it[Site.updatedAt]
            )
        }
    }
}