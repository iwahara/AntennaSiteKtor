package com.iwahara.antenna.ktor.usecase.by_site

import com.iwahara.antenna.ktor.model.site_list.SiteListRepository

interface SiteDataById {
    fun get(siteId: Int): SiteListRepository.Data
}