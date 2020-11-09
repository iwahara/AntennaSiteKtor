package com.iwahara.antenna.ktor.rss_feed.parser

class RSSParserResolver(private val parsers: List<RSSParser>) {

    fun resolve(xml: String): RSSParser {
        for (parser in parsers) {
            if (parser.supports(xml)) {
                return parser
            }
        }
        throw NotResolveParserException()
    }

}