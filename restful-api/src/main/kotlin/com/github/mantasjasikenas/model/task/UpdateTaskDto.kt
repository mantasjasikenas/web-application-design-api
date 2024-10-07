package com.github.mantasjasikenas.model.task

import io.ktor.server.plugins.requestvalidation.*
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class UpdateTaskDto(
    val name: String?,
    val description: String?,
    val priority: Priority?,
    val isCompleted: Boolean?,
    val dueDate: String?,
    val sectionId: Int?,
)

fun UpdateTaskDto.validate(): ValidationResult {
    val reasons = mutableListOf<String>()

    if (this.name == null && this.description == null && this.priority == null && this.isCompleted == null && this.dueDate == null && this.sectionId == null) {
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