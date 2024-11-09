package com.github.mantasjasikenas.data.section

import com.github.mantasjasikenas.data.task.TaskDto
import kotlinx.serialization.Serializable


@Serializable
data class SectionDto(
    val id: Int,
    val projectId: Int,
    val name: String,
    val createdBy: String,
    val createdAt: String,
    val tasks : List<TaskDto>? = null
)