package com.iwahara.antenna.ktor.model.site.list

import com.iwahara.antenna.ktor.repository.SiteRepositoryImpl

class GetSiteList(private val siteRepository: SiteRepositoryImpl) {

    fun get(): List<SiteRepositoryImpl.Data> {
        return siteRepository.findAll()
    }
}