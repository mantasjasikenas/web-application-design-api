package com.github.mantasjasikenas.routes.task

import com.github.mantasjasikenas.data.*
import com.github.mantasjasikenas.data.task.PostTaskDto
import com.github.mantasjasikenas.data.task.UpdateTaskDto
import com.github.mantasjasikenas.docs.task.*
import com.github.mantasjasikenas.repository.ProjectRepository
import com.github.mantasjasikenas.repository.SectionRepository
import com.github.mantasjasikenas.repository.TaskRepository
import com.github.mantasjasikenas.routes.getProjectSectionIdOrRespond
import com.github.mantasjasikenas.routes.getProjectSectionTaskIdOrRespond
import com.github.mantasjasikenas.util.extractRolesOrRespond
import com.github.mantasjasikenas.util.extractSubjectOrRespond
import com.github.mantasjasikenas.util.extractSubjectWithRolesOrRespond
import io.github.smiley4.ktorswaggerui.dsl.routing.*
import io.ktor.http.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.routing.*

fun Route.taskRoutes(
    projectRepository: ProjectRepository,
    sectionRepository: SectionRepository,
    taskRepository: TaskRepository,
) {
    val routeService = TaskRoutesService(projectRepository, sectionRepository, taskRepository)

    authenticate {
        route(taskRoutesDocs()) {
            route("/projects/{projectId}/sections/{sectionId}/tasks") {
                get(getTasksByProjectAndSectionId()) {
                    val roles = call.extractRolesOrRespond() ?: return@get
                    val userId = call.extractSubjectOrRespond() ?: return@get

                    val (projectId, sectionId) = call.getProjectSectionIdOrRespond() ?: return@get
                    routeService.validateProjectAndSectionOrRespond(call, projectId, sectionId) ?: return@get

                    val tasks = if (roles.contains(Role.Admin)) {
                        taskRepository.allTasks(projectId, sectionId)
                    } else {
                        taskRepository.allUserTasks(userId, projectId, sectionId)
                    }

                    call.respondSuccess("Project section tasks", tasks)
                }

                post(postTaskDocs()) {
                    val (projectId, sectionId) = call.getProjectSectionIdOrRespond() ?: return@post
                    routeService.validateProjectAndSectionOrRespond(call, projectId, sectionId) ?: return@post

                    val userId = call.extractSubjectOrRespond() ?: return@post
                    val taskDto = call.receive<PostTaskDto>()

                    val task = taskRepository.addTask(userId, projectId, sectionId, taskDto)

                    if (task == null) {
                        call.respondNotFound("Task not found")
                        return@post
                    }

                    call.respondCreated("Task created", task)
                }

                get("/{taskId}", getTaskByIdDocs()) {
                    val (projectId, sectionId, taskId) = call.getProjectSectionTaskIdOrRespond() ?: return@get
                    val (userId, roles) = call.extractSubjectWithRolesOrRespond() ?: return@get

                    val task = routeService.validateOrRespond(call, projectId, sectionId, taskId) ?: return@get
                    routeService.authorizeOrRespond(call, task, userId, roles) ?: return@get

                    call.respondSuccess("Task by id", task)
                }


                patch("/{taskId}", updateTaskByIdDocs()) {
                    val updateTaskDto = call.receive<UpdateTaskDto>()

                    val (projectId, sectionId, taskId) = call.getProjectSectionTaskIdOrRespond() ?: return@patch
                    val (userId, roles) = call.extractSubjectWithRolesOrRespond() ?: return@patch

                    val taskDto = routeService.validateOrRespond(call, projectId, sectionId, taskId) ?: return@patch
                    routeService.authorizeOrRespond(call, taskDto, userId, roles) ?: return@patch

                    val updatedTask = taskRepository.updateTask(projectId, sectionId, taskId, updateTaskDto)

                    if (updatedTask == null) {
                        call.respondNotFound("Task not found")
                        return@patch
                    }

                    call.respondSuccess("Task updated", updatedTask)
                }

                delete("/{taskId}", deleteTaskByIdDocs()) {
                    val (projectId, sectionId, taskId) = call.getProjectSectionTaskIdOrRespond() ?: return@delete
                    val (userId, roles) = call.extractSubjectWithRolesOrRespond() ?: return@delete

                    val taskDto = routeService.validateOrRespond(call, projectId, sectionId, taskId) ?: return@delete
                    routeService.authorizeOrRespond(call, taskDto, userId, roles) ?: return@delete

                    val removed = taskRepository.removeTask(projectId, sectionId, taskId)

                    if (!removed) {
                        call.respondNotFound("Task not found")
                        return@delete
                    }

                    call.respondCustom(HttpStatusCode.NoContent, "Task removed")
                }
            }
        }

    }
}