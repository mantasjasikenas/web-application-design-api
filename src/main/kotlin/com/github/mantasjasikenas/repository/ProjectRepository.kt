﻿package com.github.mantasjasikenas.repository

import com.github.mantasjasikenas.data.project.PostProjectDto
import com.github.mantasjasikenas.data.project.ProjectDto
import com.github.mantasjasikenas.data.project.UpdateProjectDto

interface ProjectRepository {
    suspend fun allProjects(): List<ProjectDto>
    suspend fun allUserProjects(userId: String): List<ProjectDto>
    suspend fun projectById(id: Int): ProjectDto?
    suspend fun addProject(createdBy: String, projectDto: PostProjectDto): ProjectDto
    suspend fun removeProject(id: Int): Boolean
    suspend fun updateProject(id: Int, projectDto: UpdateProjectDto): ProjectDto?
    suspend fun projectExists(id: Int): Boolean
}

