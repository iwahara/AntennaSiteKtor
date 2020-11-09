package com.iwahara.antenna.ktor.rss_feed.parser

import com.iwahara.antenna.ktor.rss_feed.RSSFeedItem

interface RSSParser {
    fun parse(xml: String): List<RSSFeedItem>

    fun supports(xml: String): Boolean
}