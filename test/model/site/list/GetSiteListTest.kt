package com.iwahara.antenna.ktor.model.site.list

import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.joda.time.DateTime
import kotlin.test.Test
import kotlin.test.assertEquals

class GetSiteListTest {

    @Test
    fun test_get() {
        val siteRepository = mockk<SiteRepository>()
        val updateAt = DateTime.now()
        val expected = listOf(
            SiteRepository.Data(1, "サイト", "http://example.com", "http://example.com/feed", 1, updateAt)
        )

        every { siteRepository.findAll() } returns expected

        val model = GetSiteList(siteRepository)
        val actual = model.get()

        assertEquals(1, actual.size)
        assertEquals(expected, actual)
        verify(exactly = 1) { siteRepository.findAll() }
        confirmVerified(siteRepository)
    }
}