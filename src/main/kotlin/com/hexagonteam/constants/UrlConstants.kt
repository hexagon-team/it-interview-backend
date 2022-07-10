package com.hexagonteam.constants

/**
 * This cheat sheet will help you choose a name for the constant.
 *
 * example url - https://video.syncended.dev:25/videoplayer?id=123#00h15m30s
 *
 * 1. https:// - PROTOCOL
 * 2. video. - SUBDOMAIN
 * 3. syncended.dev - DOMAIN
 * 4. :25 - PORT
 * 5. videoplayer - PATH
 * 6. ? - prefix for query
 * 7. id=123 - PARAMETER
 * 8. #00h15m30s - FRAGMENT
 */
object UrlConstants {
    const val LOGIN_PATH = "/login"
    const val REGISTRATION_PATH = "/registration"
    const val SERVER_DOMAIN = "0.0.0.0"
    const val SERVER_PORT = 8080

    // Database TODO (temp data) move to gradle
    const val DATABASE_URL = "jdbc:postgresql://localhost:5432/it-interview"
    const val DATABASE_DRIVER = "org.postgresql.Driver"
    const val DATABASE_USER = "postgres"
    const val DATABASE_PASSWORD = "657299"
}