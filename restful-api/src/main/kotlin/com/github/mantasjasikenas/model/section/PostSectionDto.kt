package com.github.mantasjasikenas.model.section

import io.github.tabilzad.ktor.annotations.KtorFieldDescription
import io.ktor.server.plugins.requestvalidation.*
import kotlinx.serialization.Serializable

@Serializable
data class PostSectionDto(
    @KtorFieldDescription("Section name")
    val name: String,
    @KtorFieldDescription("Section creator")
    val createdBy: String,
)

fun PostSectionDto.validate(): ValidationResult {
    val reasons = mutableListOf<String>()

    if (this.name.isBlank()) {
        reasons.add("Section name cannot be blank")
    }

    if (this.createdBy.isBlank()) {
        reasons.add("Section creator cannot be blank")
    }

    return if (reasons.isEmpty()) {
        ValidationResult.Valid
    } else {
        ValidationResult.Invalid(reasons)
    }
}