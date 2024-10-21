package com.github.mantasjasikenas.routes.section

import com.github.mantasjasikenas.data.Role
import com.github.mantasjasikenas.data.respondForbidden
import com.github.mantasjasikenas.data.respondNotFound
import com.github.mantasjasikenas.data.section.SectionDto
import com.github.mantasjasikenas.repository.ProjectRepository
import com.github.mantasjasikenas.repository.SectionRepository
import io.ktor.server.application.*

class SectionRoutesService(
    private val projectRepository: ProjectRepository,
    private val sectionRepository: SectionRepository,
) {
    private fun isUserAuthorized(
        section: SectionDto,
        userId: String,
        userRoles: List<Role>?
    ): Boolean {
        if (userRoles == null) {
            return false
        }

        return Role.Admin in userRoles || (Role.User in userRoles && section.createdBy == userId)
    }

    suspend fun authorizeOrRespond(
        call: ApplicationCall,
        section: SectionDto,
        userId: String,
        userRoles: List<Role>?
    ): Unit? {
        if (!isUserAuthorized(section, userId, userRoles)) {
            call.respondForbidden()
            return null
        }

        return Unit
    }

    suspend fun validateProjectOrRespond(call: ApplicationCall, projectId: Int): Unit? {
        if (!projectRepository.projectExists(projectId)) {
            call.respondNotFound("Project not found")
            return null
        }

        return Unit
    }

    suspend fun validateOrRespond(
        call: ApplicationCall,
        projectId: Int,
        sectionId: Int,
    ): SectionDto? {
        if (validateProjectOrRespond(call, projectId) == null) {
            return null
        }

        val section = sectionRepository.sectionById(projectId, sectionId) ?: run {
            call.respondNotFound("Section not found")
            return null
        }

        return section
    }
}