package com.github.mantasjasikenas.model.section

import kotlinx.serialization.Serializable


@Serializable
data class SectionDto(
    val id: Int,
    val projectId: Int,
    val name: String,
    val createdBy: String,
    val createdAt: String,
)