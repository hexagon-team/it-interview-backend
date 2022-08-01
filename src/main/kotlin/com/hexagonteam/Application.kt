package com.hexagonteam

import com.hexagonteam.constants.Urls.SERVER_DOMAIN
import com.hexagonteam.constants.Urls.SERVER_PORT
import com.hexagonteam.database.ServerProperties
import com.hexagonteam.login.configureLoginRouting
import com.hexagonteam.plugins.configureSerialization
import com.hexagonteam.registration.configureRegistrationRouting
import io.ktor.server.cio.*
import io.ktor.server.engine.*
import org.jetbrains.exposed.sql.Database


fun main() {

    with(ServerProperties) {
        Database.connect(
            url = databaseUrl,
            driver = databaseDriver,
            user = databaseUser,
            password = databasePassword,
        )
    }

    embeddedServer(CIO, port = SERVER_PORT, host = SERVER_DOMAIN) {
        configureLoginRouting()
        configureRegistrationRouting()
        configureSerialization()
    }.start(wait = true)
}
