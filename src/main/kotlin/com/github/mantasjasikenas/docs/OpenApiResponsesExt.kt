package com.github.mantasjasikenas.docs

import com.github.mantasjasikenas.data.ApiResponse
import io.github.smiley4.ktorswaggerui.dsl.routes.OpenApiResponses
import io.ktor.http.*

inline fun OpenApiResponses.notFoundResponse(
    description: String = "Not found", message: String = "Requested resource could not be found"
) {
    HttpStatusCode.NotFound to {
        this.description = description
        body<ApiResponse<String>> {
            example("default") {
                value = ApiResponse(
                    data = null, success = false, status = HttpStatusCode.NotFound.value.toString(), message = message
                )
            }
        }
    }
}

inline fun OpenApiResponses.badRequestResponse(
    description: String = "Bad request", message: String = "The server could not understand the request"
) {
    HttpStatusCode.BadRequest to {
        this.description = description
        body<ApiResponse<String>> {
            example("default") {
                value = ApiResponse(
                    data = null, success = false, status = HttpStatusCode.BadRequest.value.toString(), message = message
                )
            }
        }
    }
}

inline fun OpenApiResponses.unprocessableEntityResponse(
    description: String = "Unprocessable entity",
    message: String = "The request was well-formed but had semantic errors"
) {
    HttpStatusCode.UnprocessableEntity to {
        this.description = description
        body<ApiResponse<String>> {
            example("default") {
                value = ApiResponse(
                    data = null,
                    success = false,
                    status = HttpStatusCode.UnprocessableEntity.value.toString(),
                    message = message
                )
            }
        }
    }
}

inline fun OpenApiResponses.noContentResponse(
    description: String = "No content",
    message: String = "The server successfully processed the request and is not returning any content"
) {
    HttpStatusCode.NoContent to {
        this.description = description
        body<ApiResponse<String>> {
            example("default") {
                value = ApiResponse(
                    data = null, success = true, status = HttpStatusCode.NoContent.value.toString(), message = message
                )
            }
        }
    }
}

inline fun <reified T> OpenApiResponses.okResponse(
    description: String = "OK", message: String = "The request was successful", data: T? = null
) {
    HttpStatusCode.OK to {
        this.description = description
        body<ApiResponse<T>> {
            example("default") {
                value = ApiResponse(
                    data = data, success = true, status = HttpStatusCode.OK.value.toString(), message = message
                )
            }
        }
    }
}

inline fun <reified T> OpenApiResponses.createdResponse(
    description: String = "Created",
    message: String = "The request has been fulfilled and has resulted in one or more new resources being created",
    data: T? = null
) {
    HttpStatusCode.Created to {
        this.description = description
        body<ApiResponse<String>> {
            example("default") {
                value = ApiResponse(
                    data = data, success = true, status = HttpStatusCode.Created.value.toString(), message = message
                )
            }
        }
    }
}

inline fun OpenApiResponses.unauthorizedResponse(
    description: String = "Unauthorized",
    message: String = "The request is unauthenticated",
    errors: List<String>? = null
) {
    HttpStatusCode.Unauthorized to {
        this.description = description
        body<ApiResponse<String>> {
            example("default") {
                value = ApiResponse(
                    data = null,
                    success = false,
                    status = HttpStatusCode.Unauthorized.value.toString(),
                    message = message,
                    errors = errors
                )
            }
        }
    }
}

inline fun OpenApiResponses.forbiddenResponse(
    description: String = "Forbidden", message: String = "Access to the resource is prohibited"
) {
    HttpStatusCode.Forbidden to {
        this.description = description
        body<ApiResponse<String>> {
            example("default") {
                value = ApiResponse(
                    data = null, success = false, status = HttpStatusCode.Forbidden.value.toString(), message = message
                )
            }
        }
    }
}