package com.github.mantasjasikenas.plugins

import io.github.smiley4.ktorswaggerui.SwaggerUI
import io.github.smiley4.ktorswaggerui.data.AuthScheme
import io.github.smiley4.ktorswaggerui.data.AuthType
import io.ktor.server.application.*

fun Application.configureSwaggerUI() {
    install(SwaggerUI) {
        info {
            title = "Tasks RESTful API"
            version = "1.0"
            description = "API used for tasks management"
        }
        externalDocs {
            url = "https://github.com/mantasjasikenas/web-application-design"
        }
        server {
            url = "http://localhost:8080"
            description = "Local Server"
        }
        security {
            defaultSecuritySchemeNames = setOf("SecurityScheme")
            securityScheme("SecurityScheme") {
                type = AuthType.HTTP
                scheme = AuthScheme.BEARER
            }
        }
    }
}