package com.github.mantasjasikenas.model

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable


@Serializable
data class TaskDto(
    val id: Int,
    val sectionId: Int,
    val name: String,
    val description: String,
    val priority: Priority,
    val isCompleted: Boolean,
    val dueDate: LocalDateTime?,
    val createdBy: String,
    val createdAt: LocalDateTime,
)

enum class Priority {
    Low, Medium, High, Vital
}

@Serializable
data class PostTaskDto(
    val name: String,
    val description: String,
    val priority: Priority,
    val isCompleted: Boolean,
    val dueDate: LocalDateTime?,
    val createdBy: String,
)

@Serializable
data class UpdateTaskDto(
    val name: String?,
    val description: String?,
    val priority: Priority?,
    val isCompleted: Boolean?,
    val dueDate: LocalDateTime?,
    val sectionId: Int?,
)