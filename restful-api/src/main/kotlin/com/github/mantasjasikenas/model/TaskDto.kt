package com.github.mantasjasikenas.model

import io.github.tabilzad.ktor.annotations.KtorFieldDescription
import io.ktor.server.plugins.requestvalidation.*
import kotlinx.datetime.LocalDateTime
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

enum class Priority {
    Low, Medium, High, Vital
}

@Serializable
data class PostTaskDto(
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
)

fun PostTaskDto.validate(): ValidationResult {
    val reasons = mutableListOf<String>()

    if (this.name.isBlank()) {
        reasons.add("Task name cannot be blank")
    }

    if (this.description.isBlank()) {
        reasons.add("Task description cannot be blank")
    }

    if (this.createdBy.isBlank()) {
        reasons.add("Task creator cannot be blank")
    }

    if (this.dueDate != null && this.dueDate.isBlank()) {
        reasons.add("Task due date cannot be blank")
    }

    if (this.dueDate != null && LocalDateTime.Formats.ISO.parseOrNull(this.dueDate) == null) {
        reasons.add("Task due date is not in correct format. Use ISO format. For example: 2021-12-31T23:59:59")
    }

    return if (reasons.isEmpty()) {
        ValidationResult.Valid
    } else {
        ValidationResult.Invalid(reasons)
    }
}


@Serializable
data class UpdateTaskDto(
    @KtorFieldDescription("Task name")
    val name: String?,
    @KtorFieldDescription("Task description")
    val description: String?,
    @KtorFieldDescription("Task priority")
    val priority: Priority?,
    @KtorFieldDescription("Task completion status")
    val isCompleted: Boolean?,
    @KtorFieldDescription("Task due date")
    val dueDate: String?,
    @KtorFieldDescription("Task creator")
    val sectionId: Int?,
)