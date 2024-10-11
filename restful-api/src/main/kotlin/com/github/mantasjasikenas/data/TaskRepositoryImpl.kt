package com.github.mantasjasikenas.data

import com.github.mantasjasikenas.db.*
import com.github.mantasjasikenas.model.task.PostTaskDto
import com.github.mantasjasikenas.model.task.TaskDto
import com.github.mantasjasikenas.model.task.UpdateTaskDto
import kotlinx.datetime.LocalDateTime
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.selectAll

class TaskRepositoryImpl : TaskRepository {
    override suspend fun allTasks(projectId: Int, sectionId: Int): List<TaskDto> = suspendTransaction {
        (TasksTable innerJoin SectionsTable)
            .selectAll().where { (TasksTable.sectionId eq sectionId) and (SectionsTable.projectId eq projectId) }
            .map { TaskDAO.wrapRow(it) }
            .map(::daoToModel)
    }

    override suspend fun taskById(projectId: Int, sectionId: Int, id: Int): TaskDto? = suspendTransaction {
        (TasksTable innerJoin SectionsTable)
            .selectAll()
            .where {
                (TasksTable.sectionId eq sectionId) and (SectionsTable.projectId eq projectId) and (TasksTable.id eq id)
            }.map { TaskDAO.wrapRow(it) }
            .firstOrNull()
            ?.let(::daoToModel)
    }

    override suspend fun addTask(projectId: Int, sectionId: Int, taskDto: PostTaskDto): TaskDto? = suspendTransaction {
        if (SectionsTable.selectAll()
                .where { (SectionsTable.id eq sectionId) and (SectionsTable.projectId eq projectId) }
                .count().toInt() == 0
        ) {
            return@suspendTransaction null
        }

        TaskDAO.new {
            name = taskDto.name
            description = taskDto.description
            priority = taskDto.priority
            this.sectionId = EntityID(sectionId, TasksTable)
            isCompleted = taskDto.completed
            dueDateTime = taskDto.dueDate?.let { LocalDateTime.parse(it) }
            createdBy = taskDto.createdBy
        }.let(::daoToModel)
    }

    override suspend fun removeTask(projectId: Int, sectionId: Int, id: Int): Boolean = suspendTransaction {
        val count = (TasksTable innerJoin SectionsTable)
            .selectAll()
            .where {
                (TasksTable.id eq id) and (TasksTable.sectionId eq sectionId) and (SectionsTable.projectId eq projectId)
            }.count()

        if (count == 0L) {
            return@suspendTransaction false
        }

        val rowsDeleted = TasksTable.deleteWhere { TasksTable.id eq id }

        rowsDeleted == 1
    }

    override suspend fun updateTask(projectId: Int, sectionId: Int, id: Int, taskDto: UpdateTaskDto): TaskDto? =
        suspendTransaction {
            if (SectionsTable.selectAll()
                    .where { (SectionsTable.id eq sectionId) and (SectionsTable.projectId eq projectId) }
                    .count().toInt() == 0
            ) {
                return@suspendTransaction null
            }

            TaskDAO.findByIdAndUpdate(id) {
                it.name = taskDto.name ?: it.name
                it.description = taskDto.description ?: it.description
                it.priority = taskDto.priority ?: it.priority
                it.sectionId =
                    if (taskDto.sectionId != null) EntityID(taskDto.sectionId, ProjectsTable) else it.sectionId
                it.isCompleted = taskDto.completed ?: it.isCompleted
                it.dueDateTime = taskDto.dueDate?.let { due -> LocalDateTime.parse(due) } ?: it.dueDateTime
            }?.let(::daoToModel)
        }
}