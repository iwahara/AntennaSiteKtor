package com.iwahara.antenna.ktor.repository

import com.iwahara.antenna.ktor.entity.Site
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.selectAll
import org.joda.time.DateTime

class SiteRepository {
    data class Data(
        val id: Int,
        val name: String,
        val url: String,
        val feedUrl: String,
        val articleCount: Int,
        val updatedAt: DateTime?
    )

    fun findAll(): MutableList<Data> {
        val ret = mutableListOf<Data>()

        Site.selectAll().orderBy(Site.updatedAt, SortOrder.DESC).forEach {
            val data = Data(
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