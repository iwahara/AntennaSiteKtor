package com.iwahara.antenna.ktor.usecase.site_list

import com.iwahara.antenna.ktor.model.site_list.SiteListRepository

interface SiteList {
    fun get(): List<SiteListRepository.Data>
}