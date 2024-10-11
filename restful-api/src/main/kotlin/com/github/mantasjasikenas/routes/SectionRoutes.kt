package com.github.mantasjasikenas.routes

import com.github.mantasjasikenas.data.ProjectRepository
import com.github.mantasjasikenas.data.SectionRepository
import com.github.mantasjasikenas.docs.section.*
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
    route("/projects/{projectId}", sectionRoutesDocs()) {
        route("/sections") {
            get(getAllSectionsDocs()) {
                val projectId = call.validateAndGetProjectId(projectRepository) ?: return@get

                val sections = sectionRepository.allSections(projectId)

                call.respondSuccess("All sections", sections)
            }

            get("/{sectionId}", getSectionByIdDocs()) {
                val projectId = call.validateAndGetProjectId(projectRepository) ?: return@get
                val sectionId = call.validateAndGetSectionId(sectionRepository) ?: return@get

                val section = sectionRepository.sectionById(projectId, sectionId)

                if (section != null) {
                    call.respondSuccess("Section by id", section)
                } else {
                    call.respondNotFound("Section not found")
                }
            }

            post(postSectionDocs()) {
                val projectId = call.validateAndGetProjectId(projectRepository) ?: return@post

                val postSectionDto = call.receive<PostSectionDto>()
                val sectionDto = sectionRepository.addSection(projectId, postSectionDto)

                if (sectionDto == null) {
                    call.respondBadRequest("Project not found. Please provide a valid project id")
                    return@post
                }

                call.respondCreated("Section created", sectionDto)
            }

            patch("/{sectionId}", updateSectionByIdDocs()) {
                val projectId = call.validateAndGetProjectId(projectRepository) ?: return@patch
                val sectionId = call.validateAndGetSectionId(sectionRepository) ?: return@patch

                val sectionDto = call.receive<UpdateSectionDto>()

                val updatedSection = sectionRepository.updateSection(projectId, sectionId, sectionDto)

                if (updatedSection != null) {
                    call.respondSuccess("Section updated", updatedSection)
                } else {
                    call.respondNotFound("Section not found")
                }
            }

            delete("/{sectionId}", deleteSectionByIdDocs()) {
                val projectId = call.validateAndGetProjectId(projectRepository) ?: return@delete
                val sectionId = call.validateAndGetSectionId(sectionRepository) ?: return@delete

                val removed = sectionRepository.removeSection(projectId, sectionId)

                if (removed) {
                    call.respondCustom(HttpStatusCode.NoContent, "Section deleted")
                } else {
                    call.respondNotFound("Section not found")
                }
            }
        }
    }
}
