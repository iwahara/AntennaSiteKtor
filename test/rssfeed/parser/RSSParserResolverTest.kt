package com.iwahara.antenna.ktor.rss_feed.parser

import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlin.test.Test

class RSSParserResolverTest {

    @Test
    fun test_resolve() {
        val xml = ""
        val resolveOk = mockk<RSSParser>()
        every { resolveOk.supports(xml) } returns true

        val resolveNg = mockk<RSSParser>()
        every { resolveNg.supports(xml) } returns false

        val resolver = RSSParserResolver(listOf(resolveNg, resolveOk))

        val actual = resolver.resolve(xml)

        verify(exactly = 1) { resolveNg.supports(xml) }
        verify(exactly = 1) { resolveOk.supports(xml) }

        confirmVerified(resolveNg)
        confirmVerified(resolveOk)
    }
}