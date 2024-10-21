package com.github.mantasjasikenas.routes.task

import com.github.mantasjasikenas.data.Role
import com.github.mantasjasikenas.data.respondForbidden
import com.github.mantasjasikenas.data.respondNotFound
import com.github.mantasjasikenas.data.task.TaskDto
import com.github.mantasjasikenas.repository.ProjectRepository
import com.github.mantasjasikenas.repository.SectionRepository
import com.github.mantasjasikenas.repository.TaskRepository
import io.ktor.server.application.*

class TaskRoutesService(
    private val projectRepository: ProjectRepository,
    private val sectionRepository: SectionRepository,
    private val taskRepository: TaskRepository,
) {
    private fun isUserAuthorized(
        task: TaskDto,
        userId: String,
        userRoles: List<Role>?
    ): Boolean {
        if (userRoles == null) {
            return false
        }

        return Role.Admin in userRoles || (Role.User in userRoles && task.createdBy == userId)
    }

    suspend fun authorizeOrRespond(
        call: ApplicationCall,
        task: TaskDto,
        userId: String,
        userRoles: List<Role>?
    ): Unit? {
        if (!isUserAuthorized(task, userId, userRoles)) {
            call.respondForbidden()
            return null
        }

        return Unit
    }

    suspend fun validateProjectAndSectionOrRespond(call: ApplicationCall, projectId: Int, sectionId: Int): Unit? {
        if (!projectRepository.projectExists(projectId)) {
            call.respondNotFound("Project not found")
            return null
        }

        if (!sectionRepository.sectionExists(sectionId)) {
            call.respondNotFound("Section not found")
            return null
        }

        return Unit
    }

    suspend fun validateOrRespond(
        call: ApplicationCall,
        projectId: Int,
        sectionId: Int,
        taskId: Int
    ): TaskDto? {

        if (validateProjectAndSectionOrRespond(call, projectId, sectionId) == null) {
            return null
        }

        val task = taskRepository.taskById(projectId, sectionId, taskId) ?: run {
            call.respondNotFound("Task not found")
            return null
        }

        return task
    }
}