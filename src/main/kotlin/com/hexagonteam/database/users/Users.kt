package com.hexagonteam.database.users


import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

object Users : Table() {
    private val login = Users.varchar(name = "login", length = 25)
    private val password = Users.varchar(name = "password", length = 25)
    private val email = Users.varchar(name = "email", length = 50)

    fun insert(userDto: UserDto) {
        transaction {
            Users.insert {
                it[login] = userDto.login
                it[password] = userDto.password
                it[email] = userDto.password
            }
        }
    }

    fun getUser(login: String): UserDto? {
        return try {
            transaction {
                val user = Users.select { Users.login.eq(login) }.single()

                UserDto(
                    login = user[Users.login],
                    password = user[password],
                    email = user[email]
                )
            }
        } catch (exception: Exception) {
            exception.printStackTrace()
            null
        }
    }
}