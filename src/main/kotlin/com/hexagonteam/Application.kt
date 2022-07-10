package com.hexagonteam

import com.hexagonteam.login.configureLoginRouting
import io.ktor.server.engine.*
import io.ktor.server.cio.*
import com.hexagonteam.plugins.*
import com.hexagonteam.registration.configureRegistrationRouting

fun main() {
    embeddedServer(CIO, port = 8080, host = "0.0.0.0") {
        configureRouting()
        configureLoginRouting()
        configureRegistrationRouting()
        configureSerialization()
    }.start(wait = true)
}
