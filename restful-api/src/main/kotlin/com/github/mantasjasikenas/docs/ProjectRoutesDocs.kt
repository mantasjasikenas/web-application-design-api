package com.github.mantasjasikenas.docs

import com.github.mantasjasikenas.model.ApiResponse
import com.github.mantasjasikenas.model.project.PostProjectDto
import com.github.mantasjasikenas.model.project.ProjectDto
import com.github.mantasjasikenas.model.project.UpdateProjectDto
import io.github.smiley4.ktorswaggerui.dsl.routes.OpenApiRoute
import io.ktor.http.*

fun projectRoutesDocs(): OpenApiRoute.() -> Unit = {
    description = "Project routes"
    tags("Projects")
}

fun getAllProjectsDocs(): OpenApiRoute.() -> Unit = {
    description = "Get all projects"
    response {
        HttpStatusCode.OK to {
            description = "All projects"
            body<ApiResponse<List<ProjectDto>>> {
                description = "API response with all projects"
                example("All projects") {
                    value = ApiResponse(
                        data = listOf(
                            ProjectDto(
                                id = 1,
                                name = "Project 1",
                                description = "Description 1",
                                createdAt = "2021-09-01T12:00:00Z",
                                createdBy = "user1"
                            ),
                            ProjectDto(
                                id = 2,
                                name = "Project 2",
                                description = "Description 2",
                                createdAt = "2022-09-01T12:00:00Z",
                                createdBy = "user2"
                            )
                        ),
                        success = true,
                        status = HttpStatusCode.OK.value.toString(),
                        message = "All projects"
                    )
                }
            }
        }
    }
}

fun postProjectDocs(): OpenApiRoute.() -> Unit = {
    description = "Create a new project"
    request {
        body<PostProjectDto> {
            description = "Project to create"
            example("New project") {
                value = PostProjectDto(
                    name = "New Project",
                    description = "New project description",
                    createdBy = "user1"
                )
            }
        }
    }
    response {
        HttpStatusCode.Created to {
            description = "Project created"
            body<ApiResponse<ProjectDto>> {
                example("Project created") {
                    value = ApiResponse(
                        data = ProjectDto(
                            id = 1,
                            name = "New Project",
                            description = "New project description",
                            createdAt = "2023-10-01T12:00:00Z",
                            createdBy = "user1"
                        ),
                        success = true,
                        status = HttpStatusCode.Created.value.toString(),
                        message = "Project created"
                    )
                }
            }
        }
        HttpStatusCode.BadRequest to {
            description = "Bad request"
            body<ApiResponse<String>> {
                example("Bad request") {
                    value = ApiResponse(
                        data = null,
                        success = false,
                        status = HttpStatusCode.BadRequest.value.toString(),
                        message = "Invalid project data"
                    )
                }
            }
        }
    }
}

fun getProjectByIdDocs(): OpenApiRoute.() -> Unit = {
    description = "Get project by id"

    request {
        queryParameter<Int>("id") {
            description = "Project id"
            required = true
            example("default") {
                value = 1
            }
        }
    }

    response {
        HttpStatusCode.OK to {
            description = "Project by id"
            body<ApiResponse<ProjectDto>> {
                example("Project by id") {
                    value = ApiResponse(
                        data = ProjectDto(
                            id = 1,
                            name = "Project 1",
                            description = "Description 1",
                            createdAt = "2021-09-01T12:00:00Z",
                            createdBy = "user1"
                        ),
                        success = true,
                        status = HttpStatusCode.OK.value.toString(),
                        message = "Project by id"
                    )
                }
            }
        }
        HttpStatusCode.BadRequest to {
            description = "Bad request"
            body<ApiResponse<String>> {
                example("Bad request") {
                    value = ApiResponse(
                        data = null,
                        success = false,
                        status = HttpStatusCode.BadRequest.value.toString(),
                        message = "Project id is required"
                    )
                }
            }
        }
        HttpStatusCode.NotFound to {
            description = "Project not found"
            body<ApiResponse<String>> {
                example("Project not found") {
                    value = ApiResponse(
                        data = null,
                        success = false,
                        status = HttpStatusCode.NotFound.value.toString(),
                        message = "Project not found"
                    )
                }
            }
        }
    }
}

fun updateProjectByIdDocs(): OpenApiRoute.() -> Unit = {
    description = "Update project by id"

    request {
        queryParameter<Int>("id") {
            description = "Project id"
            required = true
            example("default") {
                value = 1
            }
        }


        body<UpdateProjectDto> {
            description = "Project data to update"
            example("Update project") {
                value = UpdateProjectDto(
                    name = "Updated Project",
                    description = "Updated project description"
                )
            }
        }
    }

    response {
        HttpStatusCode.OK to {
            description = "Project updated"
            body<ApiResponse<ProjectDto>> {
                example("Project updated") {
                    value = ApiResponse(
                        data = ProjectDto(
                            id = 1,
                            name = "Updated Project",
                            description = "Updated project description",
                            createdAt = "2021-09-01T12:00:00Z",
                            createdBy = "user1"
                        ),
                        success = true,
                        status = HttpStatusCode.OK.value.toString(),
                        message = "Project updated"
                    )
                }
            }
        }
        HttpStatusCode.BadRequest to {
            description = "Bad request"
            body<ApiResponse<String>> {
                example("Bad request") {
                    value = ApiResponse(
                        data = null,
                        success = false,
                        status = HttpStatusCode.BadRequest.value.toString(),
                        message = "Invalid project data"
                    )
                }
            }
        }
        HttpStatusCode.NotFound to {
            description = "Project not found"
            body<ApiResponse<String>> {
                example("Project not found") {
                    value = ApiResponse(
                        data = null,
                        success = false,
                        status = HttpStatusCode.NotFound.value.toString(),
                        message = "Project not found"
                    )
                }
            }
        }
    }
}

fun deleteProjectByIdDocs(): OpenApiRoute.() -> Unit = {
    description = "Delete project by id"

    request {
        queryParameter<Int>("id") {
            description = "Project id"
            required = true
            example("default") {
                value = 1
            }
        }
    }

    response {
        HttpStatusCode.NoContent to {
            description = "Project deleted"
            body<ApiResponse<String>> {
                example("Project deleted") {
                    value = ApiResponse(
                        data = null,
                        success = true,
                        status = HttpStatusCode.NoContent.value.toString(),
                        message = "Project deleted"
                    )
                }
            }
        }
        HttpStatusCode.BadRequest to {
            description = "Bad request"
            body<ApiResponse<String>> {
                example("Bad request") {
                    value = ApiResponse(
                        data = null,
                        success = false,
                        status = HttpStatusCode.BadRequest.value.toString(),
                        message = "Project id is required"
                    )
                }
            }
        }
        HttpStatusCode.NotFound to {
            description = "Project not found"
            body<ApiResponse<String>> {
                example("Project not found") {
                    value = ApiResponse(
                        data = null,
                        success = false,
                        status = HttpStatusCode.NotFound.value.toString(),
                        message = "Project not found"
                    )
                }
            }
        }
    }
}