package com.github.mantasjasikenas.plugins

import io.ktor.server.application.*
import io.ktor.server.plugins.cors.routing.*

fun Application.configureCORS() {
    install(CORS) {
        anyHost()
        anyMethod()
        allowOrigins { true }
        allowHeaders { true }
        allowNonSimpleContentTypes = true
        allowCredentials = true
    }
}