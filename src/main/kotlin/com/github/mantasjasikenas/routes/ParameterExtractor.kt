package com.github.mantasjasikenas.routes

import com.github.mantasjasikenas.data.respondBadRequest
import io.ktor.server.application.*


internal suspend fun ApplicationCall.getTaskIdOrRespond(): Int? {
    val taskId = parameters["taskId"]?.toInt()

    if (taskId == null) {
        respondBadRequest("Task id is required")
        return null
    }

    return taskId
}

internal suspend fun ApplicationCall.getProjectIdOrRespond(): Int? {
    val projectId = parameters["projectId"]?.toInt()

    if (projectId == null) {
        respondBadRequest("Project id is required")
        return null
    }

    return projectId
}

internal suspend fun ApplicationCall.getSectionIdOrRespond(): Int? {
    val sectionId = parameters["sectionId"]?.toInt()

    if (sectionId == null) {
        respondBadRequest("Section id is required")
        return null
    }

    return sectionId
}

internal suspend fun ApplicationCall.getProjectSectionTaskIdOrRespond(): Triple<Int, Int, Int>? {
    val projectId = this.getProjectIdOrRespond() ?: return null
    val sectionId = this.getSectionIdOrRespond() ?: return null
    val taskId = this.getTaskIdOrRespond() ?: return null

    return Triple(projectId, sectionId, taskId)
}

internal suspend fun ApplicationCall.getProjectSectionIdOrRespond(): Pair<Int, Int>? {
    val projectId = this.getProjectIdOrRespond() ?: return null
    val sectionId = this.getSectionIdOrRespond() ?: return null

    return Pair(projectId, sectionId)
}

