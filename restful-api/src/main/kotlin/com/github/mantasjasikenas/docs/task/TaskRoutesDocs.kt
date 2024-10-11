package com.github.mantasjasikenas.docs.task

import com.github.mantasjasikenas.docs.*
import com.github.mantasjasikenas.model.task.PostTaskDto
import com.github.mantasjasikenas.model.task.Priority
import com.github.mantasjasikenas.model.task.UpdateTaskDto
import io.github.smiley4.ktorswaggerui.dsl.routes.OpenApiRoute


fun taskRoutesDocs(): OpenApiRoute.() -> Unit = {
    description = "Task routes"
    tags("Tasks")
}

fun postTaskDocs(): OpenApiRoute.() -> Unit = {
    description = "Create a new task"

    request {
        projectIdPathParameter()

        sectionIdPathParameter()

        body<PostTaskDto> {
            description = "Task to create"
            example("default") {
                value = postTaskDtoExample
            }
        }
    }
    response {
        createdResponse(description = "Task created", message = "Task created", data = taskDtoExample)

        badRequestResponse(message = "Invalid task data")

        unprocessableEntityResponse(message = "Validation failed")
    }
}

fun getTasksByProjectAndSectionId(): OpenApiRoute.() -> Unit = {
    description = "Get tasks by project and section id"

    request {
        projectIdPathParameter()

        sectionIdPathParameter()
    }

    response {
        okResponse(
            description = "All tasks",
            message = "All tasks",
            data = listOf(taskDtoExample, taskDtoExample2)
        )

        badRequestResponse(message = "Both project id and section id are required")
    }
}

fun getTaskByIdDocs(): OpenApiRoute.() -> Unit = {
    description = "Get task by id"

    request {
        projectIdPathParameter()

        sectionIdPathParameter()

        taskIdPathParameter()
    }

    response {
        okResponse(
            description = "Task by id",
            message = "Task by id",
            data = taskDtoExample
        )

        badRequestResponse(message = "Task id is required")

        notFoundResponse(message = "Task not found")
    }
}

fun updateTaskByIdDocs(): OpenApiRoute.() -> Unit = {
    description = "Update task by id"

    request {
        projectIdPathParameter()

        sectionIdPathParameter()

        taskIdPathParameter()

        body<UpdateTaskDto> {
            description = "Task data to update"
            example("Update task") {
                value = updateTaskDtoExample
            }
        }
    }
    response {
        okResponse(
            description = "Task updated",
            message = "Task updated",
            data = taskDtoExample.copy(
                priority = Priority.Medium,
                completed = true
            )
        )

        badRequestResponse(message = "Invalid task data")

        unprocessableEntityResponse(message = "Validation failed")

        notFoundResponse(message = "Task not found")
    }
}

fun deleteTaskByIdDocs(): OpenApiRoute.() -> Unit = {
    description = "Delete task by id"

    request {
        projectIdPathParameter()

        sectionIdPathParameter()

        taskIdPathParameter()
    }

    response {
        noContentResponse(message = "Task deleted")

        badRequestResponse(message = "Task id is required")

        notFoundResponse(message = "Task not found")
    }
}