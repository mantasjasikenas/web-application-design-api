package com.github.mantasjasikenas.data

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import kotlinx.serialization.Serializable

@Serializable
data class ApiResponse<T>(
    val success: Boolean,
    val status: String,
    val message: String,
    val data: T? = null,
    val errors: List<String>? = null
)

suspend inline fun <reified T> ApplicationCall.respondSuccess(
    message: String = "The request was successful",
    data: T? = null
) {
    respondCustom(HttpStatusCode.OK, message, data)
}

suspend inline fun <reified T> ApplicationCall.respondCreated(
    message: String = "The request has been fulfilled and has resulted in one or more new resources being created",
    data: T? = null
) {
    respondCustom(HttpStatusCode.Created, message, data)
}

suspend inline fun ApplicationCall.respondBadRequest(
    message: String = "The server could not understand the request",
    errors: List<String>? = null
) {
    respondCustom<Unit>(HttpStatusCode.BadRequest, message, errors = errors)
}

suspend inline fun ApplicationCall.respondNotFound(
    message: String = "Requested resource could not be found",
    errors: List<String>? = null
) {
    respondCustom<Unit>(HttpStatusCode.NotFound, message, errors = errors)
}

suspend inline fun ApplicationCall.respondUnauthorized(
    message: String = "The request is unauthenticated",
    errors: List<String>? = null
) {
    respondCustom<Unit>(HttpStatusCode.Unauthorized, message, errors = errors)
}

suspend inline fun ApplicationCall.respondForbidden(
    message: String = "Access to the resource is prohibited",
    errors: List<String>? = null
) {
    respondCustom<Unit>(HttpStatusCode.Forbidden, message, errors = errors)
}

suspend inline fun ApplicationCall.respondUnprocessableEntity(
    message: String = "The request was well-formed but had semantic errors",
    errors: List<String>? = null
) {
    respondCustom<Unit>(HttpStatusCode.UnprocessableEntity, message, errors = errors)
}

suspend fun ApplicationCall.respondNoContent(message: String = "The server successfully processed the request and is not returning any content") {
    respondCustom<Unit>(HttpStatusCode.NoContent, message)
}

suspend inline fun ApplicationCall.respondInternalServerError(
    message: String = "An unexpected error occurred", errors: List<String>? = null
) {
    respondCustom<Unit>(HttpStatusCode.InternalServerError, message, errors = errors)
}

suspend inline fun ApplicationCall.respondCustom(
    status: HttpStatusCode,
    message: String,
    errors: List<String>? = null
) {
    respondCustom<Unit>(
        status = status,
        message = message,
        errors = errors
    )
}

suspend inline fun <reified T> ApplicationCall.respondCustom(
    status: HttpStatusCode,
    message: String,
    data: T? = null,
    errors: List<String>? = null
) {
    respond(
        status = status,
        message = ApiResponse(
            success = status.isSuccess(),
            status = status.value.toString(),
            message = message,
            data = data,
            errors = errors
        )
    )
}