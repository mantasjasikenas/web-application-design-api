package com.github.mantasjasikenas.model

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable


@Serializable
data class ProjectDto(
    val id: Int,
    val name: String,
    val description: String,
    val createdAt: LocalDateTime,
    val createdBy: String,
)

@Serializable
data class PostProjectDto(
    val name: String,
    val description: String,
    val createdBy: String,
)

@Serializable
data class UpdateProjectDto(
    val name: String?,
    val description: String?,
)