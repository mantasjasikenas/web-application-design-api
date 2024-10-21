package com.github.mantasjasikenas.repository.impl

import com.github.mantasjasikenas.db.suspendTransaction
import com.github.mantasjasikenas.db.tables.*
import com.github.mantasjasikenas.data.section.PostSectionDto
import com.github.mantasjasikenas.data.section.SectionDto
import com.github.mantasjasikenas.data.section.UpdateSectionDto
import com.github.mantasjasikenas.repository.SectionRepository
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.selectAll
import java.util.*

class SectionRepositoryImpl : SectionRepository {
    override suspend fun allSections(projectId: Int): List<SectionDto> = suspendTransaction {
        SectionDAO
            .find { SectionsTable.projectId eq projectId }
            .map(::daoToModel)
    }

    override suspend fun sectionById(projectId: Int, id: Int): SectionDto? = suspendTransaction {
        SectionDAO
            .find {
                (SectionsTable.projectId eq projectId) and (SectionsTable.id eq id)
            }
            .firstOrNull()
            ?.let(::daoToModel)
    }

    override suspend fun addSection(createdBy: String, projectId: Int, sectionDto: PostSectionDto): SectionDto? = suspendTransaction {
        if (ProjectsTable.selectAll()
                .where { (ProjectsTable.id eq projectId) }
                .count().toInt() == 0
        ) {
            return@suspendTransaction null
        }

        SectionDAO.new {
            name = sectionDto.name
            this.createdBy = EntityID(UUID.fromString(createdBy), UsersTable)
            this.projectId = EntityID(projectId, ProjectsTable)
        }.let(::daoToModel)
    }

    override suspend fun removeSection(projectId: Int, id: Int): Boolean = suspendTransaction {
        val rowsDeleted = SectionsTable.deleteWhere {
            SectionsTable.id eq id and (SectionsTable.projectId eq projectId)
        }
        rowsDeleted == 1
    }

    override suspend fun updateSection(projectId: Int, id: Int, sectionDto: UpdateSectionDto): SectionDto? =
        suspendTransaction {
            SectionDAO.findByIdAndUpdate(id) {
                it.name = sectionDto.name ?: it.name
                it.projectId =
                    if (sectionDto.projectId != null) EntityID(sectionDto.projectId, ProjectsTable) else it.projectId
            }?.let(::daoToModel)
        }

    override suspend fun sectionExists(id: Int): Boolean = suspendTransaction {
        SectionDAO.findById(id) != null
    }
}