﻿package com.github.mantasjasikenas.model.project

import kotlinx.serialization.Serializable


@Serializable
data class ProjectDto(
    val id: Int,
    val name: String,
    val description: String,
    val createdAt: String,
    val createdBy: String,
)

