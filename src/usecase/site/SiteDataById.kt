package com.iwahara.antenna.ktor.usecase.site

import com.iwahara.antenna.ktor.model.site_list.SiteRepository

interface SiteDataById {
    fun get(siteId: Int): SiteRepository.Data
}