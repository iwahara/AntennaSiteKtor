package com.iwahara.antenna.ktor.rss_feed

import org.joda.time.DateTime

data class RSSFeedItem(val title: String, val url: String, val postDatetime: DateTime)