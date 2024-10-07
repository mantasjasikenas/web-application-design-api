package com.github.mantasjasikenas.model.project

import io.ktor.server.plugins.requestvalidation.*
import kotlinx.serialization.Serializable

@Serializable
data class UpdateProjectDto(
    val name: String?,
    val description: String?,
)

fun UpdateProjectDto.validate(): ValidationResult {
    val reasons = mutableListOf<String>()

    if (this.name == null && this.description == null) {
        reasons.add("At least one field must be provided")
    }

    return if (reasons.isEmpty()) {
        ValidationResult.Valid
    } else {
        ValidationResult.Invalid(reasons)
    }
}