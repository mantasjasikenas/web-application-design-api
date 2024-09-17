package com.github.mantasjasikenas.routes

import com.github.mantasjasikenas.data.TaskRepository
import com.github.mantasjasikenas.data.TaskRepositoryImpl
import com.github.mantasjasikenas.model.PostTaskDto
import com.github.mantasjasikenas.model.UpdateTaskDto
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Routing.taskRoutes() {
    val taskRepository: TaskRepository = TaskRepositoryImpl()

    // TODO: move elsewhere
    post("/sections/{sectionId}/tasks") {
        val sectionId = call.parameters["sectionId"]?.toInt()

        if (sectionId == null) {
            call.respond(HttpStatusCode.BadRequest)
            return@post
        }

        val taskDto = call.receive<PostTaskDto>()
        val id = taskRepository.addTask(sectionId, taskDto)

        call.respond(HttpStatusCode.Created, id)
    }

    route("/tasks") {
        get {
            val tasks = taskRepository.allTasks()

            call.respond(tasks)
        }

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

        put("/tasks/{id}") {
            val id = call.parameters["id"]?.toInt()
            val taskDto = call.receive<UpdateTaskDto>()

            if (id == null) {
                call.respond(HttpStatusCode.BadRequest)
                return@put
            }

            taskRepository.updateTask(id, taskDto)

            call.respond(HttpStatusCode.OK)
        }

        delete("/tasks/{id}") {
            val id = call.parameters["id"]?.toInt()

            if (id == null) {
                call.respond(HttpStatusCode.BadRequest)
                return@delete
            }

            taskRepository.removeTask(id)

            call.respond(HttpStatusCode.OK)
        }
    }

}
