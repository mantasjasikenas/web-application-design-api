package com.github.mantasjasikenas.repository

import com.github.mantasjasikenas.data.task.PostTaskDto
import com.github.mantasjasikenas.data.task.TaskDto
import com.github.mantasjasikenas.data.task.UpdateTaskDto

interface TaskRepository {
    suspend fun allTasks(projectId: Int, sectionId: Int): List<TaskDto>
    suspend fun allUserTasks(userId: String, projectId: Int, sectionId: Int): List<TaskDto>
    suspend fun taskById(projectId: Int, sectionId: Int, id: Int): TaskDto?
    suspend fun addTask(createdBy: String, projectId: Int, sectionId: Int, taskDto: PostTaskDto): TaskDto?
    suspend fun removeTask(projectId: Int, sectionId: Int, id: Int): Boolean
    suspend fun updateTask(projectId: Int, sectionId: Int, id: Int, taskDto: UpdateTaskDto): TaskDto?
}