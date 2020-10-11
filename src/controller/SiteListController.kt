package com.iwahara.antenna.ktor.controller

import com.iwahara.antenna.ktor.usecase.site.list.SiteListUseCase
import com.iwahara.antenna.ktor.view.site.ListView
import io.ktor.application.*
import io.ktor.html.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject


fun Routing.siteList() {
    val useCase by inject<SiteListUseCase>()
    val siteArticleDataList = useCase.get(10)
    get("/site.html") {
        call.respondHtmlTemplate(ListView(siteArticleDataList)) {}
    }
}