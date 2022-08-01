package com.hexagonteam.database.users

sealed class UsersTableError

data class UserNotFoundError(
    val message: String = "User not found"
) : UsersTableError()

object DbHaveDuplicatesError : UsersTableError()

data class UserAlreadyExistsError(
    val message: String = "User already exists"
) : UsersTableError()