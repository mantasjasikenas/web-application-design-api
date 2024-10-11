package com.github.mantasjasikenas.routes

import com.github.mantasjasikenas.data.ProjectRepository
import com.github.mantasjasikenas.data.SectionRepository
import com.github.mantasjasikenas.model.respondBadRequest
import com.github.mantasjasikenas.model.respondNotFound
import io.ktor.server.application.*

internal suspend fun ApplicationCall.validateAndGetProjectId(projectRepository: ProjectRepository): Int? {
    val projectId = parameters["projectId"]?.toInt()

    if (projectId == null) {
        respondBadRequest("Project id is required")

        return null
    }

    if (!projectRepository.projectExists(projectId)) {
        respondNotFound("Project not found")

        return null
    }

    return projectId
}

internal suspend fun ApplicationCall.validateAndGetSectionId(sectionRepository: SectionRepository): Int? {
    val sectionId = parameters["sectionId"]?.toInt()

    if (sectionId == null) {
        respondBadRequest("Section id is required")

        return null
    }

    if (!sectionRepository.sectionExists(sectionId)) {
        respondNotFound("Section not found")

        return null
    }

    return sectionId
}

internal suspend fun ApplicationCall.getTaskId(): Int? {
    val taskId = parameters["taskId"]?.toInt()

    if (taskId == null) {
        respondBadRequest("Task id is required")

        return null
    }

    return taskId
}