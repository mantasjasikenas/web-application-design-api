package com.github.mantasjasikenas.plugins

import com.github.mantasjasikenas.model.PostTaskDto
import com.github.mantasjasikenas.model.respondCustom
import com.github.mantasjasikenas.model.validate
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.requestvalidation.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*

fun Application.configureValidation() {
    install(StatusPages) {
        exception<RequestValidationException> { call, cause ->
            call.respond(HttpStatusCode.BadRequest, cause.reasons.joinToString())
        }

        status(HttpStatusCode.NotFound) { call, _ ->
            call.respondCustom(HttpStatusCode.NotFound, "Resource not found")
        }

        status(HttpStatusCode.BadRequest) { call, _ ->
            call.respondCustom(HttpStatusCode.BadRequest, "Bad request")
        }

        status(HttpStatusCode.InternalServerError) { call, _ ->
            call.respondCustom(HttpStatusCode.InternalServerError, "Internal server error")
        }

        exception<Throwable> { call, cause ->
            call.respondCustom(HttpStatusCode.InternalServerError, cause.message ?: "Internal server error")
        }

    }

    install(RequestValidation) {
        validate<PostTaskDto>(PostTaskDto::validate)
    }
}