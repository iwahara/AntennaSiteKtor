package com.iwahara.antenna.ktor.model.site_list

import com.iwahara.antenna.ktor.model.SiteRepository
import com.iwahara.antenna.ktor.usecase.site.list.SiteList

class SiteListImpl(private val siteRepository: SiteRepository) : SiteList {

    override fun get(): List<SiteRepository.Data> {
        return siteRepository.findAll()
    }
}