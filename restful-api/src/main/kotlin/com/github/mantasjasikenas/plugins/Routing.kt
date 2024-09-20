package com.github.mantasjasikenas.plugins

import com.github.mantasjasikenas.routes.projectRoutes
import com.github.mantasjasikenas.routes.sectionRoutes
import com.github.mantasjasikenas.routes.taskRoutes
import io.github.tabilzad.ktor.annotations.GenerateOpenApi
import io.ktor.server.application.*
import io.ktor.server.routing.*

@GenerateOpenApi
fun Application.configureRouting() {
    routing {
        route("/api/v1") {
            projectRoutes()
            taskRoutes()
            sectionRoutes()
        }
    }
}
