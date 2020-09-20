package com.iwahara.antenna.ktor.entity

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.jodatime.datetime

object Site : Table() {
    val id = integer("id").autoIncrement()
    val name = varchar("name", length = 256)
    val url = varchar("url", length = 2048)
    val feedUrl = varchar("feed_url", length = 2048)
    val articleCount = integer("article_count").default(0)
    val updatedAt = datetime("updated_at").nullable()

    override val primaryKey = PrimaryKey(id, name = "pk_site_id")
}