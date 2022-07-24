package com.hexagonteam.database.users

data class UserResult(
    val dto: UserDto?,
    val error: UsersTableError? = null
)
