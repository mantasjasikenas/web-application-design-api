﻿package com.github.mantasjasikenas.data.project

import io.ktor.server.plugins.requestvalidation.*
import kotlinx.serialization.Serializable

@Serializable
data class PostProjectDto(
    val name: String,
    val description: String,
)

fun PostProjectDto.validate(): ValidationResult {
    val reasons = mutableListOf<String>()

    if (this.name.isBlank()) {
        reasons.add("Task name cannot be blank")
    }

    if (this.description.isBlank()) {
        reasons.add("Task description cannot be blank")
    }

    return if (reasons.isEmpty()) {
        ValidationResult.Valid
    } else {
        ValidationResult.Invalid(reasons)
    }
}