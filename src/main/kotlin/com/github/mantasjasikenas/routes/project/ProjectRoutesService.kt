package com.github.mantasjasikenas.routes.project

import com.github.mantasjasikenas.data.Role
import com.github.mantasjasikenas.data.project.ProjectDto
import com.github.mantasjasikenas.data.respondForbidden
import com.github.mantasjasikenas.data.respondNotFound
import com.github.mantasjasikenas.repository.ProjectRepository
import io.ktor.server.application.*

class ProjectRoutesService(
    private val projectRepository: ProjectRepository,
) {
    private fun isUserAuthorized(
        project: ProjectDto,
        userId: String,
        userRoles: List<Role>?
    ): Boolean {
        if (userRoles == null) {
            return false
        }

        return Role.Admin in userRoles || (Role.User in userRoles && project.createdBy == userId)
    }

    suspend fun authorizeOrRespond(
        call: ApplicationCall,
        project: ProjectDto,
        userId: String,
        userRoles: List<Role>?
    ): ProjectDto? {
        if (!isUserAuthorized(project, userId, userRoles)) {
            call.respondForbidden("You are not allowed to access this project")
            return null
        }

        return project
    }

    suspend fun validateOrRespond(
        call: ApplicationCall,
        projectId: Int,
    ): ProjectDto? {
        val project = projectRepository.projectById(projectId) ?: run {
            call.respondNotFound("Project not found")
            return null
        }

        return project
    }
}