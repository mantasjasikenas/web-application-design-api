package com.github.mantasjasikenas.plugins

import com.github.mantasjasikenas.repository.ProjectRepository
import com.github.mantasjasikenas.repository.SectionRepository
import com.github.mantasjasikenas.repository.TaskRepository
import com.github.mantasjasikenas.routes.authRoutes
import com.github.mantasjasikenas.routes.project.projectRoutes
import com.github.mantasjasikenas.routes.scalarRoute
import com.github.mantasjasikenas.routes.section.sectionRoutes
import com.github.mantasjasikenas.routes.task.taskRoutes
import com.github.mantasjasikenas.service.UserService
import io.github.smiley4.ktorswaggerui.routing.openApiSpec
import io.github.smiley4.ktorswaggerui.routing.swaggerUI
import io.ktor.server.application.*
import io.ktor.server.routing.*


fun Application.configureRouting(
    projectRepository: ProjectRepository,
    sectionRepository: SectionRepository,
    taskRepository: TaskRepository,
    userService: UserService
) {
    routing {
        route("/api/v1") {
            route("api.json") {
                openApiSpec()
            }

            route("swagger") {
                swaggerUI("/api/v1/api.json")
            }

            scalarRoute("/api/v1/api.json")

            authRoutes(userService)
            
            projectRoutes(projectRepository)
            sectionRoutes(sectionRepository, projectRepository)
            taskRoutes(projectRepository, sectionRepository, taskRepository)
        }
    }
}