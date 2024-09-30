package com.github.mantasjasikenas.routes

import com.github.mantasjasikenas.data.TaskRepository
import com.github.mantasjasikenas.model.*
import io.github.tabilzad.ktor.annotations.KtorResponds
import io.github.tabilzad.ktor.annotations.ResponseEntry
import io.github.tabilzad.ktor.annotations.Tag
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

@Tag(["Tasks"])
fun Route.taskRoutes(taskRepository: TaskRepository) {
    @KtorResponds(
        [
            ResponseEntry("201", TaskDto::class, description = "Task created"),
            ResponseEntry("400", String::class, description = "Bad request")
        ]
    )
    post("/sections/{sectionId}/tasks") {
        val sectionId = call.parameters["sectionId"]?.toInt()

        if (sectionId == null) {
            call.respondBadRequest("Section id is required")
            return@post
        }

        val taskDto = call.receive<PostTaskDto>()
        val task = taskRepository.addTask(sectionId, taskDto)

        call.respondCreated("Task created", task)
    }

    @KtorResponds(
        [
            ResponseEntry("200", TaskDto::class, true, description = "All tasks")
        ]
    )
    get("/projects/{projectId}/sections/{sectionId}/tasks") {
        val projectId = call.parameters["projectId"]?.toInt()
        val sectionId = call.parameters["sectionId"]?.toInt()

        if (projectId == null || sectionId == null) {
            call.respondBadRequest("Project id and section id are required")
            return@get
        }

        val tasks = taskRepository.allTasks(projectId, sectionId)

        call.respondSuccess("Project section tasks", tasks)
    }


    route("/tasks") {
        @KtorResponds(
            [
                ResponseEntry("200", TaskDto::class, true, description = "All tasks")
            ]
        )
        get {
            val tasks = taskRepository.allTasks()

            call.respondSuccess("All tasks", tasks)
        }

        @KtorResponds(
            [
                ResponseEntry("200", TaskDto::class, true, description = "Task by id"),
                ResponseEntry("400", String::class, description = "Bad request"),
                ResponseEntry("404", String::class, description = "Not found")
            ]
        )
        get("/{id}") {
            val id = call.parameters["id"]?.toInt()

            if (id == null) {
                call.respondBadRequest("Task id is required")
                return@get
            }

            val task = taskRepository.taskById(id)

            if (task != null) {
                call.respondSuccess("Task by id", task)
            } else {
                call.respondNotFound("Task not found")
            }
        }

        @KtorResponds(
            [
                ResponseEntry("200", TaskDto::class, description = "Task updated"),
                ResponseEntry("400", String::class, description = "Bad request"),
                ResponseEntry("404", String::class, description = "Not found")
            ]
        )
        put("/{id}") {
            val id = call.parameters["id"]?.toInt()
            val taskDto = call.receive<UpdateTaskDto>()

            if (id == null) {
                call.respondBadRequest("Task id is required")
                return@put
            }

            val updatedTask = taskRepository.updateTask(id, taskDto)

            if (updatedTask != null) {
                call.respondSuccess("Task updated", updatedTask)
            } else {
                call.respondNotFound("Task not found")
            }
        }

        @KtorResponds(
            [
                ResponseEntry("200", String::class, description = "Task removed"),
                ResponseEntry("404", String::class, description = "Not found"),
                ResponseEntry("400", String::class, description = "Bad request")
            ]
        )
        delete("/{id}") {
            val id = call.parameters["id"]?.toInt()

            if (id == null) {
                call.respondBadRequest("Task id is required")
                return@delete
            }

            val removed = taskRepository.removeTask(id)

            if (removed) {
                call.respondCustom(HttpStatusCode.NoContent, "Task removed")
            } else {
                call.respondNotFound("Task not found")
            }
        }
    }

}
