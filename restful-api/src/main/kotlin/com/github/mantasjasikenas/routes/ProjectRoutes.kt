package com.github.mantasjasikenas.routes

import com.github.mantasjasikenas.data.ProjectRepository
import com.github.mantasjasikenas.docs.*
import com.github.mantasjasikenas.model.*
import com.github.mantasjasikenas.model.project.PostProjectDto
import com.github.mantasjasikenas.model.project.UpdateProjectDto
import io.github.smiley4.ktorswaggerui.dsl.routing.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.routing.*


fun Route.projectRoutes(projectRepository: ProjectRepository) {
    route("/projects", projectRoutesDocs()) {
        get(getAllProjectsDocs()) {
            val projects = projectRepository.allProjects()

            call.respondSuccess("All projects", projects)
        }

        post(postProjectDocs()) {
            val projectDto = call.receive<PostProjectDto>()
            val project = projectRepository.addProject(projectDto)

            call.respondCreated("Project created", project)
        }

        get("/{id}", getProjectByIdDocs()) {
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

        patch("/{id}", updateProjectByIdDocs()) {
            val id = call.parameters["id"]?.toInt()
            val projectDto = call.receive<UpdateProjectDto>()

            if (id == null) {
                call.respondBadRequest("Project id is required")
                return@patch
            }

            val updatedProject = projectRepository.updateProject(id, projectDto)

            if (updatedProject != null) {
                call.respondSuccess("Project updated", updatedProject)
            } else {
                call.respondNotFound("Project not found")
            }
        }

        delete("/{id}", deleteProjectByIdDocs()) {
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
