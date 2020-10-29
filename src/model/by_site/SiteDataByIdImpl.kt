package com.iwahara.antenna.ktor.model.by_site

import com.iwahara.antenna.ktor.usecase.by_site.SiteDataById

class SiteDataByIdImpl(private val siteDataRepository: SiteDataRepository) : SiteDataById {

    override fun get(siteId: Int): SiteDataById.Data {
        return convert(siteDataRepository.findById(siteId))
    }

    private fun convert(origin: SiteDataRepository.Data): SiteDataById.Data {
        return SiteDataById.Data(origin.id, origin.name, origin.url, origin.feedUrl, origin.articleCount, origin.updatedAt)
    }
}