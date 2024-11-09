package com.github.mantasjasikenas.repository.impl

import com.github.mantasjasikenas.data.project.PostProjectDto
import com.github.mantasjasikenas.data.project.ProjectDto
import com.github.mantasjasikenas.data.project.UpdateProjectDto
import com.github.mantasjasikenas.db.suspendTransaction
import com.github.mantasjasikenas.db.tables.ProjectDAO
import com.github.mantasjasikenas.db.tables.ProjectsTable
import com.github.mantasjasikenas.db.tables.UsersTable
import com.github.mantasjasikenas.db.tables.daoToModel
import com.github.mantasjasikenas.repository.ProjectRepository
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import java.util.*

class ProjectRepositoryImpl : ProjectRepository {
    override suspend fun allProjects(): List<ProjectDto> = suspendTransaction {
        ProjectDAO
            .all()
            .map(::daoToModel)
    }

    override suspend fun allUserProjects(userId: String): List<ProjectDto> = suspendTransaction {
        ProjectDAO
            .find { ProjectsTable.createdBy eq UUID.fromString(userId) }
            .map(::daoToModel)
    }

    override suspend fun projectById(id: Int): ProjectDto? = suspendTransaction {
        ProjectDAO
            .findById(id)
            ?.let(::daoToModel)
    }

    override suspend fun addProject(createdBy: String, projectDto: PostProjectDto): ProjectDto = suspendTransaction {
        ProjectDAO.new {
            name = projectDto.name
            description = projectDto.description
            this.createdBy = EntityID(UUID.fromString(createdBy), UsersTable)
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

    override suspend fun projectExists(id: Int): Boolean = suspendTransaction {
        ProjectDAO.findById(id) != null
    }
}