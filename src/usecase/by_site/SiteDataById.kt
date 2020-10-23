package com.iwahara.antenna.ktor.usecase.by_site

import com.iwahara.antenna.ktor.model.by_site.SiteDataRepository

interface SiteDataById {
    fun get(siteId: Int): SiteDataRepository.Data
}