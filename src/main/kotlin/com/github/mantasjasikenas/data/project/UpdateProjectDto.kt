﻿package com.github.mantasjasikenas.data.project

import io.ktor.server.plugins.requestvalidation.*
import kotlinx.serialization.Serializable

@Serializable
data class UpdateProjectDto(
    val name: String? = null,
    val description: String? = null
)

fun UpdateProjectDto.validate(): ValidationResult {
    val reasons = mutableListOf<String>()

    if (this.name == null && this.description == null) {
        reasons.add("At least one field must be provided")
    }

    if (this.name != null && this.name.isBlank()) {
        reasons.add("Project name cannot be empty")
    }

    if (this.description != null && this.description.isBlank()) {
        reasons.add("Project description cannot be empty")
    }

    return if (reasons.isEmpty()) {
        ValidationResult.Valid
    } else {
        ValidationResult.Invalid(reasons)
    }
}