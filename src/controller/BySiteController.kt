package com.iwahara.antenna.ktor.controller

import com.iwahara.antenna.ktor.usecase.by_site.BySiteUseCase
import com.iwahara.antenna.ktor.view.site.ArticleBySite
import io.ktor.application.*
import io.ktor.html.*
import io.ktor.locations.*
import io.ktor.routing.*
import kotlinx.html.HTML
import org.koin.ktor.ext.inject

@Location("/site/{siteId}")
data class BySite(val siteId: Int)

@Location("/site/{siteId}/{offset}")
data class BySiteOffset(val siteId: Int, val offset: String)

fun Routing.bySite() {
    val controller by inject<BySiteController>()
    get<BySite> { bySite ->
        call.respondHtmlTemplate(controller.bySite(bySite)) {}
    }
    get<BySiteOffset> { bySiteOffset ->
        call.respondHtmlTemplate(controller.bySite(bySiteOffset)) {}
    }
}

class BySiteController(private val useCase: BySiteUseCase) {
    fun bySite(bySite: BySite): Template<HTML> {
        val data = useCase.get(bySite.siteId, 10)
        return ArticleBySite(data)
    }

    fun bySite(bySiteOffset: BySiteOffset): Template<HTML> {
        val data = useCase.get(bySiteOffset.siteId, 10, bySiteOffset.offset)
        return ArticleBySite(data)
    }
}