package com.iwahara.antenna.ktor.usecase.site.list

import com.iwahara.antenna.ktor.model.SiteRepository

interface SiteList {
    fun get(): List<SiteRepository.Data>
}