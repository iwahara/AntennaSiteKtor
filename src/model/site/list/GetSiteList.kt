package com.iwahara.antenna.ktor.model.site.list

class GetSiteList(private val siteRepository: SiteRepository) {

    fun get(): List<SiteRepository.Data> {
        return siteRepository.findAll()
    }
}