package com.github.mantasjasikenas.plugins

import com.github.mantasjasikenas.data.*
import com.github.mantasjasikenas.routes.projectRoutes
import com.github.mantasjasikenas.routes.sectionRoutes
import com.github.mantasjasikenas.routes.taskRoutes
import io.github.tabilzad.ktor.annotations.GenerateOpenApi
import io.ktor.server.application.*
import io.ktor.server.routing.*

@GenerateOpenApi
fun Application.configureRouting() {
    val projectRepository: ProjectRepository = ProjectRepositoryImpl()
    val taskRepository: TaskRepository = TaskRepositoryImpl()
    val sectionRepository: SectionRepository = SectionRepositoryImpl()

    routing {
        route("/api/v1") {
            projectRoutes(projectRepository)
            taskRoutes(taskRepository)
            sectionRoutes(sectionRepository)
        }
    }
}
