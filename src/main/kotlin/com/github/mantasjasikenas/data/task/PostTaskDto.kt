package com.github.mantasjasikenas.data.task

import io.ktor.server.plugins.requestvalidation.*
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class PostTaskDto(
    val name: String,
    val description: String,
    val priority: Priority,
    val completed: Boolean,
    val dueDate: String?,
)

fun PostTaskDto.validate(): ValidationResult {
    val reasons = mutableListOf<String>()

    if (this.name.isBlank()) {
        reasons.add("Task name cannot be blank")
    }

    if (this.description.isBlank()) {
        reasons.add("Task description cannot be blank")
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