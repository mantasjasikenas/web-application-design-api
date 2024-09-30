package com.github.mantasjasikenas.model.task

import io.github.tabilzad.ktor.annotations.KtorFieldDescription
import kotlinx.serialization.Serializable


@Serializable
data class TaskDto(
    @KtorFieldDescription("Task id")
    val id: Int,
    @KtorFieldDescription("Section id")
    val sectionId: Int,
    @KtorFieldDescription("Task name")
    val name: String,
    @KtorFieldDescription("Task description")
    val description: String,
    @KtorFieldDescription("Task priority")
    val priority: Priority,
    @KtorFieldDescription("Task completion status")
    val isCompleted: Boolean,
    @KtorFieldDescription("Task due date")
    val dueDate: String?,
    @KtorFieldDescription("Task creator")
    val createdBy: String,
    @KtorFieldDescription("Task creation date")
    val createdAt: String,
)