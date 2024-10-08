package com.github.mantasjasikenas.model.task

import io.ktor.server.plugins.requestvalidation.*
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class UpdateTaskDto(
    val name: String? = null,
    val description: String? = null,
    val priority: Priority? = null,
    val completed: Boolean? = null,
    val dueDate: String? = null,
    val sectionId: Int? = null
)

fun UpdateTaskDto.validate(): ValidationResult {
    val reasons = mutableListOf<String>()

    if (this.name == null && this.description == null && this.priority == null && this.completed == null && this.dueDate == null && this.sectionId == null) {
        reasons.add("At least one field must be provided")
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