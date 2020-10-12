package com.iwahara.antenna.ktor.view.site

import com.iwahara.antenna.ktor.usecase.site.list.SiteListUseCase
import com.iwahara.antenna.ktor.view.MainTemplate
import io.ktor.html.*
import kotlinx.html.*

class ListView(private val siteArticleDataList: List<SiteListUseCase.Data>, private val mainTemplate: MainTemplate = MainTemplate()) : Template<HTML> {
    override fun HTML.apply() {
        insert(mainTemplate) {
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