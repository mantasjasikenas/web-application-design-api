package com.github.mantasjasikenas.model.project

import io.github.tabilzad.ktor.annotations.KtorFieldDescription
import kotlinx.serialization.Serializable


@Serializable
data class ProjectDto(
    @KtorFieldDescription("Project id")
    val id: Int,
    @KtorFieldDescription("Project name")
    val name: String,
    @KtorFieldDescription("Project description")
    val description: String,
    @KtorFieldDescription("Project creation date")
    val createdAt: String,
    @KtorFieldDescription("Project creator")
    val createdBy: String,
)

