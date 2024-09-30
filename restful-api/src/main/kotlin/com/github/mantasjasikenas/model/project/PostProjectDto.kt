package com.github.mantasjasikenas.model.project

import io.github.tabilzad.ktor.annotations.KtorFieldDescription
import io.ktor.server.plugins.requestvalidation.*
import kotlinx.serialization.Serializable

@Serializable
data class PostProjectDto(
    @KtorFieldDescription("Project name")
    val name: String,
    @KtorFieldDescription("Project description")
    val description: String,
    @KtorFieldDescription("Project creator")
    val createdBy: String,
)

fun PostProjectDto.validate(): ValidationResult {
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

    return if (reasons.isEmpty()) {
        ValidationResult.Valid
    } else {
        ValidationResult.Invalid(reasons)
    }
}