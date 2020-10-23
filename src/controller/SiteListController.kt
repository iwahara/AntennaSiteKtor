package com.iwahara.antenna.ktor.controller

import com.iwahara.antenna.ktor.usecase.site_list.SiteListUseCase
import com.iwahara.antenna.ktor.view.site.ListView
import io.ktor.application.*
import io.ktor.html.*
import io.ktor.routing.*
import kotlinx.html.HTML
import org.koin.ktor.ext.inject


fun Routing.siteList() {
    val controller by inject<SiteListController>()
    get("/site/list") {
        call.respondHtmlTemplate(controller.site()) {}
    }
}

class SiteListController(private val useCase: SiteListUseCase) {
    fun site(): Template<HTML> {
        val siteArticleDataList = useCase.get(10)
        return ListView(siteArticleDataList)
    }
}