package com.github.mantasjasikenas.plugins

import com.github.mantasjasikenas.model.project.PostProjectDto
import com.github.mantasjasikenas.model.project.UpdateProjectDto
import com.github.mantasjasikenas.model.project.validate
import com.github.mantasjasikenas.model.respondCustom
import com.github.mantasjasikenas.model.section.PostSectionDto
import com.github.mantasjasikenas.model.section.UpdateSectionDto
import com.github.mantasjasikenas.model.section.validate
import com.github.mantasjasikenas.model.task.PostTaskDto
import com.github.mantasjasikenas.model.task.UpdateTaskDto
import com.github.mantasjasikenas.model.task.validate
import io.ktor.http.*
import io.ktor.serialization.*
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.plugins.requestvalidation.*
import io.ktor.server.plugins.statuspages.*
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.MissingFieldException

@OptIn(ExperimentalSerializationApi::class)
fun Application.configureValidation() {
    install(StatusPages) {
        exception<RequestValidationException> { call, cause ->
            call.respondCustom(HttpStatusCode.UnprocessableEntity, cause.reasons.joinToString())
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

        exception<Throwable> { call, throwable ->
            if (throwable is IllegalArgumentException) {
                call.respondCustom(HttpStatusCode.BadRequest, "Bad request (illegal argument)")

                return@exception
            }

            if (throwable is IllegalStateException) {
                call.respondCustom(HttpStatusCode.BadRequest, "Bad request (illegal state)")

                return@exception
            }

            if (throwable is BadRequestException) {
                if (throwable.cause is JsonConvertException) {
                    when (val cause = throwable.cause?.cause) {
                        is MissingFieldException
                            -> call.respondCustom(
                            HttpStatusCode.UnprocessableEntity,
                            "Missing fields: ${cause.missingFields.joinToString()}"
                        )

                        else -> call.respondCustom(HttpStatusCode.BadRequest, "Wrong JSON body")
                    }
                    return@exception
                }

                call.respondCustom(HttpStatusCode.BadRequest, "Bad request")

                return@exception
            }
        }
    }

    install(RequestValidation) {
        validate<PostTaskDto>(PostTaskDto::validate)
        validate<PostProjectDto>(PostProjectDto::validate)
        validate<PostSectionDto>(PostSectionDto::validate)

        validate<UpdateTaskDto>(UpdateTaskDto::validate)
        validate<UpdateProjectDto>(UpdateProjectDto::validate)
        validate<UpdateSectionDto>(UpdateSectionDto::validate)
    }
}