package com.github.mantasjasikenas.routes

import com.github.mantasjasikenas.data.SectionRepository
import com.github.mantasjasikenas.model.PostSectionDto
import com.github.mantasjasikenas.model.SectionDto
import com.github.mantasjasikenas.model.UpdateSectionDto
import io.github.tabilzad.ktor.annotations.KtorResponds
import io.github.tabilzad.ktor.annotations.ResponseEntry
import io.github.tabilzad.ktor.annotations.Tag
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
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
            call.respond(HttpStatusCode.BadRequest)
            return@post
        }

        val sectionDto = call.receive<PostSectionDto>()
        val id = sectionRepository.addSection(projectId, sectionDto)

        call.respond(HttpStatusCode.Created, id)
    }


    route("/sections") {
        @KtorResponds(
            [
                ResponseEntry("200", PostSectionDto::class, true, description = "All sections")
            ]
        )
        get {
            val sections = sectionRepository.allSections()

            call.respond(sections)
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
                call.respond(HttpStatusCode.BadRequest)
                return@get
            }

            val section = sectionRepository.sectionById(id)

            if (section != null) {
                call.respond(HttpStatusCode.OK, section)
            } else {
                call.respond(HttpStatusCode.NotFound)
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
                call.respond(HttpStatusCode.BadRequest)
                return@put
            }

            val updatedSection = sectionRepository.updateSection(sectionId, sectionDto)

            if (updatedSection != null) {
                call.respond(HttpStatusCode.OK, updatedSection)
            } else {
                call.respond(HttpStatusCode.NotFound)
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
                call.respond(HttpStatusCode.BadRequest)
                return@delete
            }

            val removed = sectionRepository.removeSection(id)

            if (removed) {
                call.respond(HttpStatusCode.NoContent)
            } else {
                call.respond(HttpStatusCode.NotFound)
            }
        }
    }
}
