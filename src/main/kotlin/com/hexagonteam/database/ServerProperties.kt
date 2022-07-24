package com.hexagonteam.database

import java.util.*

object ServerProperties {
    private val properties: Properties by lazy {
        val props = Properties()
        props.load(
            javaClass.classLoader.getResourceAsStream("server.properties")
        )
        props
    }

    val databaseUrl: String
        get() = properties.getProperty("database_url")

    val databaseDriver: String
        get() = properties.getProperty("database_driver")

    val databaseUser: String
        get() = properties.getProperty("database_user")

    val databasePassword: String
        get() = properties.getProperty("database_password")

}