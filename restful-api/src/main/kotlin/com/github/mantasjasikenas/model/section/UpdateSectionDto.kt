package com.github.mantasjasikenas.model.section

import io.github.tabilzad.ktor.annotations.KtorFieldDescription
import io.ktor.server.plugins.requestvalidation.*
import kotlinx.serialization.Serializable

@Serializable
data class UpdateSectionDto(
    @KtorFieldDescription("Section name")
    val name: String?,
)

fun UpdateSectionDto.validate(): ValidationResult {
    val reasons = mutableListOf<String>()

    if (this.name == null) {
        reasons.add("At least one field must be provided")
    }

    return if (reasons.isEmpty()) {
        ValidationResult.Valid
    } else {
        ValidationResult.Invalid(reasons)
    }
}