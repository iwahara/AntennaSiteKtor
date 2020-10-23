package com.iwahara.antenna.ktor.model.site_list

import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.joda.time.DateTime
import kotlin.test.Test
import kotlin.test.assertEquals

class ArticleListImplTest {

    @Test
    fun test_get() {
        val articleRepository = mockk<ArticleBySiteRepository>()
        val postDatetime = DateTime.now()
        val targetDatetime = DateTime.now()
        val expected =
                listOf(ArticleBySiteRepository.Data(1, "記事", "http://example.com", postDatetime, "202009120909091234", 1))

        every { articleRepository.findBySite(1, targetDatetime, 10) } returns expected

        val model = ArticleListImpl(articleRepository)
        val actual = model.get(1, targetDatetime, 10)

        assertEquals(1, actual.size)
        assertEquals(expected, actual)
        verify(exactly = 1) { articleRepository.findBySite(1, targetDatetime, 10) }
        confirmVerified(articleRepository)
    }
}