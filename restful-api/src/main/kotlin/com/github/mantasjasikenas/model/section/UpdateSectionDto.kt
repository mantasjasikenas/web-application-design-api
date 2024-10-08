package com.github.mantasjasikenas.model.section

import io.ktor.server.plugins.requestvalidation.*
import kotlinx.serialization.Serializable

@Serializable
data class UpdateSectionDto(
    val name: String? = null,
    val projectId: Int? = null
)

fun UpdateSectionDto.validate(): ValidationResult {
    val reasons = mutableListOf<String>()

    if (this.name == null && this.projectId == null) {
        reasons.add("At least one field must be provided")
    }

    return if (reasons.isEmpty()) {
        ValidationResult.Valid
    } else {
        ValidationResult.Invalid(reasons)
    }
}