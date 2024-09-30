package com.github.mantasjasikenas.model.section

import io.github.tabilzad.ktor.annotations.KtorFieldDescription
import kotlinx.serialization.Serializable


@Serializable
data class SectionDto(
    @KtorFieldDescription("Section id")
    val id: Int,
    @KtorFieldDescription("Project id")
    val projectId: Int,
    @KtorFieldDescription("Section name")
    val name: String,
    @KtorFieldDescription("Section creator")
    val createdBy: String,
    @KtorFieldDescription("Section creation date")
    val createdAt: String,
)