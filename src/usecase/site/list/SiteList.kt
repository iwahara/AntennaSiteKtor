package com.iwahara.antenna.ktor.usecase.site.list

import com.iwahara.antenna.ktor.model.site.list.SiteRepository

interface SiteList {
    fun get(): List<SiteRepository.Data>
}