package com.github.mantasjasikenas.routes.project

import com.github.mantasjasikenas.data.*
import com.github.mantasjasikenas.data.project.PostProjectDto
import com.github.mantasjasikenas.data.project.UpdateProjectDto
import com.github.mantasjasikenas.docs.project.*
import com.github.mantasjasikenas.repository.ProjectRepository
import com.github.mantasjasikenas.routes.getProjectIdOrRespond
import com.github.mantasjasikenas.util.extractRolesOrRespond
import com.github.mantasjasikenas.util.extractSubjectOrRespond
import com.github.mantasjasikenas.util.extractSubjectWithRolesOrRespond
import io.github.smiley4.ktorswaggerui.dsl.routing.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.routing.*


fun Route.projectRoutes(projectRepository: ProjectRepository) {
    val routeService = ProjectRoutesService(projectRepository)

    authenticate {
        route("/projects", projectRoutesDocs()) {
            get(getAllProjectsDocs()) {
                val roles = call.extractRolesOrRespond() ?: return@get

                if (!roles.contains(Role.Admin)) {
                    call.respondForbidden()
                    return@get
                }

                val projects = projectRepository.allProjects()

                call.respondSuccess("All projects", projects)
            }

            post(postProjectDocs()) {
                val projectDto = call.receive<PostProjectDto>()

                val userId = call.extractSubjectOrRespond() ?: return@post
                val project = projectRepository.addProject(userId, projectDto)

                call.respondCreated("Project created", project)
            }

            get("/{projectId}", getProjectByIdDocs()) {
                val projectId = call.getProjectIdOrRespond() ?: return@get
                val (userId, roles) = call.extractSubjectWithRolesOrRespond() ?: return@get

                val project = routeService.validateOrRespond(call, projectId) ?: return@get
                routeService.authorizeOrRespond(call, project, userId, roles) ?: return@get

                call.respondSuccess("Project by id", project)
            }

            patch("/{projectId}", updateProjectByIdDocs()) {
                val projectId = call.getProjectIdOrRespond() ?: return@patch
                val (userId, roles) = call.extractSubjectWithRolesOrRespond() ?: return@patch

                val projectDto = call.receive<UpdateProjectDto>()

                val project = routeService.validateOrRespond(call, projectId) ?: return@patch
                routeService.authorizeOrRespond(call, project, userId, roles) ?: return@patch

                val updatedProject = projectRepository.updateProject(projectId, projectDto)

                if (updatedProject == null) {
                    call.respondNotFound("Project not found")
                    return@patch
                }

                call.respondSuccess("Project updated", updatedProject)
            }

            delete("/{projectId}", deleteProjectByIdDocs()) {
                val projectId = call.getProjectIdOrRespond() ?: return@delete
                val (userId, roles) = call.extractSubjectWithRolesOrRespond() ?: return@delete

                val project = routeService.validateOrRespond(call, projectId) ?: return@delete
                routeService.authorizeOrRespond(call, project, userId, roles) ?: return@delete

                val removed = projectRepository.removeProject(projectId)

                if (!removed) {
                    call.respondNotFound("Project not found")
                    return@delete
                }

                call.respondCustom(HttpStatusCode.NoContent, "Project deleted")
            }
        }
    }
}