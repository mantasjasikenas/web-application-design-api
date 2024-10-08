package com.github.mantasjasikenas.data

import com.github.mantasjasikenas.db.*
import com.github.mantasjasikenas.model.section.PostSectionDto
import com.github.mantasjasikenas.model.section.SectionDto
import com.github.mantasjasikenas.model.section.UpdateSectionDto
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere

class SectionRepositoryImpl : SectionRepository {
    override suspend fun allSections(): List<SectionDto> = suspendTransaction {
        SectionDAO
            .all()
            .map(::daoToModel)
    }

    override suspend fun sectionById(id: Int): SectionDto? = suspendTransaction {
        SectionDAO
            .findById(id)
            ?.let(::daoToModel)
    }

    override suspend fun addSection(projectId: Int, sectionDto: PostSectionDto): SectionDto = suspendTransaction {
        SectionDAO.new {
            name = sectionDto.name
            createdBy = sectionDto.createdBy
            this.projectId = EntityID(projectId, ProjectsTable)
        }.let(::daoToModel)
    }

    override suspend fun removeSection(id: Int): Boolean = suspendTransaction {
        val rowsDeleted = SectionsTable.deleteWhere {
            SectionsTable.id eq id
        }
        rowsDeleted == 1
    }

    override suspend fun updateSection(id: Int, sectionDto: UpdateSectionDto): SectionDto? = suspendTransaction {
        SectionDAO.findByIdAndUpdate(id) {
            it.name = sectionDto.name ?: it.name
            it.projectId = if (sectionDto.projectId != null) EntityID(sectionDto.projectId, ProjectsTable) else it.projectId
        }?.let(::daoToModel)
    }
}