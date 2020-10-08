package com.iwahara.antenna.ktor.controller

import com.iwahara.antenna.ktor.usecase.site.list.SiteListUseCase
import com.iwahara.antenna.ktor.view.MainTemplate
import io.ktor.application.*
import io.ktor.html.*
import io.ktor.routing.*
import kotlinx.html.a
import kotlinx.html.classes
import kotlinx.html.div
import kotlinx.html.p
import org.joda.time.DateTime
import org.koin.ktor.ext.inject


fun Routing.siteList() {
    val useCase by inject<SiteListUseCase>()
    val siteArticleDataList = useCase.get(DateTime.now(), 10)
    get("/article_view/site.html") {
        call.respondHtmlTemplate(MainTemplate()) {
            title {
                text("サイト別一覧")
            }
            content {
                div {
                    classes = setOf("row row-eq-height")
                    for (siteArticleData in siteArticleDataList) {
                        div {
                            classes = setOf("col-xs-12 col-md-6")
                            div {
                                classes = setOf("panel panel-info")
                                div {
                                    classes = setOf("panel-heading")
                                    a {
                                        href = "/site/${siteArticleData.siteData.id}"
                                        text(siteArticleData.siteData.name)
                                    }
                                }
                                div {
                                    classes = setOf("list-group")
                                    for (article in siteArticleData.articleList) {
                                        a {
                                            classes = setOf("list-group-item")
                                            href = article.url
                                            target = "_blank"
                                            p {
                                                classes = setOf("list-group-item-heading")
                                                text(article.name)
                                            }
                                            p {
                                                classes = setOf("list-group-item-text")
                                                text(siteArticleData.siteData.name)
                                            }
                                            p {
                                                classes = setOf("list-group-item-text")
                                                text(article.postDatetime.toString("yyyy-MM-dd HH:mm:ss"))
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}