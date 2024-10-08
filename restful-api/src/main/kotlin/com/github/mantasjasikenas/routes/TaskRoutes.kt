package com.github.mantasjasikenas.routes

import com.github.mantasjasikenas.data.SectionRepository
import com.github.mantasjasikenas.data.TaskRepository
import com.github.mantasjasikenas.docs.*
import com.github.mantasjasikenas.model.*
import com.github.mantasjasikenas.model.task.PostTaskDto
import com.github.mantasjasikenas.model.task.UpdateTaskDto
import io.github.smiley4.ktorswaggerui.dsl.routing.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.routing.*

fun Route.taskRoutes(
    taskRepository: TaskRepository,
    sectionRepository: SectionRepository,
) {
    route(taskRoutesDocs()) {
        post("/sections/{id}/tasks", postTaskDocs()) {
            val sectionId = call.parameters["id"]?.toInt()

            if (sectionId == null) {
                call.respondBadRequest("Section id is required")
                return@post
            }

            sectionRepository.sectionById(sectionId) ?: run {
                call.respondNotFound("Section not found. Please provide a valid section id")
                return@post
            }

            val taskDto = call.receive<PostTaskDto>()
            val task = taskRepository.addTask(sectionId, taskDto)

            call.respondCreated("Task created", task)
        }

        get("/projects/{projectId}/sections/{sectionId}/tasks", getTasksByProjectAndSectionId()) {
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
            get(getAllTasksDocs()) {
                val tasks = taskRepository.allTasks()

                call.respondSuccess("All tasks", tasks)
            }

            get("/{id}", getTaskByIdDocs()) {
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

            patch("/{id}", updateTaskByIdDocs()) {
                val id = call.parameters["id"]?.toInt()
                val taskDto = call.receive<UpdateTaskDto>()

                if (id == null) {
                    call.respondBadRequest("Task id is required")
                    return@patch
                }

                taskDto.sectionId?.let { sectionRepository.sectionById(it) } ?: run {
                    call.respondNotFound("Section not found. Please provide a valid section id")
                    return@patch
                }

                val updatedTask = taskRepository.updateTask(id, taskDto)

                if (updatedTask != null) {
                    call.respondSuccess("Task updated", updatedTask)
                } else {
                    call.respondNotFound("Task not found")
                }
            }

            delete("/{id}", deleteTaskByIdDocs()) {
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
}
