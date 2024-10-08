package com.github.mantasjasikenas.model.task

import kotlinx.serialization.Serializable


@Serializable
data class TaskDto(
    val id: Int,
    val sectionId: Int,
    val name: String,
    val description: String,
    val priority: Priority,
    val completed: Boolean,
    val dueDate: String?,
    val createdBy: String,
    val createdAt: String,
)