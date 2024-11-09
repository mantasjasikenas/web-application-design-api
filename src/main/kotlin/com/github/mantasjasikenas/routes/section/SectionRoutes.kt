package com.github.mantasjasikenas.routes.section

import com.github.mantasjasikenas.data.*
import com.github.mantasjasikenas.data.section.PostSectionDto
import com.github.mantasjasikenas.data.section.UpdateSectionDto
import com.github.mantasjasikenas.docs.section.*
import com.github.mantasjasikenas.repository.ProjectRepository
import com.github.mantasjasikenas.repository.SectionRepository
import com.github.mantasjasikenas.routes.getProjectIdOrRespond
import com.github.mantasjasikenas.routes.getProjectSectionIdOrRespond
import com.github.mantasjasikenas.util.extractRolesOrRespond
import com.github.mantasjasikenas.util.extractSubjectOrRespond
import com.github.mantasjasikenas.util.extractSubjectWithRolesOrRespond
import io.github.smiley4.ktorswaggerui.dsl.routing.delete
import io.github.smiley4.ktorswaggerui.dsl.routing.get
import io.github.smiley4.ktorswaggerui.dsl.routing.patch
import io.github.smiley4.ktorswaggerui.dsl.routing.post
import io.github.smiley4.ktorswaggerui.dsl.routing.route
import io.ktor.http.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.routing.*


fun Route.sectionRoutes(
    sectionRepository: SectionRepository,
    projectRepository: ProjectRepository,
) {
    val routeService = SectionRoutesService(projectRepository, sectionRepository)

    authenticate {
        route("/projects/{projectId}", sectionRoutesDocs()) {
            route("/sections") {
                get(getAllSectionsDocs()) {
                    val roles = call.extractRolesOrRespond() ?: return@get
                    val userId = call.extractSubjectOrRespond() ?: return@get

                    val projectId = call.getProjectIdOrRespond() ?: return@get
                    routeService.validateProjectOrRespond(call, projectId) ?: return@get

                    // check if query parameter is present, if to return sections with tasks
                    val withTasks = call.request.queryParameters["withTasks"]?.toBoolean() ?: false

                    val sections = if (roles.contains(Role.Admin)) {
                        sectionRepository.allSections(projectId, withTasks)
                    } else {
                        sectionRepository.allUserSections(userId, projectId, withTasks)
                    }

                    call.respondSuccess("All sections", sections)
                }

                post(postSectionDocs()) {
                    val projectId = call.getProjectIdOrRespond() ?: return@post
                    routeService.validateProjectOrRespond(call, projectId) ?: return@post

                    val userId = call.extractSubjectOrRespond() ?: return@post
                    val postSectionDto = call.receive<PostSectionDto>()

                    val sectionDto = sectionRepository.addSection(userId, projectId, postSectionDto)

                    if (sectionDto == null) {
                        call.respondBadRequest("Project not found")
                        return@post
                    }

                    call.respondCreated("Section created", sectionDto)
                }

                get("/{sectionId}", getSectionByIdDocs()) {
                    val (projectId, sectionId) = call.getProjectSectionIdOrRespond() ?: return@get
                    val (userId, roles) = call.extractSubjectWithRolesOrRespond() ?: return@get

                    val section = routeService.validateOrRespond(call, projectId, sectionId) ?: return@get
                    routeService.authorizeOrRespond(call, section, userId, roles) ?: return@get

                    call.respondSuccess("Section by id", section)
                }

                patch("/{sectionId}", updateSectionByIdDocs()) {
                    val (projectId, sectionId) = call.getProjectSectionIdOrRespond() ?: return@patch
                    val (userId, roles) = call.extractSubjectWithRolesOrRespond() ?: return@patch

                    val updateSectionDto = call.receive<UpdateSectionDto>()

                    val sectionDto = routeService.validateOrRespond(call, projectId, sectionId) ?: return@patch
                    routeService.authorizeOrRespond(call, sectionDto, userId, roles) ?: return@patch

                    val updatedSection = sectionRepository.updateSection(projectId, sectionId, updateSectionDto)

                    if (updatedSection == null) {
                        call.respondNotFound("Section not found")
                        return@patch
                    }

                    call.respondSuccess("Section updated", updatedSection)
                }

                delete("/{sectionId}", deleteSectionByIdDocs()) {
                    val (projectId, sectionId) = call.getProjectSectionIdOrRespond() ?: return@delete
                    val (userId, roles) = call.extractSubjectWithRolesOrRespond() ?: return@delete

                    val taskDto = routeService.validateOrRespond(call, projectId, sectionId) ?: return@delete
                    routeService.authorizeOrRespond(call, taskDto, userId, roles) ?: return@delete

                    val removed = sectionRepository.removeSection(projectId, sectionId)

                    if (!removed) {
                        call.respondNotFound("Section not found")
                        return@delete
                    }

                    call.respondCustom(HttpStatusCode.NoContent, "Section deleted")
                }
            }
        }
    }
}