package com.github.mantasjasikenas.plugins

import io.github.smiley4.ktorswaggerui.SwaggerUI
import io.ktor.server.application.*

fun Application.configureSwaggerUI() {
    install(SwaggerUI) {
        info {
            title = "Tornado RESTful API"
            version = "1.0"
            description = "API used for Tornado tasks management"
        }
        server {
            url = "http://localhost:8080"
            description = "Local Server"
        }
    }
}