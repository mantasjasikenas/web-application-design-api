package com.github.mantasjasikenas.routes

import com.github.mantasjasikenas.data.SectionRepository
import com.github.mantasjasikenas.data.SectionRepositoryImpl
import com.github.mantasjasikenas.model.PostSectionDto
import com.github.mantasjasikenas.model.UpdateSectionDto
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Routing.sectionRoutes() {
    val sectionRepository: SectionRepository = SectionRepositoryImpl()

    // TODO: move elsewhere
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
        get {
            val sections = sectionRepository.allSections()

            call.respond(sections)
        }

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

        put("/sections/{sectionId}") {
            val sectionId = call.parameters["sectionId"]?.toInt()
            val sectionDto = call.receive<UpdateSectionDto>()

            if (sectionId == null) {
                call.respond(HttpStatusCode.BadRequest)
                return@put
            }

            sectionRepository.updateSection(sectionId, sectionDto)

            call.respond(HttpStatusCode.OK)
        }

        delete("/sections/{id}") {
            val id = call.parameters["id"]?.toInt()

            if (id == null) {
                call.respond(HttpStatusCode.BadRequest)
                return@delete
            }

            sectionRepository.removeSection(id)

            call.respond(HttpStatusCode.OK)
        }
    }

}
