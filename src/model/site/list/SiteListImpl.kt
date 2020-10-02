package com.iwahara.antenna.ktor.model.site.list

class SiteListImpl(private val siteRepository: SiteRepository) {

    fun get(): List<SiteRepository.Data> {
        return siteRepository.findAll()
    }
}