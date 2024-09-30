package com.github.mantasjasikenas.routes

import com.github.mantasjasikenas.data.ProjectRepository
import com.github.mantasjasikenas.model.*
import com.github.mantasjasikenas.model.project.PostProjectDto
import com.github.mantasjasikenas.model.project.ProjectDto
import com.github.mantasjasikenas.model.project.UpdateProjectDto
import io.github.tabilzad.ktor.annotations.KtorResponds
import io.github.tabilzad.ktor.annotations.ResponseEntry
import io.github.tabilzad.ktor.annotations.Tag
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.routing.*

@Tag(["Projects"])
fun Route.projectRoutes(projectRepository: ProjectRepository) {
    route("/projects") {
        @KtorResponds(
            [
                ResponseEntry("200", PostProjectDto::class, true, description = "All projects")
            ]
        )
        get {
            val projects = projectRepository.allProjects()

            call.respondSuccess("All projects", projects)
        }

        @KtorResponds(
            [
                ResponseEntry("201", ProjectDto::class, description = "Project created"),
                ResponseEntry("400", String::class, description = "Bad request")
            ]
        )
        post {
            val projectDto = call.receive<PostProjectDto>()
            val project = projectRepository.addProject(projectDto)

            call.respondCreated("Project created", project)
        }

        @KtorResponds(
            [
                ResponseEntry("200", ProjectDto::class, description = "Project by id"),
                ResponseEntry("400", String::class, description = "Bad request"),
                ResponseEntry("404", String::class, description = "Not found")
            ]
        )
        get("/{id}") {
            val id = call.parameters["id"]?.toInt()

            if (id == null) {
                call.respondBadRequest("Project id is required")
                return@get
            }

            val project = projectRepository.projectById(id)

            if (project != null) {
                call.respondSuccess("Project by id", project)
            } else {
                call.respondNotFound("Project not found")
            }
        }

        @KtorResponds(
            [
                ResponseEntry("200", ProjectDto::class, description = "Project updated"),
                ResponseEntry("400", String::class, description = "Bad request"),
                ResponseEntry("404", String::class, description = "Not found")
            ]
        )
        put("/{id}") {
            val id = call.parameters["id"]?.toInt()
            val projectDto = call.receive<UpdateProjectDto>()

            if (id == null) {
                call.respondBadRequest("Project id is required")
                return@put
            }

            val updatedProject = projectRepository.updateProject(id, projectDto)

            if (updatedProject != null) {
                call.respondSuccess("Project updated", updatedProject)
            } else {
                call.respondNotFound("Project not found")
            }
        }

        @KtorResponds(
            [
                ResponseEntry("204", String::class, description = "Project deleted"),
                ResponseEntry("400", String::class, description = "Bad request"),
                ResponseEntry("404", String::class, description = "Not found")
            ]
        )
        delete("/{id}") {
            val id = call.parameters["id"]?.toInt()

            if (id == null) {
                call.respondBadRequest("Project id is required")
                return@delete
            }

            val removed = projectRepository.removeProject(id)

            if (removed) {
                call.respondCustom(HttpStatusCode.NoContent, "Project deleted")
            } else {
                call.respondNotFound("Project not found")
            }
        }
    }

}
