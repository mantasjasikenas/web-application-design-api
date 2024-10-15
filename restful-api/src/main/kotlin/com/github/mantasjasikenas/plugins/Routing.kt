package com.github.mantasjasikenas.plugins

import com.github.mantasjasikenas.model.Role
import com.github.mantasjasikenas.repository.ProjectRepository
import com.github.mantasjasikenas.repository.SectionRepository
import com.github.mantasjasikenas.repository.TaskRepository
import com.github.mantasjasikenas.routes.*
import com.github.mantasjasikenas.service.UserService
import com.github.mantasjasikenas.util.authorized
import com.github.mantasjasikenas.util.extractClaim
import io.github.smiley4.ktorswaggerui.routing.openApiSpec
import io.github.smiley4.ktorswaggerui.routing.swaggerUI
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*


fun Application.configureRouting(
    projectRepository: ProjectRepository,
    sectionRepository: SectionRepository,
    taskRepository: TaskRepository,
    userService: UserService
) {
    routing {
        route("/api/v1") {

            authenticate {
                authorized(Role.Admin) {
                    get("/protected/admin") {
                        val username = call.extractClaim("username")

                        call.respond("You are authorized as Admin. Your username is $username.")
                    }
                }
            }

            authenticate {
                authorized(Role.User) {
                    get("/protected/user") {
                        val username = call.extractClaim("username")

                        call.respond("You are authorized as User. Your username is $username.")
                    }
                }
            }

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