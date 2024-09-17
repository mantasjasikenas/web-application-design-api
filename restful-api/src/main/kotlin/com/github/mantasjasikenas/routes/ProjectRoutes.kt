package com.github.mantasjasikenas.routes

import com.github.mantasjasikenas.data.ProjectRepository
import com.github.mantasjasikenas.data.ProjectRepositoryImpl
import com.github.mantasjasikenas.model.PostProjectDto
import com.github.mantasjasikenas.model.UpdateProjectDto
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Routing.projectRoutes() {
    val projectRepository: ProjectRepository = ProjectRepositoryImpl()

    route("/projects") {
        get {
            val projects = projectRepository.allProjects()

            call.respond(projects)
        }

        post {
            val projectDto = call.receive<PostProjectDto>()
            val id = projectRepository.addProject(projectDto)

            call.respond(HttpStatusCode.Created, id)
        }

        get("/{id}") {
            val id = call.parameters["id"]?.toInt()

            if (id == null) {
                call.respond(HttpStatusCode.BadRequest)
                return@get
            }

            val project = projectRepository.projectById(id)

            if (project != null) {
                call.respond(HttpStatusCode.OK, project)
            } else {
                call.respond(HttpStatusCode.NotFound)
            }
        }

        put("/projects/{id}") {
            val id = call.parameters["id"]?.toInt()
            val projectDto = call.receive<UpdateProjectDto>()

            if (id == null) {
                call.respond(HttpStatusCode.BadRequest)
                return@put
            }

            projectRepository.updateProject(id, projectDto)

            call.respond(HttpStatusCode.OK)
        }

        delete("/projects/{id}") {
            val id = call.parameters["id"]?.toInt()

            if (id == null) {
                call.respond(HttpStatusCode.BadRequest)
                return@delete
            }

            val removed = projectRepository.removeProject(id)

            if (removed) {
                call.respond(HttpStatusCode.NoContent)
            } else {
                call.respond(HttpStatusCode.NotFound)
            }
        }
    }

}
