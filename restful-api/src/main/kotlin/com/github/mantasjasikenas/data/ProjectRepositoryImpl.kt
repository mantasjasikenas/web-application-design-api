package com.github.mantasjasikenas.data

import com.github.mantasjasikenas.db.ProjectDAO
import com.github.mantasjasikenas.db.ProjectsTable
import com.github.mantasjasikenas.db.daoToModel
import com.github.mantasjasikenas.db.suspendTransaction
import com.github.mantasjasikenas.model.PostProjectDto
import com.github.mantasjasikenas.model.ProjectDto
import com.github.mantasjasikenas.model.UpdateProjectDto
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere

class ProjectRepositoryImpl : ProjectRepository {
    override suspend fun allProjects(): List<ProjectDto> = suspendTransaction {
        ProjectDAO
            .all()
            .map(::daoToModel)
    }

    override suspend fun projectById(id: Int): ProjectDto? = suspendTransaction {
        ProjectDAO
            .findById(id)
            ?.let(::daoToModel)
    }

    override suspend fun addProject(projectDto: PostProjectDto): ProjectDto = suspendTransaction {
        ProjectDAO.new {
            name = projectDto.name
            description = projectDto.description
            createdBy = projectDto.createdBy
        }.let(::daoToModel)
    }

    override suspend fun removeProject(id: Int): Boolean = suspendTransaction {
        val rowsDeleted = ProjectsTable.deleteWhere {
            ProjectsTable.id eq id
        }
        rowsDeleted == 1
    }

    override suspend fun updateProject(id: Int, projectDto: UpdateProjectDto): ProjectDto? = suspendTransaction {
        ProjectDAO.findByIdAndUpdate(id) {
            it.name = projectDto.name ?: it.name
            it.description = projectDto.description ?: it.description
        }?.let(::daoToModel)
    }
}