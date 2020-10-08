package com.iwahara.antenna.ktor.repository

import com.iwahara.antenna.ktor.DataBaseSettings
import com.iwahara.antenna.ktor.entity.Site
import com.iwahara.antenna.ktor.model.site.list.SiteRepository
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

class SiteRepositoryImpl(private val dbSettings: DataBaseSettings) : SiteRepository {

    override fun findAll(): List<SiteRepository.Data> {
        val ret = mutableListOf<SiteRepository.Data>()

        transaction(dbSettings.db) {
            Site.selectAll().orderBy(Site.updatedAt, SortOrder.DESC).forEach {
                val data = SiteRepository.Data(
                    it[Site.id],
                    it[Site.name],
                    it[Site.url],
                    it[Site.feedUrl],
                    it[Site.articleCount],
                    it[Site.updatedAt]
                )
                ret.add(data)
            }
        }

        return ret
    }
}