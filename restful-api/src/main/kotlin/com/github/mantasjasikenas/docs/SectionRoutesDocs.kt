﻿package com.github.mantasjasikenas.docs

import com.github.mantasjasikenas.model.ApiResponse
import com.github.mantasjasikenas.model.section.PostSectionDto
import com.github.mantasjasikenas.model.section.SectionDto
import com.github.mantasjasikenas.model.section.UpdateSectionDto
import io.github.smiley4.ktorswaggerui.dsl.routes.OpenApiRoute
import io.ktor.http.*

fun sectionRoutesDocs(): OpenApiRoute.() -> Unit = {
    description = "Section routes"
    tags("Sections")
}

fun createSectionDocs(): OpenApiRoute.() -> Unit = {
    description = "Create a new section"
    request {
        queryParameter<Int>("projectId") {
            description = "Project id"
            required = true
            example("default") {
                value = 1
            }
        }
        body<PostSectionDto> {
            description = "Section to create"
            example("New section") {
                value = PostSectionDto(
                    name = "New Section",
                    createdBy = "user1"
                )
            }
        }
    }
    response {
        HttpStatusCode.Created to {
            description = "Section created"
            body<ApiResponse<SectionDto>> {
                example("Section created") {
                    value = ApiResponse(
                        data = SectionDto(
                            id = 1,
                            name = "New Section",
                            projectId = 1,
                            createdBy = "user1",
                            createdAt = "2021-09-01T12:00:00Z"
                        ),
                        success = true,
                        status = HttpStatusCode.Created.value.toString(),
                        message = "Section created"
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
                        message = "Invalid section data"
                    )
                }
            }
        }
    }
}

fun getAllSectionsDocs(): OpenApiRoute.() -> Unit = {
    description = "Get all sections"
    response {
        HttpStatusCode.OK to {
            description = "All sections"
            body<ApiResponse<List<SectionDto>>> {
                description = "API response with all sections"
                example("All sections") {
                    value = ApiResponse(
                        data = listOf(
                            SectionDto(
                                id = 1,
                                name = "Section 1",
                                projectId = 1,
                                createdBy = "user1",
                                createdAt = "2021-09-01T12:00:00Z"
                            ),
                            SectionDto(
                                id = 2,
                                name = "Section 2",
                                projectId = 1,
                                createdBy = "user1",
                                createdAt = "2021-09-01T12:00:00Z"
                            )
                        ),
                        success = true,
                        status = HttpStatusCode.OK.value.toString(),
                        message = "All sections"
                    )
                }
            }
        }
    }
}

fun getSectionByIdDocs(): OpenApiRoute.() -> Unit = {
    description = "Get section by id"
    request {
        queryParameter<Int>("id") {
            description = "Section id"
            required = true
            example("default") {
                value = 1
            }
        }
    }
    response {
        HttpStatusCode.OK to {
            description = "Section by id"
            body<ApiResponse<SectionDto>> {
                example("Section by id") {
                    value = ApiResponse(
                        data = SectionDto(
                            id = 1,
                            name = "Section 1",
                            projectId = 1,
                            createdBy = "user1",
                            createdAt = "2021-09-01T12:00:00Z"
                        ),
                        success = true,
                        status = HttpStatusCode.OK.value.toString(),
                        message = "Section by id"
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
                        message = "Section id is required"
                    )
                }
            }
        }
        HttpStatusCode.NotFound to {
            description = "Section not found"
            body<ApiResponse<String>> {
                example("Section not found") {
                    value = ApiResponse(
                        data = null,
                        success = false,
                        status = HttpStatusCode.NotFound.value.toString(),
                        message = "Section not found"
                    )
                }
            }
        }
    }
}

fun updateSectionByIdDocs(): OpenApiRoute.() -> Unit = {
    description = "Update section by id"
    request {
        queryParameter<Int>("sectionId") {
            description = "Section id"
            required = true
            example("default") {
                value = 1
            }
        }
        body<UpdateSectionDto> {
            description = "Section data to update"
            example("Update section") {
                value = UpdateSectionDto(
                    name = "Updated Section"
                )
            }
        }
    }
    response {
        HttpStatusCode.OK to {
            description = "Section updated"
            body<ApiResponse<SectionDto>> {
                example("Section updated") {
                    value = ApiResponse(
                        data = SectionDto(
                            id = 1,
                            name = "Updated Section",
                            projectId = 1,
                            createdBy = "user1",
                            createdAt = "2021-09-01T12:00:00Z"
                        ),
                        success = true,
                        status = HttpStatusCode.OK.value.toString(),
                        message = "Section updated"
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
                        message = "Invalid section data"
                    )
                }
            }
        }
        HttpStatusCode.NotFound to {
            description = "Section not found"
            body<ApiResponse<String>> {
                example("Section not found") {
                    value = ApiResponse(
                        data = null,
                        success = false,
                        status = HttpStatusCode.NotFound.value.toString(),
                        message = "Section not found"
                    )
                }
            }
        }
    }
}

fun deleteSectionByIdDocs(): OpenApiRoute.() -> Unit = {
    description = "Delete section by id"
    request {
        queryParameter<Int>("id") {
            description = "Section id"
            required = true
            example("default") {
                value = 1
            }
        }
    }
    response {
        HttpStatusCode.NoContent to {
            description = "Section deleted"
            body<ApiResponse<String>> {
                example("Section deleted") {
                    value = ApiResponse(
                        data = null,
                        success = true,
                        status = HttpStatusCode.NoContent.value.toString(),
                        message = "Section deleted"
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
                        message = "Section id is required"
                    )
                }
            }
        }
        HttpStatusCode.NotFound to {
            description = "Section not found"
            body<ApiResponse<String>> {
                example("Section not found") {
                    value = ApiResponse(
                        data = null,
                        success = false,
                        status = HttpStatusCode.NotFound.value.toString(),
                        message = "Section not found"
                    )
                }
            }
        }
    }
}