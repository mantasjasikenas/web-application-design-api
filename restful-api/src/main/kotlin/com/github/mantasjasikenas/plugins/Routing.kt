package com.github.mantasjasikenas.plugins

import com.github.mantasjasikenas.data.*
import com.github.mantasjasikenas.routes.projectRoutes
import com.github.mantasjasikenas.routes.sectionRoutes
import com.github.mantasjasikenas.routes.taskRoutes
import io.github.smiley4.ktorswaggerui.routing.openApiSpec
import io.github.smiley4.ktorswaggerui.routing.swaggerUI
import io.ktor.server.application.*
import io.ktor.server.routing.*


fun Application.configureRouting() {
    val projectRepository: ProjectRepository = ProjectRepositoryImpl()
    val taskRepository: TaskRepository = TaskRepositoryImpl()
    val sectionRepository: SectionRepository = SectionRepositoryImpl()

    routing {
        route("/api/v1") {
            route("api.json") {
                openApiSpec()
            }

            route("swagger") {
                swaggerUI("/api/v1/api.json")
            }

            projectRoutes(projectRepository)
            taskRoutes(taskRepository)
            sectionRoutes(sectionRepository)
        }
    }
}
