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
    override suspend fun allTasks(): List<TaskDto> = suspendTransaction {
        TaskDAO.all().map(::daoToModel)
    }

    override suspend fun allTasks(projectId: Int, sectionId: Int): List<TaskDto> = suspendTransaction {
        (TasksTable innerJoin SectionsTable)
            .selectAll().where { (TasksTable.sectionId eq sectionId) and (SectionsTable.projectId eq projectId) }
            .map { TaskDAO.wrapRow(it) }
            .map(::daoToModel)
    }

    override suspend fun taskById(id: Int): TaskDto? = suspendTransaction {
        TaskDAO.findById(id)?.let(::daoToModel)
    }


    override suspend fun addTask(sectionId: Int, taskDto: PostTaskDto): TaskDto = suspendTransaction {
        TaskDAO.new {
            name = taskDto.name
            description = taskDto.description
            priority = taskDto.priority
            this.sectionId = EntityID(sectionId, TasksTable)
            isCompleted = taskDto.isCompleted
            dueDateTime = taskDto.dueDate?.let { LocalDateTime.parse(it) }
            createdBy = taskDto.createdBy
        }.let(::daoToModel)
    }

    override suspend fun removeTask(id: Int): Boolean = suspendTransaction {
        val rowsDeleted = TasksTable.deleteWhere {
            TasksTable.id eq id
        }
        rowsDeleted == 1
    }

    override suspend fun updateTask(id: Int, taskDto: UpdateTaskDto): TaskDto? = suspendTransaction {
        TaskDAO.findByIdAndUpdate(id) {
            it.name = taskDto.name ?: it.name
            it.description = taskDto.description ?: it.description
            it.priority = taskDto.priority ?: it.priority
            it.sectionId = if (taskDto.sectionId != null) EntityID(taskDto.sectionId, ProjectsTable) else it.sectionId
            it.isCompleted = taskDto.isCompleted ?: it.isCompleted
            it.dueDateTime = taskDto.dueDate?.let { due -> LocalDateTime.parse(due) } ?: it.dueDateTime
        }?.let(::daoToModel)
    }
}