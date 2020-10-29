package com.iwahara.antenna.ktor.usecase.by_site

import com.iwahara.antenna.ktor.ClockSpecify
import com.iwahara.antenna.ktor.database.DataBaseConnectionInfo
import com.iwahara.antenna.ktor.database.DataBaseSettings
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.joda.time.DateTime
import kotlin.test.Test
import kotlin.test.assertEquals

class BySiteUseCaseTest {
    @Test
    fun test_get() {
        val targetDatetime = DateTime.now()
        val count = 10

        val siteId = 1

        val updateAt = DateTime.now()
        val siteDataById = mockk<SiteDataById>()
        val site = SiteDataById.Data(1, "サイト", "http://example.com", "http://example.com/feed", 1, updateAt)
        every { siteDataById.get(siteId) } returns site

        val postDatetime = DateTime.now()
        val articleListBySite = mockk<ArticleListBySite>()
        val articleList = listOf(
                ArticleListBySite.Data(1, "記事", "http://example.com", postDatetime, "202009120909091234", 1)
        )
        every { articleListBySite.get(siteId, targetDatetime, count) } returns articleList
        val dbSetting = DataBaseSettings(DataBaseConnectionInfo("org.h2.Driver", "jdbc:h2:mem:regular", "", ""))

        val clock = ClockSpecify(targetDatetime)

        val useCase = BySiteUseCase(dbSetting, articleListBySite, siteDataById, clock)
        val actual = useCase.get(siteId, count)

        assertEquals(siteId, actual.siteData.id)

        verify(exactly = 1) { siteDataById.get(siteId) }
        verify(exactly = 1) { articleListBySite.get(1, targetDatetime, count) }
        confirmVerified(siteDataById)
        confirmVerified(articleListBySite)
    }
}