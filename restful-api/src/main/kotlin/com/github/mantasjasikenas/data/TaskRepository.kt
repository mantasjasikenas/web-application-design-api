package com.github.mantasjasikenas.data

import com.github.mantasjasikenas.model.PostTaskDto
import com.github.mantasjasikenas.model.TaskDto
import com.github.mantasjasikenas.model.UpdateTaskDto

interface TaskRepository {
    suspend fun allTasks(): List<TaskDto>
    suspend fun taskById(id: Int): TaskDto?
    suspend fun addTask(sectionId: Int, taskDto: PostTaskDto): TaskDto
    suspend fun removeTask(id: Int): Boolean
    suspend fun updateTask(id: Int, taskDto: UpdateTaskDto): TaskDto?
}

