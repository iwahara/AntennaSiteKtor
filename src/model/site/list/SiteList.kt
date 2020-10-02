package com.iwahara.antenna.ktor.model.site.list

class SiteList(private val siteRepository: SiteRepository) {

    fun get(): List<SiteRepository.Data> {
        return siteRepository.findAll()
    }
}