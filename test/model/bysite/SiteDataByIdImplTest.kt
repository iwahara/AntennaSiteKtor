package com.iwahara.antenna.ktor.model.by_site

import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.joda.time.DateTime
import kotlin.test.Test
import kotlin.test.assertEquals

class SiteDataByIdImplTest {

    @Test
    fun test_get() {
        val siteDataRepository = mockk<SiteDataRepository>()
        val updateAt = DateTime.now()
        val expected = SiteDataRepository.Data(1, "サイト", "http://example.com", "http://example.com/feed", 100, updateAt)

        every { siteDataRepository.findById(1) } returns expected

        val model = SiteDataByIdImpl(siteDataRepository)
        val actual = model.get(1)

        assertEquals(expected.id, actual.id)
        assertEquals(expected.name, actual.name)
        assertEquals(expected.url, actual.url)
        assertEquals(expected.feedUrl, actual.feedUrl)
        assertEquals(expected.articleCount, actual.articleCount)
        assertEquals(expected.updatedAt, actual.updatedAt)
        
        verify(exactly = 1) { siteDataRepository.findById(1) }
        confirmVerified(siteDataRepository)
    }
}