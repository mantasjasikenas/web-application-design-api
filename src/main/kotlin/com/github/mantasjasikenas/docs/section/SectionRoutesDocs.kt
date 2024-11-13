package com.github.mantasjasikenas.docs.section

import com.github.mantasjasikenas.data.section.PostSectionDto
import com.github.mantasjasikenas.data.section.UpdateSectionDto
import com.github.mantasjasikenas.docs.*
import io.github.smiley4.ktorswaggerui.dsl.routes.OpenApiRoute


fun sectionRoutesDocs(): OpenApiRoute.() -> Unit = {
    description = "Section routes"

    tags("Sections")
}

fun postSectionDocs(): OpenApiRoute.() -> Unit = {
    description = "Create a new section"

    request {
        projectIdPathParameter()

        body<PostSectionDto> {
            description = "Section to create"
            example("default") {
                value = postSectionDtoExample
            }
        }
    }

    response {
        createdResponse(description = "Section created", message = "Section created", data = sectionDtoExample)

        badRequestResponse(message = "Invalid section repository")

        notFoundResponse(message = "Project not found")

        unprocessableEntityResponse(message = "Validation failed")

        unauthorizedResponse()

        forbiddenResponse()
    }
}

fun getAllSectionsDocs(): OpenApiRoute.() -> Unit = {
    description = "Get all sections"

    request {
        projectIdPathParameter()

        queryParameter<Boolean>("withTasks") {
            description = "Include tasks in sections"
            example("default") {
                value = false
            }
        }
    }

    response {
        okResponse(
            description = "All sections",
            message = "All sections",
            data = listOf(sectionDtoExample, sectionDtoExample2)
        )
    }
}

fun getSectionByIdDocs(): OpenApiRoute.() -> Unit = {
    description = "Get section by id"

    request {
        projectIdPathParameter()

        sectionIdPathParameter()
    }

    response {
        okResponse(description = "Section by id", message = "Section by id", data = sectionDtoExample)

        badRequestResponse(message = "Section id is required")

        notFoundResponse(message = "Section not found")

        unauthorizedResponse()

        forbiddenResponse()
    }
}

fun updateSectionByIdDocs(): OpenApiRoute.() -> Unit = {
    description = "Update section by id"

    request {
        projectIdPathParameter()

        sectionIdPathParameter()

        body<UpdateSectionDto> {
            description = "Section repository to update"
            example("default") {
                value = updateSectionDtoExample
            }
        }
    }

    response {
        okResponse(description = "Section updated", message = "Section updated", data = sectionDtoExample)

        badRequestResponse(message = "Invalid section repository")

        notFoundResponse(message = "Section not found")

        unprocessableEntityResponse(message = "Validation failed")

        unauthorizedResponse()

        forbiddenResponse()
    }
}

fun deleteSectionByIdDocs(): OpenApiRoute.() -> Unit = {
    description = "Delete section by id"

    request {
        projectIdPathParameter()

        sectionIdPathParameter()
    }

    response {
        noContentResponse(message = "Section deleted")

        badRequestResponse(message = "Section id is required")

        notFoundResponse(message = "Section not found")

        unauthorizedResponse()

        forbiddenResponse()
    }
}