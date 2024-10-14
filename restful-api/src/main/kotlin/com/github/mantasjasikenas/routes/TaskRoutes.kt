package com.github.mantasjasikenas.routes

import com.github.mantasjasikenas.repository.ProjectRepository
import com.github.mantasjasikenas.repository.SectionRepository
import com.github.mantasjasikenas.repository.TaskRepository
import com.github.mantasjasikenas.docs.task.*
import com.github.mantasjasikenas.model.respondCreated
import com.github.mantasjasikenas.model.respondCustom
import com.github.mantasjasikenas.model.respondNotFound
import com.github.mantasjasikenas.model.respondSuccess
import com.github.mantasjasikenas.model.task.PostTaskDto
import com.github.mantasjasikenas.model.task.UpdateTaskDto
import io.github.smiley4.ktorswaggerui.dsl.routing.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.routing.*

fun Route.taskRoutes(
    projectRepository: ProjectRepository,
    sectionRepository: SectionRepository,
    taskRepository: TaskRepository,
) {
    route(taskRoutesDocs()) {
        route("/projects/{projectId}/sections/{sectionId}/tasks") {
            get(getTasksByProjectAndSectionId()) {
                val projectId = call.validateAndGetProjectId(projectRepository) ?: return@get
                val sectionId = call.validateAndGetSectionId(sectionRepository) ?: return@get

                val tasks = taskRepository.allTasks(projectId, sectionId)

                call.respondSuccess("Project section tasks", tasks)
            }

            get("/{taskId}", getTaskByIdDocs()) {
                val projectId = call.validateAndGetProjectId(projectRepository) ?: return@get
                val sectionId = call.validateAndGetSectionId(sectionRepository) ?: return@get

                val taskId = call.getTaskId() ?: return@get

                val task = taskRepository.taskById(projectId, sectionId, taskId)

                if (task != null) {
                    call.respondSuccess("Task by id", task)
                } else {
                    call.respondNotFound("Task not found")
                }
            }

            post(postTaskDocs()) {
                val projectId = call.validateAndGetProjectId(projectRepository) ?: return@post
                val sectionId = call.validateAndGetSectionId(sectionRepository) ?: return@post

                val taskDto = call.receive<PostTaskDto>()
                val task = taskRepository.addTask(projectId, sectionId, taskDto)

                if (task == null) {
                    call.respondNotFound("Task not found")
                    return@post
                } else {
                    call.respondCreated("Task created", task)
                }
            }

            patch("/{taskId}", updateTaskByIdDocs()) {
                val projectId = call.validateAndGetProjectId(projectRepository) ?: return@patch
                val sectionId = call.validateAndGetSectionId(sectionRepository) ?: return@patch

                val taskId = call.getTaskId() ?: return@patch

                val taskDto = call.receive<UpdateTaskDto>()

                val updatedTask = taskRepository.updateTask(projectId, sectionId, taskId, taskDto)

                if (updatedTask != null) {
                    call.respondSuccess("Task updated", updatedTask)
                } else {
                    call.respondNotFound("Task not found")
                }
            }

            delete("/{taskId}", deleteTaskByIdDocs()) {
                val projectId = call.validateAndGetProjectId(projectRepository) ?: return@delete
                val sectionId = call.validateAndGetSectionId(sectionRepository) ?: return@delete

                val taskId = call.getTaskId() ?: return@delete

                val removed = taskRepository.removeTask(projectId, sectionId, taskId)

                if (removed) {
                    call.respondCustom(HttpStatusCode.NoContent, "Task removed")
                } else {
                    call.respondNotFound("Task not found")
                }
            }
        }
    }
}
