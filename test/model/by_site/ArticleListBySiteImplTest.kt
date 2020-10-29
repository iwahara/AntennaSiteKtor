package com.iwahara.antenna.ktor.model.by_site

import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.joda.time.DateTime
import kotlin.test.Test
import kotlin.test.assertEquals

class ArticleListBySiteImplTest {

    @Test
    fun test_get() {
        val articleRepository = mockk<ArticleBySiteRepository>()
        val postDatetime = DateTime.now()
        val targetDatetime = DateTime.now()
        val expected =
                listOf(ArticleBySiteRepository.Data(1, "記事", "http://example.com", postDatetime, "12345678901234567890123456789012", 1))

        every { articleRepository.findBySite(1, targetDatetime, 10, "12345678901234567890123456789012") } returns expected

        val model = ArticleListBySiteImpl(articleRepository)
        val actual = model.get(1, targetDatetime, 10, "12345678901234567890123456789012")

        assertEquals(1, actual.size)
        assertEquals(expected[0].id, actual[0].id)
        assertEquals(expected[0].name, actual[0].name)
        assertEquals(expected[0].url, actual[0].url)
        assertEquals(expected[0].postDatetime, actual[0].postDatetime)
        assertEquals(expected[0].sortingOrder, actual[0].sortingOrder)
        assertEquals(expected[0].viewCount, actual[0].viewCount)

        verify(exactly = 1) { articleRepository.findBySite(1, targetDatetime, 10, "12345678901234567890123456789012") }
        confirmVerified(articleRepository)
    }
}