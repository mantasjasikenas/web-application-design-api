package com.github.mantasjasikenas.model

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

suspend inline fun <reified T> ApplicationCall.respondSuccess(message: String, data: T? = null) {
    respondCustom(HttpStatusCode.OK, message, data)
}

suspend inline fun <reified T> ApplicationCall.respondCreated(message: String, data: T? = null) {
    respondCustom(HttpStatusCode.Created, message, data)
}

suspend inline fun ApplicationCall.respondBadRequest(message: String, errors: List<String>? = null) {
    respondCustom<Unit>(HttpStatusCode.BadRequest, message, errors = errors)
}

suspend inline fun ApplicationCall.respondNotFound(message: String, errors: List<String>? = null) {
    respondCustom<Unit>(HttpStatusCode.NotFound, message, errors = errors)
}

suspend inline fun ApplicationCall.respondInternalServerError(message: String, errors: List<String>? = null) {
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