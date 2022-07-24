package com.hexagonteam.utils

import java.util.UUID

fun generateToken(): String {
    // TODO Bro, replace it with real token 🤡
    //  https://github.com/hexagon-team/it-interview-backend/issues/2
    return UUID.randomUUID().toString()
}