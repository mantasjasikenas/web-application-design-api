package com.github.mantasjasikenas.db

import com.github.mantasjasikenas.model.SectionDto
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.kotlin.datetime.datetime


object SectionsTable : IntIdTable() {
    val projectId = reference("project_id", ProjectsTable, onDelete = ReferenceOption.CASCADE)
    val name = varchar("name", 255)
    val createdAt =
        datetime("created_at").default(
            Clock.System.now()
                .toLocalDateTime(TimeZone.Companion.currentSystemDefault())
        )
    val createdBy = varchar("created_by", 255)
}

class SectionDAO(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<SectionDAO>(SectionsTable)

    var name by SectionsTable.name
    var projectId by SectionsTable.projectId
    var project by ProjectDAO referencedOn SectionsTable.projectId
    var createdAt by SectionsTable.createdAt
    var createdBy by SectionsTable.createdBy
}


fun daoToModel(dao: SectionDAO) = SectionDto(
    id = dao.id.value,
    name = dao.name,
    createdAt = dao.createdAt,
    createdBy = dao.createdBy,
    projectId = dao.project.id.value,
)