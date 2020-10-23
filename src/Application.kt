package com.iwahara.antenna.ktor

import com.iwahara.antenna.ktor.controller.SiteListController
import com.iwahara.antenna.ktor.controller.siteList
import com.iwahara.antenna.ktor.database.DataBaseConnectionInfo
import com.iwahara.antenna.ktor.database.DataBaseSettings
import com.iwahara.antenna.ktor.model.by_site.ArticleBySiteRepository
import com.iwahara.antenna.ktor.model.by_site.ArticleListBySiteImpl
import com.iwahara.antenna.ktor.model.by_site.SiteDataByIdImpl
import com.iwahara.antenna.ktor.model.by_site.SiteDataRepository
import com.iwahara.antenna.ktor.model.site_list.ArticleListImpl
import com.iwahara.antenna.ktor.model.site_list.SiteListImpl
import com.iwahara.antenna.ktor.model.site_list.SiteListRepository
import com.iwahara.antenna.ktor.repository.ArticleRepositoryImpl
import com.iwahara.antenna.ktor.repository.SiteRepositoryImpl
import com.iwahara.antenna.ktor.usecase.by_site.ArticleListBySite
import com.iwahara.antenna.ktor.usecase.by_site.SiteDataById
import com.iwahara.antenna.ktor.usecase.site_list.ArticleList
import com.iwahara.antenna.ktor.usecase.site_list.SiteList
import com.iwahara.antenna.ktor.usecase.site_list.SiteListUseCase
import freemarker.cache.ClassTemplateLoader
import io.ktor.application.*
import io.ktor.freemarker.*
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.locations.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.util.*
import org.koin.core.module.Module
import org.koin.dsl.module
import org.koin.ktor.ext.Koin

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@KtorExperimentalAPI
@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false, testModule: Module? = null) {
    install(FreeMarker) {
        templateLoader = ClassTemplateLoader(this::class.java.classLoader, "templates")
    }

    install(Locations) {
    }
    val url = environment.config.property("antenna.database.url").getString()
    val user = environment.config.property("antenna.database.user").getString()
    val password = environment.config.property("antenna.database.password").getString()
    val driver = environment.config.property("antenna.database.driver").getString()

    val databaseConnectionInfo = DataBaseConnectionInfo(driver, url, user, password)

    val migration = Migration(databaseConnectionInfo)
    migration.migrate()

    install(Koin) {
        printLogger()
        modules(getModule(databaseConnectionInfo))

        if (testing && testModule != null) {
            //テスト用のModuleで上書き
            modules(testModule)
        }
    }
    routing {
        get("/") {
            call.respondText("HELLO WORLD!", contentType = ContentType.Text.Plain)

        }

        get("/html-freemarker") {
            call.respond(FreeMarkerContent("index.ftl", mapOf("data" to IndexData(listOf(1, 2, 3))), ""))
        }

        get("/test/{name}") {
            val name = call.parameters["name"]
            call.respondText("Hello World $name")
        }

        // Static feature. Try to access `/static/ktor_logo.svg`
        static("/static") {
            resources("static")
        }

        get<MyLocation> {
            call.respondText("Location: name=${it.name}, arg1=${it.arg1}, arg2=${it.arg2}")
        }
        // Register nested routes
        get<Type.Edit> {
            call.respondText("Inside $it")
        }
        get<Type.List> {
            call.respondText("Inside $it")
        }

        siteList()

    }
}


private fun getModule(databaseConnectionInfo: DataBaseConnectionInfo): Module {
    return module {
        factory { ArticleRepositoryImpl() as ArticleBySiteRepository }
        factory { SiteRepositoryImpl() as SiteDataRepository }
        factory { SiteRepositoryImpl() as SiteListRepository }

        factory { SiteListImpl(get()) as SiteList }
        factory { ArticleListImpl(get()) as ArticleList }
        factory { ArticleListBySiteImpl(get()) as ArticleListBySite }
        factory { SiteDataByIdImpl(get()) as SiteDataById }

        factory { ClockNow() as Clock }
        factory { SiteListUseCase(DataBaseSettings(databaseConnectionInfo), get(), get(), get()) }
        factory { SiteListController(get()) }
    }
}


data class IndexData(val items: List<Int>)

@Location("/location/{name}")
class MyLocation(val name: String, val arg1: Int = 42, val arg2: String = "default")

@Location("/type/{name}")
data class Type(val name: String) {
    @Location("/edit")
    data class Edit(val type: Type)

    @Location("/list/{page}")
    data class List(val type: Type, val page: Int)
}

