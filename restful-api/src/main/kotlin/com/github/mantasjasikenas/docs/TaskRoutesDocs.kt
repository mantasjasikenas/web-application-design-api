package com.github.mantasjasikenas.docs

import com.github.mantasjasikenas.model.ApiResponse
import com.github.mantasjasikenas.model.task.PostTaskDto
import com.github.mantasjasikenas.model.task.Priority
import com.github.mantasjasikenas.model.task.TaskDto
import com.github.mantasjasikenas.model.task.UpdateTaskDto
import io.github.smiley4.ktorswaggerui.dsl.routes.OpenApiRoute
import io.ktor.http.*

fun taskRoutesDocs(): OpenApiRoute.() -> Unit = {
    description = "Task routes"
    tags("Tasks")
}

fun postTaskDocs(): OpenApiRoute.() -> Unit = {
    description = "Create a new task"

    request {
        queryParameter<Int>("sectionId") {
            description = "Section id"
            required = true
            example("default") {
                value = 1
            }
        }

        body<PostTaskDto> {
            description = "Task to create"
            example("New task") {
                value = PostTaskDto(
                    name = "New Task",
                    description = "New task description",
                    priority = Priority.Vital,
                    isCompleted = true,
                    dueDate = "2023-10-01T12:00:00Z",
                    createdBy = "user1",
                )
            }
        }
    }
    response {
        HttpStatusCode.Created to {
            description = "Task created"
            body<ApiResponse<TaskDto>> {
                example("Task created") {
                    value = ApiResponse(
                        data = TaskDto(
                            id = 1,
                            name = "New Task",
                            description = "New task description",
                            createdAt = "2023-10-01T12:00:00Z",
                            sectionId = 1,
                            priority = Priority.Low,
                            isCompleted = false,
                            dueDate = "2023-10-01T12:00:00Z",
                            createdBy = "user1",
                        ),
                        success = true,
                        status = HttpStatusCode.Created.value.toString(),
                        message = "Task created"
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
                        message = "Invalid task data"
                    )
                }
            }
        }
    }
}

fun getTasksByProjectAndSectionId(): OpenApiRoute.() -> Unit = {
    description = "Get tasks by project and section id"

    request {
        queryParameter<Int>("projectId") {
            description = "Project id"
            required = true
            example("default") {
                value = 1
            }
        }
        queryParameter<Int>("sectionId") {
            description = "Section id"
            required = true
            example("default") {
                value = 1
            }
        }
    }

    response {
        HttpStatusCode.OK to {
            description = "All tasks"

            body<ApiResponse<List<TaskDto>>> {
                description = "API response with all tasks"
                example("All tasks") {
                    value = ApiResponse(
                        data = listOf(
                            TaskDto(
                                id = 1,
                                name = "Task 1",
                                description = "Description 1",
                                createdAt = "2021-09-01T12:00:00Z",
                                sectionId = 4,
                                priority = Priority.Vital,
                                isCompleted = false,
                                dueDate = "2023-10-01T12:00:00Z",
                                createdBy = "user1",
                            ),
                            TaskDto(
                                id = 2,
                                name = "Task 2",
                                description = "Description 2",
                                createdAt = "2022-09-01T12:00:00Z",
                                sectionId = 4,
                                priority = Priority.Vital,
                                isCompleted = false,
                                dueDate = "2023-10-01T12:00:00Z",
                                createdBy = "user1",
                            )
                        ),
                        success = true,
                        status = HttpStatusCode.OK.value.toString(),
                        message = "All tasks"
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
                        message = "Task id is required"
                    )
                }
            }
        }
    }
}

fun getAllTasksDocs(): OpenApiRoute.() -> Unit = {
    description = "Get all tasks"
    response {
        HttpStatusCode.OK to {
            description = "All tasks"
            body<ApiResponse<List<TaskDto>>> {
                description = "API response with all tasks"
                example("All tasks") {
                    value = ApiResponse(
                        data = listOf(
                            TaskDto(
                                id = 1,
                                name = "Task 1",
                                description = "Description 1",
                                createdAt = "2021-09-01T12:00:00Z",
                                sectionId = 4,
                                priority = Priority.Vital,
                                isCompleted = false,
                                dueDate = "2023-10-01T12:00:00Z",
                                createdBy = "user1",
                            ),
                            TaskDto(
                                id = 2,
                                name = "Task 2",
                                description = "Description 2",
                                createdAt = "2022-09-01T12:00:00Z",
                                sectionId = 4,
                                priority = Priority.Vital,
                                isCompleted = false,
                                dueDate = "2023-10-01T12:00:00Z",
                                createdBy = "user1",
                            )
                        ),
                        success = true,
                        status = HttpStatusCode.OK.value.toString(),
                        message = "All tasks"
                    )
                }
            }
        }
    }
}

fun getTaskByIdDocs(): OpenApiRoute.() -> Unit = {
    description = "Get task by id"

    request {
        queryParameter<Int>("id") {
            description = "Task id"
            required = true
            example("default") {
                value = 1
            }
        }
    }

    response {
        HttpStatusCode.OK to {
            description = "Task by id"
            body<ApiResponse<TaskDto>> {
                example("Task by id") {
                    value = ApiResponse(
                        data = TaskDto(
                            id = 1,
                            name = "Task 1",
                            description = "Description 1",
                            createdAt = "2021-09-01T12:00:00Z",
                            sectionId = 4,
                            priority = Priority.Vital,
                            isCompleted = false,
                            dueDate = "2023-10-01T12:00:00Z",
                            createdBy = "user1",
                        ),
                        success = true,
                        status = HttpStatusCode.OK.value.toString(),
                        message = "Task by id"
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
                        message = "Task id is required"
                    )
                }
            }
        }
        HttpStatusCode.NotFound to {
            description = "Task not found"
            body<ApiResponse<String>> {
                example("Task not found") {
                    value = ApiResponse(
                        data = null,
                        success = false,
                        status = HttpStatusCode.NotFound.value.toString(),
                        message = "Task not found"
                    )
                }
            }
        }
    }
}

fun updateTaskByIdDocs(): OpenApiRoute.() -> Unit = {
    description = "Update task by id"

    request {
        queryParameter<Int>("id") {
            description = "Task id"
            required = true
            example("default") {
                value = 1
            }
        }
    }

    request {
        body<UpdateTaskDto> {
            description = "Task data to update"
            example("Update task") {
                value = UpdateTaskDto(
                    name = "Updated Task",
                    description = "Updated task description",
                    priority = Priority.Vital,
                    isCompleted = false,
                    dueDate = "2023-10-01T12:00:00Z",
                    sectionId = 1,
                )
            }
        }
    }
    response {
        HttpStatusCode.OK to {
            description = "Task updated"
            body<ApiResponse<TaskDto>> {
                example("Task updated") {
                    value = ApiResponse(
                        data = TaskDto(
                            id = 1,
                            name = "Updated Task",
                            description = "Updated task description",
                            createdAt = "2021-09-01T12:00:00Z",
                            sectionId = 4,
                            priority = Priority.Vital,
                            isCompleted = false,
                            dueDate = "2023-10-01T12:00:00Z",
                            createdBy = "user1",
                        ),
                        success = true,
                        status = HttpStatusCode.OK.value.toString(),
                        message = "Task updated"
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
                        message = "Invalid task data"
                    )
                }
            }
        }
        HttpStatusCode.NotFound to {
            description = "Task not found"
            body<ApiResponse<String>> {
                example("Task not found") {
                    value = ApiResponse(
                        data = null,
                        success = false,
                        status = HttpStatusCode.NotFound.value.toString(),
                        message = "Task not found"
                    )
                }
            }
        }
    }
}

fun deleteTaskByIdDocs(): OpenApiRoute.() -> Unit = {
    description = "Delete task by id"

    request {
        queryParameter<Int>("id") {
            description = "Task id"
            required = true
            example("default") {
                value = 1
            }
        }
    }

    response {
        HttpStatusCode.NoContent to {
            description = "Task deleted"
            body<ApiResponse<String>> {
                example("Task deleted") {
                    value = ApiResponse(
                        data = null,
                        success = true,
                        status = HttpStatusCode.NoContent.value.toString(),
                        message = "Task deleted"
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
                        message = "Task id is required"
                    )
                }
            }
        }
        HttpStatusCode.NotFound to {
            description = "Task not found"
            body<ApiResponse<String>> {
                example("Task not found") {
                    value = ApiResponse(
                        data = null,
                        success = false,
                        status = HttpStatusCode.NotFound.value.toString(),
                        message = "Task not found"
                    )
                }
            }
        }
    }
}