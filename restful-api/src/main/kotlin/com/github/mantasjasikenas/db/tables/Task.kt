package com.github.mantasjasikenas.db.tables

import com.github.mantasjasikenas.model.task.Priority
import com.github.mantasjasikenas.model.task.TaskDto
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.kotlin.datetime.CurrentDateTime
import org.jetbrains.exposed.sql.kotlin.datetime.datetime


object TasksTable : IntIdTable() {
    val name = text("name")
    val description = text("description")
    val priority = enumerationByName("priority", 10, Priority::class)
    val isCompleted = bool("is_completed").default(false)
    val dueDateTime = datetime("due_date").nullable()
    val createdAt = datetime("created_at").defaultExpression(CurrentDateTime)
    val createdBy = reference("created_by", UsersTable, onDelete = ReferenceOption.CASCADE)
    val sectionId = reference("section_id", SectionsTable, onDelete = ReferenceOption.CASCADE)
}


class TaskDAO(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<TaskDAO>(TasksTable)

    var name by TasksTable.name
    var description by TasksTable.description
    var priority by TasksTable.priority
    var isCompleted by TasksTable.isCompleted
    var dueDateTime by TasksTable.dueDateTime
    var createdBy by TasksTable.createdBy
    var sectionId by TasksTable.sectionId
    var createdAt by TasksTable.createdAt
    var section by SectionDAO referencedOn TasksTable.sectionId
}


fun daoToModel(dao: TaskDAO) = TaskDto(
    id = dao.id.value,
    sectionId = dao.section.id.value,
    name = dao.name,
    description = dao.description,
    priority = dao.priority,
    completed = dao.isCompleted,
    dueDate = dao.dueDateTime.toString(),
    createdBy = dao.createdBy.value.toString(),
    createdAt = dao.createdAt.toString(),
)