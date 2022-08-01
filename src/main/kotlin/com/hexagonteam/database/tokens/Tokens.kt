package com.hexagonteam.database.tokens


import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction

object Tokens : Table() {
    private val id = Tokens.varchar(name = "id", length = 50)
    private val login = Tokens.varchar(name = "login", length = 25)
    private val token = Tokens.varchar(name = "token", length = 50)

    fun insert(tokenDto: TokenDto) {
        transaction {
            Tokens.insert {
                it[id] = tokenDto.rowId
                it[login] = tokenDto.login
                it[token] = tokenDto.token
            }
        }
    }
}