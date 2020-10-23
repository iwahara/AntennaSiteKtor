package com.iwahara.antenna.ktor.model.by_site

import com.iwahara.antenna.ktor.usecase.by_site.SiteDataById

class SiteDataByIdImpl(private val siteDataRepository: SiteDataRepository) : SiteDataById {
    override fun get(siteId: Int): SiteDataRepository.Data {
        return siteDataRepository.findById(siteId)
    }
}