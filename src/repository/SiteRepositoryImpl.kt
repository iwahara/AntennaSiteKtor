package com.iwahara.antenna.ktor.repository

import com.iwahara.antenna.ktor.entity.Site
import com.iwahara.antenna.ktor.model.site_list.SiteListRepository
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.selectAll

class SiteRepositoryImpl() : SiteListRepository {

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
}