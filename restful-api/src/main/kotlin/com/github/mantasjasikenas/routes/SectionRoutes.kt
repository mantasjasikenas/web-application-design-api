package com.github.mantasjasikenas.routes

import com.github.mantasjasikenas.data.SectionRepository
import com.github.mantasjasikenas.model.*
import io.github.tabilzad.ktor.annotations.KtorResponds
import io.github.tabilzad.ktor.annotations.ResponseEntry
import io.github.tabilzad.ktor.annotations.Tag
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.routing.*

@Tag(["Sections"])
fun Route.sectionRoutes(sectionRepository: SectionRepository) {
    @KtorResponds(
        [
            ResponseEntry("201", Int::class, description = "Section created"),
            ResponseEntry("400", String::class, description = "Bad request")
        ]
    )
    post("/projects/{projectId}/sections") {
        val projectId = call.parameters["id"]?.toInt()

        if (projectId == null) {
            call.respondBadRequest("Project id is required")
            return@post
        }

        val postSectionDto = call.receive<PostSectionDto>()
        val sectionDto = sectionRepository.addSection(projectId, postSectionDto)

        call.respondCreated("Section created", sectionDto)
    }


    route("/sections") {
        @KtorResponds(
            [
                ResponseEntry("200", PostSectionDto::class, true, description = "All sections")
            ]
        )
        get {
            val sections = sectionRepository.allSections()

            call.respondSuccess("All sections", sections)
        }

        @KtorResponds(
            [
                ResponseEntry("200", PostSectionDto::class, description = "Section by id"),
                ResponseEntry("400", String::class, description = "Bad request"),
                ResponseEntry("404", String::class, description = "Not found")
            ]
        )
        get("/{id}") {
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

        @KtorResponds(
            [
                ResponseEntry("200", SectionDto::class, description = "Section updated"),
                ResponseEntry("400", String::class, description = "Bad request"),
                ResponseEntry("404", String::class, description = "Not found")
            ]
        )
        put("/{sectionId}") {
            val sectionId = call.parameters["sectionId"]?.toInt()
            val sectionDto = call.receive<UpdateSectionDto>()

            if (sectionId == null) {
                call.respondBadRequest("Section id is required")
                return@put
            }

            val updatedSection = sectionRepository.updateSection(sectionId, sectionDto)

            if (updatedSection != null) {
                call.respondSuccess("Section updated", updatedSection)
            } else {
                call.respondNotFound("Section not found")
            }
        }

        @KtorResponds(
            [
                ResponseEntry("204", String::class, description = "Section deleted"),
                ResponseEntry("400", String::class, description = "Bad request"),
                ResponseEntry("404", String::class, description = "Not found")
            ]
        )
        delete("/{id}") {
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
