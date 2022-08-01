package com.hexagonteam.database.users


import org.jetbrains.exposed.exceptions.ExposedSQLException
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

object Users : Table() {
    private val login = Users.varchar(name = "login", length = 25)
    private val password = Users.varchar(name = "password", length = 25)
    private val email = Users.varchar(name = "email", length = 50)

    fun insert(userDto: UserDto): UserResult {
        return try {
            transaction {
                Users.insert {
                    it[login] = userDto.login
                    it[password] = userDto.password
                    it[email] = userDto.password
                }

                UserResult(dto = userDto)
            }
        } catch (exception: ExposedSQLException) {
            exception.printStackTrace()
            UserResult(dto = null, error = UserAlreadyExistsError())
        }
    }

    fun getUser(login: String): UserResult {
        return try {
            transaction {
                val user = Users.select { Users.login.eq(login) }.single()

                val dto = UserDto(
                    login = user[Users.login],
                    password = user[password],
                    email = user[email]
                )

                UserResult(dto = dto)
            }
        } catch (exception: Exception) {
            exception.printStackTrace()

            val error = when (exception) {
                is NoSuchElementException -> UserNotFoundError()
                else -> DbHaveDuplicatesError
            }

            UserResult(
                dto = null,
                error = error
            )
        }
    }
}