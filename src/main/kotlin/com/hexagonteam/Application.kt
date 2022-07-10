package com.hexagonteam

import com.hexagonteam.constants.UrlConstants.DATABASE_DRIVER
import com.hexagonteam.constants.UrlConstants.DATABASE_PASSWORD
import com.hexagonteam.constants.UrlConstants.DATABASE_URL
import com.hexagonteam.constants.UrlConstants.DATABASE_USER
import com.hexagonteam.constants.UrlConstants.SERVER_DOMAIN
import com.hexagonteam.constants.UrlConstants.SERVER_PORT
import com.hexagonteam.login.configureLoginRouting
import io.ktor.server.engine.*
import io.ktor.server.cio.*
import com.hexagonteam.plugins.*
import com.hexagonteam.registration.configureRegistrationRouting
import org.jetbrains.exposed.sql.Database

fun main() {
    Database.connect(
        url = DATABASE_URL,
        driver = DATABASE_DRIVER,
        user = DATABASE_USER,
        password = DATABASE_PASSWORD,
    )

    embeddedServer(CIO, port = SERVER_PORT, host = SERVER_DOMAIN) {
        configureRouting()
        configureLoginRouting()
        configureRegistrationRouting()
        configureSerialization()
    }.start(wait = true)
}
