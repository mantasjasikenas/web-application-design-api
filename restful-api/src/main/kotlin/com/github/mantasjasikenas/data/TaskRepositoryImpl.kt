package com.github.mantasjasikenas.data

import com.github.mantasjasikenas.db.*
import com.github.mantasjasikenas.model.PostTaskDto
import com.github.mantasjasikenas.model.TaskDto
import com.github.mantasjasikenas.model.UpdateTaskDto
import kotlinx.datetime.LocalDateTime
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere

class TaskRepositoryImpl : TaskRepository {
    override suspend fun allTasks(): List<TaskDto> = suspendTransaction {
        TaskDAO
            .all()
            .map(::daoToModel)
    }

    override suspend fun taskById(id: Int): TaskDto? = suspendTransaction {
        TaskDAO
            .findById(id)
            ?.let(::daoToModel)
    }


    override suspend fun addTask(sectionId: Int, taskDto: PostTaskDto): TaskDto = suspendTransaction {
        TaskDAO.new {
            name = taskDto.name
            description = taskDto.description
            priority = taskDto.priority
            this.sectionId = EntityID(sectionId, TasksTable)
            isCompleted = taskDto.isCompleted
            dueDateTime =taskDto.dueDate?.let { LocalDateTime.parse(it) }
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
            it.dueDateTime = taskDto.dueDate?.let { LocalDateTime.parse(it) } ?: it.dueDateTime
        }?.let(::daoToModel)
    }
}