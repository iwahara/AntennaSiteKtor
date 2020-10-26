package com.iwahara.antenna.ktor.view.site

import com.iwahara.antenna.ktor.usecase.by_site.BySiteUseCase
import com.iwahara.antenna.ktor.view.MainTemplate
import io.ktor.html.*
import kotlinx.html.*

class ArticleBySite(private val data: BySiteUseCase.Data, private val mainTemplate: MainTemplate = MainTemplate()) : Template<HTML> {
    override fun HTML.apply() {
        insert(mainTemplate) {
            title {
                text("${data.siteData.name}の記事一覧")
            }
            content {
                div {
                    classes = setOf("row row-eq-height")
                    div {
                        classes = setOf("col-xs-12 col-md-6")
                        div {
                            classes = setOf("panel panel-info")
                            div {
                                classes = setOf("panel-heading")
                                a {
                                    href = "/site/${data.siteData.id}"
                                    text(data.siteData.name)
                                }
                            }
                            div {
                                classes = setOf("list-group")
                                for (article in data.articleList) {
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
                                            text(data.siteData.name)
                                        }
                                        p {
                                            classes = setOf("list-group-item-text")
                                            text(article.postDatetime.toString("yyyy-MM-dd HH:mm:ss"))
                                        }
                                    }
                                }
                                if (data.articleList.isNotEmpty()) {
                                    a {
                                        classes = setOf("list-group-item", "next_page_link")
                                        href = "/site/${data.siteData.id}/${data.articleList.last().sortingOrder}"
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