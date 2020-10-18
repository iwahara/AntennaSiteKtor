package com.iwahara.antenna.ktor.model.by_site

import com.iwahara.antenna.ktor.model.ArticleRepository
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
        val articleRepository = mockk<ArticleRepository>()
        val postDatetime = DateTime.now()
        val targetDatetime = DateTime.now()
        val expected =
                listOf(ArticleRepository.Data(1, "記事", "http://example.com", postDatetime, "12345678901234567890123456789012", 1))

        every { articleRepository.findBySite(1, targetDatetime, 10, "12345678901234567890123456789012") } returns expected

        val model = ArticleListBySiteImpl(articleRepository)
        val actual = model.get(1, targetDatetime, 10, "12345678901234567890123456789012")

        assertEquals(1, actual.size)
        assertEquals(expected, actual)
        verify(exactly = 1) { articleRepository.findBySite(1, targetDatetime, 10, "12345678901234567890123456789012") }
        confirmVerified(articleRepository)
    }
}