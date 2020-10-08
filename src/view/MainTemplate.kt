package com.iwahara.antenna.ktor.view

import io.ktor.html.*
import kotlinx.html.*

class MainTemplate : Template<HTML> {
    val title = Placeholder<TITLE>()
    val content = Placeholder<HtmlBlockTag>()
    override fun HTML.apply() {
        head {
            meta {
                httpEquiv = "Content-Type"
                content = "text/html; charset=utf-8"
            }
            link {
                rel = "stylesheet"
                href = "https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
            }
            link {
                rel = "stylesheet"
                href = "https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css"
            }
            script {
                src = "https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"
            }
            script {
                src = "https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"
            }
            title { insert(this@MainTemplate.title) }
            meta {
                name = "viewport"
                content = "target-densitydpi=device-dpi, width=device-width, initial-scale=1.0, user-scalable=yes"
            }
            meta {
                name = "referrer"
                content = "origin-when-crossorigin"
            }
        }
        body {
            nav {
                classes = setOf("navbar", "navbar-inverse", "navbar-fixed-top")
                div {
                    classes = setOf("container-fluid")
                    div {
                        classes = setOf("navbar-header")
                        button {
                            type = ButtonType.button
                            classes = setOf("navbar-toggle", "collapsed")
                            attributes["data-toggle"] = "collapse"
                            attributes["data-target"] = "#navbarEexample8"
                            span {
                                classes = setOf("sr-only")
                                +"Menu"
                            }
                            for (i in 1..3) {
                                span {
                                    classes = setOf("icon-bar")
                                }
                            }
                        }
                        a {
                            classes = setOf("navbar-brand")
                            href = "/"
                            +"アンテナサイト"
                        }
                    }
                    div {
                        classes = setOf("collapse", "navbar-collapse")
                        id = "navbarEexample8"
                        ul {
                            classes = setOf("nav", "navbar-nav")
                            li {
                                a {
                                    href = "/news/"
                                    +"記事一覧(更新順)"
                                }
                            }
                            li {
                                a {
                                    href = "/site/"
                                    +"サイト別一覧"
                                }
                            }
                        }
                    }
                }
            }
            div {
                classes = setOf("header")
                a {
                    href = "/"
                    +"アンテナサイト"
                    style = "width:300px;"
                }
            }
            div {
                classes = setOf("container")
                insert(content)
            }
        }
    }
}