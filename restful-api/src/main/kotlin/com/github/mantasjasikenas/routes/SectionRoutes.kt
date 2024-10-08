package com.github.mantasjasikenas.routes

import com.github.mantasjasikenas.data.ProjectRepository
import com.github.mantasjasikenas.data.SectionRepository
import com.github.mantasjasikenas.docs.*
import com.github.mantasjasikenas.model.*
import com.github.mantasjasikenas.model.section.PostSectionDto
import com.github.mantasjasikenas.model.section.UpdateSectionDto
import io.github.smiley4.ktorswaggerui.dsl.routing.delete
import io.github.smiley4.ktorswaggerui.dsl.routing.get
import io.github.smiley4.ktorswaggerui.dsl.routing.patch
import io.github.smiley4.ktorswaggerui.dsl.routing.post
import io.github.smiley4.ktorswaggerui.dsl.routing.route
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.routing.*


fun Route.sectionRoutes(
    sectionRepository: SectionRepository,
    projectRepository: ProjectRepository,
) {
    route(sectionRoutesDocs()) {
        post("/projects/{id}/sections", createSectionDocs()) {
            val projectId = call.parameters["id"]?.toInt()

            if (projectId == null) {
                call.respondBadRequest("Project id is required")
                return@post
            }

            projectRepository.projectById(projectId) ?: run {
                call.respondNotFound("Project not found. Please provide a valid project id")
                return@post
            }

            val postSectionDto = call.receive<PostSectionDto>()
            val sectionDto = sectionRepository.addSection(projectId, postSectionDto)

            call.respondCreated("Section created", sectionDto)
        }

        route("/sections") {
            get(getAllSectionsDocs()) {
                val sections = sectionRepository.allSections()

                call.respondSuccess("All sections", sections)
            }

            get("/{id}", getSectionByIdDocs()) {
                val id = call.parameters["id"]?.toInt()

                if (id == null) {
                    call.respondBadRequest("Section id is required")
                    return@get
                }

                val section = sectionRepository.sectionById(id)

                if (section != null) {
                    call.respondSuccess("Section by id", section)
                } else {
                    call.respondNotFound("Section not found")
                }
            }

            patch("/{id}", updateSectionByIdDocs()) {
                val sectionId = call.parameters["id"]?.toInt()
                val sectionDto = call.receive<UpdateSectionDto>()

                if (sectionId == null) {
                    call.respondBadRequest("Section id is required")
                    return@patch
                }

                sectionDto.projectId?.let { projectRepository.projectById(it) } ?: run {
                    call.respondNotFound("Project not found. Please provide a valid project id")
                    return@patch
                }

                val updatedSection = sectionRepository.updateSection(sectionId, sectionDto)

                if (updatedSection != null) {
                    call.respondSuccess("Section updated", updatedSection)
                } else {
                    call.respondNotFound("Section not found")
                }
            }

            delete("/{id}", deleteSectionByIdDocs()) {
                val id = call.parameters["id"]?.toInt()

                if (id == null) {
                    call.respondBadRequest("Section id is required")
                    return@delete
                }

                val removed = sectionRepository.removeSection(id)

                if (removed) {
                    call.respondCustom(HttpStatusCode.NoContent, "Section deleted")
                } else {
                    call.respondNotFound("Section not found")
                }
            }
        }
    }
}
