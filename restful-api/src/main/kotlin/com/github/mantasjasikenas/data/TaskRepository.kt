package com.github.mantasjasikenas.data

import com.github.mantasjasikenas.model.task.PostTaskDto
import com.github.mantasjasikenas.model.task.TaskDto
import com.github.mantasjasikenas.model.task.UpdateTaskDto

interface TaskRepository {
    suspend fun allTasks(): List<TaskDto>
    suspend fun allTasks(projectId: Int, sectionId: Int): List<TaskDto>
    suspend fun taskById(id: Int): TaskDto?
    suspend fun addTask(sectionId: Int, taskDto: PostTaskDto): TaskDto
    suspend fun removeTask(id: Int): Boolean
    suspend fun updateTask(id: Int, taskDto: UpdateTaskDto): TaskDto?
}

