package com.hexagonteam.login

import com.hexagonteam.constants.UrlConstants.LOGIN_PATH
import com.hexagonteam.database.tokens.TokenDto
import com.hexagonteam.database.tokens.Tokens
import com.hexagonteam.database.users.Users
import com.hexagonteam.utils.generateToken
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.util.*

fun Application.configureLoginRouting() {
    routing {
        post(path = LOGIN_PATH) {
            val receive = call.receive<LoginReceiveRemote>()
            val user = Users.getUser(receive.login)

            if (user == null) {
                call.respond(HttpStatusCode.BadRequest, message = "User not found")
            } else {
                if (user.password == receive.password) {
                    val token = generateToken()
                    val tokenDto = TokenDto(
                        rowId = UUID.randomUUID().toString(), // TODO how to replace it with autogenerate field?
                        login = receive.login,
                        token = token
                    )
                    Tokens.insert(tokenDto)

                    call.respond(LoginResponseRemote(token))

                } else call.respond(HttpStatusCode.BadRequest, message = "Invalid password")
            }
        }
    }
}