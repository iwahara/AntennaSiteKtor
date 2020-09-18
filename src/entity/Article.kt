package com.iwahara.antenna.ktor.entity

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.jodatime.datetime

object Article : Table() {
    val id = integer("id").autoIncrement()
    val name = text("article_name")
    val url = varchar("url", length = 2048)
    val postDatetime = datetime("post_datetime")
    val sortingOrder = char("sorting_order", 32).uniqueIndex()
    val viewCount = integer("view_count").default(0)

    override val primaryKey = PrimaryKey(id, name = "pk_article_id")
}