package com.github.mantasjasikenas.routes

import com.github.mantasjasikenas.data.TaskRepository
import com.github.mantasjasikenas.model.PostTaskDto
import com.github.mantasjasikenas.model.TaskDto
import com.github.mantasjasikenas.model.UpdateTaskDto
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
            call.respond(HttpStatusCode.BadRequest)
            return@post
        }

        val taskDto = call.receive<PostTaskDto>()
        val task = taskRepository.addTask(sectionId, taskDto)

        call.respond(HttpStatusCode.Created, task)
    }

//    GET /projects/{projectId}/sections/{sectionId}/tasks
    @KtorResponds(
        [
            ResponseEntry("200", TaskDto::class, true, description = "All tasks")
        ]
    )
    get("/projects/{projectId}/sections/{sectionId}/tasks") {
        val projectId = call.parameters["projectId"]?.toInt()
        val sectionId = call.parameters["sectionId"]?.toInt()

        if (projectId == null || sectionId == null) {
            call.respond(HttpStatusCode.BadRequest)
            return@get
        }

        val tasks = taskRepository.allTasks(projectId, sectionId)

        call.respond(tasks)
    }


    route("/tasks") {
        @KtorResponds(
            [
                ResponseEntry("200", TaskDto::class, true, description = "All tasks")
            ]
        )
        get {
            val tasks = taskRepository.allTasks()

            call.respond(tasks)
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
                call.respond(HttpStatusCode.BadRequest)
                return@get
            }

            val task = taskRepository.taskById(id)

            if (task != null) {
                call.respond(HttpStatusCode.OK, task)
            } else {
                call.respond(HttpStatusCode.NotFound)
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
                call.respond(HttpStatusCode.BadRequest)
                return@put
            }

            val updatedTask = taskRepository.updateTask(id, taskDto)

            if (updatedTask != null) {
                call.respond(HttpStatusCode.OK, updatedTask)
            } else {
                call.respond(HttpStatusCode.NotFound)
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
                call.respond(HttpStatusCode.BadRequest)
                return@delete
            }

            val removed = taskRepository.removeTask(id)

            if (removed) {
                call.respond(HttpStatusCode.OK)
            } else {
                call.respond(HttpStatusCode.NotFound)
            }
        }
    }

}
