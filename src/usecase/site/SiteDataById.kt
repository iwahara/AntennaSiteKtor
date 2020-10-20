package com.iwahara.antenna.ktor.usecase.site

import com.iwahara.antenna.ktor.model.SiteRepository

interface SiteDataById {
    fun get(siteId: Int): SiteRepository.Data
}