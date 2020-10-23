package com.iwahara.antenna.ktor.usecase.site

import com.iwahara.antenna.ktor.model.site_list.SiteListRepository

interface SiteDataById {
    fun get(siteId: Int): SiteListRepository.Data
}