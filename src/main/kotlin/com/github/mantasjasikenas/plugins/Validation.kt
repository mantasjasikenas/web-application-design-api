package com.github.mantasjasikenas.plugins

import com.github.mantasjasikenas.data.project.PostProjectDto
import com.github.mantasjasikenas.data.project.UpdateProjectDto
import com.github.mantasjasikenas.data.project.validate
import com.github.mantasjasikenas.data.respondCustom
import com.github.mantasjasikenas.data.section.PostSectionDto
import com.github.mantasjasikenas.data.section.UpdateSectionDto
import com.github.mantasjasikenas.data.section.validate
import com.github.mantasjasikenas.data.task.PostTaskDto
import com.github.mantasjasikenas.data.task.UpdateTaskDto
import com.github.mantasjasikenas.data.task.validate
import com.github.mantasjasikenas.data.user.PostUserDto
import com.github.mantasjasikenas.data.user.validate
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

        status(HttpStatusCode.Unauthorized) { call, _ ->
            call.respondCustom(HttpStatusCode.Unauthorized, "The request is unauthenticated")
        }

        status(HttpStatusCode.Forbidden) { call, _ ->
            call.respondCustom(HttpStatusCode.Forbidden, "Access to the resource is prohibited")
        }

        status(HttpStatusCode.NotFound) { call, _ ->
            call.respondCustom(HttpStatusCode.NotFound, "Requested resource could not be found")
        }

        status(HttpStatusCode.BadRequest) { call, _ ->
            call.respondCustom(HttpStatusCode.BadRequest, "The server could not understand the request")
        }

        status(HttpStatusCode.InternalServerError) { call, _ ->
            call.respondCustom(
                HttpStatusCode.InternalServerError,
                "The server has encountered a situation it does not know how to handle"
            )
        }

        exception<Throwable> { call, throwable ->
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

                call.respondCustom(HttpStatusCode.BadRequest, "The server could not understand the request")

                return@exception
            }

            if (throwable is IllegalArgumentException || throwable is IllegalStateException) {
                call.respondCustom(HttpStatusCode.BadRequest, "The server could not understand the request")

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

        validate<PostUserDto>(PostUserDto::validate)
    }
}