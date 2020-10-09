package com.iwahara.antenna.ktor

import com.iwahara.antenna.ktor.controller.siteList
import com.iwahara.antenna.ktor.database.DataBaseConnectionInfo
import com.iwahara.antenna.ktor.database.DataBaseSettings
import com.iwahara.antenna.ktor.model.site.list.ArticleListImpl
import com.iwahara.antenna.ktor.model.site.list.ArticleRepository
import com.iwahara.antenna.ktor.model.site.list.SiteListImpl
import com.iwahara.antenna.ktor.model.site.list.SiteRepository
import com.iwahara.antenna.ktor.repository.ArticleRepositoryImpl
import com.iwahara.antenna.ktor.repository.SiteRepositoryImpl
import com.iwahara.antenna.ktor.usecase.site.list.ArticleList
import com.iwahara.antenna.ktor.usecase.site.list.SiteList
import com.iwahara.antenna.ktor.usecase.site.list.SiteListUseCase
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
fun Application.module(testing: Boolean = false) {
    install(FreeMarker) {
        templateLoader = ClassTemplateLoader(this::class.java.classLoader, "templates")
    }

    install(Locations) {
    }
    val url = environment.config.property("antenna.database.url").getString()
    val port = environment.config.property("antenna.database.port").getString()
    val name = environment.config.property("antenna.database.name").getString()
    val user = environment.config.property("antenna.database.user").getString()
    val password = environment.config.property("antenna.database.password").getString()

    val databaseConnectionInfo = DataBaseConnectionInfo("com.mysql.jdbc.Driver", url, port, name, user, password)

    val migration = Migration(url, port, name, user, password)
    migration.migrate()

    install(Koin) {
        printLogger()
        modules(getModule(databaseConnectionInfo))
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
        factory { ArticleRepositoryImpl() as ArticleRepository }
        factory { SiteRepositoryImpl() as SiteRepository }
        factory { SiteListImpl(get()) as SiteList }
        factory { ArticleListImpl(get()) as ArticleList }
        factory { SiteListUseCase(DataBaseSettings(databaseConnectionInfo), get(), get()) }
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

