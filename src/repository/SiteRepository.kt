package com.iwahara.antenna.ktor.repository

import com.iwahara.antenna.ktor.entity.Site
import org.jetbrains.exposed.sql.selectAll

class SiteRepository {
    data class Data(
        val id: Int,
        val name: String,
        val url: String,
        val feedUrl: String
    )

    fun find(count: Int): MutableList<Data> {
        val ret = mutableListOf<Data>()
        
        Site.selectAll().limit(count).forEach {
            val data = Data(it[Site.id], it[Site.name], it[Site.url], it[Site.feedUrl])
            ret.add(data)
        }

        return ret
    }
}