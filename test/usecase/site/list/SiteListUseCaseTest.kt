package com.iwahara.antenna.ktor.usecase.site.list

import com.iwahara.antenna.ktor.ClockSpecify
import com.iwahara.antenna.ktor.database.DataBaseConnectionInfo
import com.iwahara.antenna.ktor.database.DataBaseSettings
import com.iwahara.antenna.ktor.model.SiteRepository
import com.iwahara.antenna.ktor.model.site_list.ArticleBySiteRepository
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.joda.time.DateTime
import kotlin.test.Test
import kotlin.test.assertEquals

class SiteListUseCaseTest {
    @Test
    fun test_get() {
        val targetDatetime = DateTime.now()
        val count = 10

        val updateAt = DateTime.now()
        val siteListModel = mockk<SiteList>()
        val siteList = listOf(
                SiteRepository.Data(1, "サイト", "http://example.com", "http://example.com/feed", 1, updateAt)
        )
        every { siteListModel.get() } returns siteList

        val postDatetime = DateTime.now()
        val articleListModel = mockk<ArticleList>()
        val articleList = listOf(
                ArticleBySiteRepository.Data(1, "記事", "http://example.com", postDatetime, "202009120909091234", 1)
        )
        every { articleListModel.get(1, targetDatetime, count) } returns articleList
        val dbSetting = DataBaseSettings(DataBaseConnectionInfo("org.h2.Driver", "jdbc:h2:mem:regular", "", ""))

        val clock = ClockSpecify(targetDatetime)

        val useCase = SiteListUseCase(dbSetting, siteListModel, articleListModel, clock)
        val actual = useCase.get(count)

        assertEquals(1, actual.size)

        verify(exactly = 1) { siteListModel.get() }
        verify(exactly = 1) { articleListModel.get(1, targetDatetime, count) }
        confirmVerified(siteListModel)
        confirmVerified(articleListModel)
    }
}