package com.iwahara.antenna.ktor.model.site_list

import com.iwahara.antenna.ktor.usecase.site_list.SiteList

class SiteListImpl(private val siteListRepository: SiteListRepository) : SiteList {

    override fun get(): List<SiteListRepository.Data> {
        return siteListRepository.findAll()
    }
}