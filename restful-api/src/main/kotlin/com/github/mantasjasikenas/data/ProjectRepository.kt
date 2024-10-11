package com.github.mantasjasikenas.data

import com.github.mantasjasikenas.model.project.PostProjectDto
import com.github.mantasjasikenas.model.project.ProjectDto
import com.github.mantasjasikenas.model.project.UpdateProjectDto

interface ProjectRepository {
    suspend fun allProjects(): List<ProjectDto>
    suspend fun projectById(id: Int): ProjectDto?
    suspend fun addProject(projectDto: PostProjectDto): ProjectDto
    suspend fun removeProject(id: Int): Boolean
    suspend fun updateProject(id: Int, projectDto: UpdateProjectDto): ProjectDto?
    suspend fun projectExists(id: Int): Boolean
}

