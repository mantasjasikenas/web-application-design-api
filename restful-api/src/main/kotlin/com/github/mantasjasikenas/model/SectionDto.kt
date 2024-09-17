package com.github.mantasjasikenas.model

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable


@Serializable
data class SectionDto(
    val id: Int,
    val projectId: Int,
    val name: String,
    val createdBy: String,
    val createdAt: LocalDateTime,
)

@Serializable
data class PostSectionDto(
    val name: String,
    val createdBy: String,
)

@Serializable
data class UpdateSectionDto(
    val name: String?,
)