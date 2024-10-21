package com.github.mantasjasikenas.db.tables

import com.github.mantasjasikenas.data.project.ProjectDto
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.kotlin.datetime.CurrentDateTime
import org.jetbrains.exposed.sql.kotlin.datetime.datetime

object ProjectsTable : IntIdTable() {
    val name = varchar("name", 255)
    val description = text("description")
    val createdAt = datetime("created_at").defaultExpression(CurrentDateTime)
    val createdBy = reference("created_by", UsersTable, onDelete = ReferenceOption.CASCADE)
}


class ProjectDAO(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<ProjectDAO>(ProjectsTable)

    var name by ProjectsTable.name
    var description by ProjectsTable.description
    var createdAt by ProjectsTable.createdAt
    var createdBy by ProjectsTable.createdBy
}


fun daoToModel(dao: ProjectDAO) = ProjectDto(
    id = dao.id.value,
    name = dao.name,
    description = dao.description,
    createdAt = dao.createdAt.toString(),
    createdBy = dao.createdBy.value.toString()
)