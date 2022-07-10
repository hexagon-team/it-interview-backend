package com.hexagonteam.registration

import com.hexagonteam.constants.UrlConstants
import com.hexagonteam.database.tokens.TokenDto
import com.hexagonteam.database.tokens.Tokens
import com.hexagonteam.database.users.UserDto
import com.hexagonteam.database.users.Users
import com.hexagonteam.utils.generateToken
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.exceptions.ExposedSQLException
import java.util.*

fun Application.configureRegistrationRouting() {
    routing {
        post(path = UrlConstants.REGISTRATION_PATH) {
            val receive = call.receive<RegistrationReceiveRemote>()

            if (receive.isValidEmail().not()) {
                call.respond(HttpStatusCode.BadRequest, message = "Invalid email")
            }

            val user = Users.getUser(receive.login)

            if (user == null) {
                val token = generateToken()
                val userDto = UserDto(
                    login = receive.login,
                    password = receive.password,
                    email = receive.email
                )

                val tokenDto = TokenDto(
                    rowId = UUID.randomUUID().toString(), // TODO how to replace it with autogenerate field?
                    login = receive.login,
                    token = token
                )

                try {
                    Users.insert(userDto)
                } catch (exception: ExposedSQLException) {
                    exception.printStackTrace()
                    call.respond(HttpStatusCode.Conflict, message = exception.toString())
                }

                Tokens.insert(tokenDto)

                call.respond(RegistrationResponseRemote(token))
            } else {
                call.respond(HttpStatusCode.Conflict, message = "User already exists")
            }
        }
    }
}

fun RegistrationReceiveRemote.isValidEmail(): Boolean {
    return email.isNotBlank() // TODO create validator
}