package com.hexagonteam.registration

import com.hexagonteam.constants.Paths.REGISTRATION_PATH
import com.hexagonteam.database.tokens.TokenDto
import com.hexagonteam.database.tokens.Tokens
import com.hexagonteam.database.users.*
import com.hexagonteam.utils.generateToken
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.util.*

fun Application.configureRegistrationRouting() {
    routing {
        post(path = REGISTRATION_PATH) {
            val receive = call.receive<RegistrationReceiveRemote>()

            val user = Users.getUser(receive.login)

            when {
                receive.isValidEmail().not() -> {
                    call.respond(HttpStatusCode.BadRequest, message = "Invalid email")
                }
                user.error is UserNotFoundError -> {
                    val token = generateToken()
                    val userDto = UserDto(
                        login = receive.login,
                        password = receive.password,
                        email = receive.email
                    )

                    val tokenDto = TokenDto(
                        // TODO how to replace it with autogenerate field?
                        //  https://github.com/hexagon-team/it-interview-backend/issues/4
                        rowId = UUID.randomUUID().toString(),
                        login = receive.login,
                        token = token
                    )


                    val insertResult = Users.insert(userDto)

                    insertResult.error?.let { error ->
                        if (error is UserAlreadyExistsError) {
                            call.respond(HttpStatusCode.Conflict, message = error.message)
                        }
                    }

                    Tokens.insert(tokenDto)

                    call.respond(RegistrationResponseRemote(token))
                }
                user.error is DbHaveDuplicatesError -> {
                    call.respond(HttpStatusCode.InternalServerError)
                }
                else -> call.respond(HttpStatusCode.Conflict, UserAlreadyExistsError().message)
            }
        }
    }
}

fun RegistrationReceiveRemote.isValidEmail(): Boolean {
    return email.isNotBlank() // TODO create validator https://github.com/hexagon-team/it-interview-backend/issues/3
}