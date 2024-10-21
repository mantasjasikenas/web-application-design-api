package com.github.mantasjasikenas.docs.project

import com.github.mantasjasikenas.data.project.PostProjectDto
import com.github.mantasjasikenas.data.project.UpdateProjectDto
import com.github.mantasjasikenas.docs.*
import io.github.smiley4.ktorswaggerui.dsl.routes.OpenApiRoute


fun projectRoutesDocs(): OpenApiRoute.() -> Unit = {
    description = "Project routes"
    tags("Projects")
}

fun getAllProjectsDocs(): OpenApiRoute.() -> Unit = {
    description = "Get all projects"

    response {
        okResponse(
            description = "All projects",
            message = "All projects",
            data = listOf(projectDtoExample, projectDtoExample2)
        )
        unauthorizedResponse()
        forbiddenResponse()
    }
}

fun postProjectDocs(): OpenApiRoute.() -> Unit = {
    description = "Create a new project"

    request {
        body<PostProjectDto> {
            description = "Project to create"
            example("default") {
                value = postProjectDtoExample
            }
        }
    }

    response {
        createdResponse(
            message = "Project created",
            data = projectDtoExample
        )

        badRequestResponse(
            message = "Invalid project repository"
        )

        unprocessableEntityResponse(
            message = "Validation failed"
        )

        unauthorizedResponse()

        forbiddenResponse()
    }
}

fun getProjectByIdDocs(): OpenApiRoute.() -> Unit = {
    description = "Get project by id"

    request {
        projectIdPathParameter()
    }

    response {
        okResponse(
            description = "Project by id",
            message = "Project by id",
            data = projectDtoExample
        )

        badRequestResponse(
            message = "Project id is required"
        )

        notFoundResponse(
            message = "Project not found"
        )

        unauthorizedResponse()

        forbiddenResponse()
    }
}

fun updateProjectByIdDocs(): OpenApiRoute.() -> Unit = {
    description = "Update project by id"

    request {
        projectIdPathParameter()

        body<UpdateProjectDto> {
            description = "Project repository to update"
            example("default") {
                value = updateProjectDtoExample
            }
        }
    }

    response {
        okResponse(
            message = "Project updated",
            data = projectDtoExample
        )

        badRequestResponse(
            message = "Invalid project repository"
        )

        unprocessableEntityResponse(
            message = "Validation failed"
        )

        notFoundResponse(
            message = "Project not found"
        )

        unauthorizedResponse()

        forbiddenResponse()
    }
}

fun deleteProjectByIdDocs(): OpenApiRoute.() -> Unit = {
    description = "Delete project by id"

    request {
        projectIdPathParameter()
    }

    response {
        noContentResponse(
            message = "Project deleted"
        )

        badRequestResponse(
            message = "Project id is required"
        )

        notFoundResponse(
            message = "Project not found"
        )

        unauthorizedResponse()

        forbiddenResponse()
    }
}