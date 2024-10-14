package com.github.mantasjasikenas.db.tables

import com.github.mantasjasikenas.model.section.SectionDto
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.kotlin.datetime.CurrentDateTime
import org.jetbrains.exposed.sql.kotlin.datetime.datetime


object SectionsTable : IntIdTable() {
    val projectId = reference("project_id", ProjectsTable, onDelete = ReferenceOption.CASCADE)
    val name = varchar("name", 255)
    val createdAt = datetime("created_at").defaultExpression(CurrentDateTime)
    val createdBy = reference("created_by", UsersTable, onDelete = ReferenceOption.CASCADE)
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
    createdAt = dao.createdAt.toString(),
    createdBy = dao.createdBy.value.toString(),
    projectId = dao.project.id.value,
)