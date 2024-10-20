package com.github.mantasjasikenas.repository

import com.github.mantasjasikenas.model.section.PostSectionDto
import com.github.mantasjasikenas.model.section.SectionDto
import com.github.mantasjasikenas.model.section.UpdateSectionDto

interface SectionRepository {
    suspend fun allSections(projectId: Int): List<SectionDto>
    suspend fun sectionById(projectId: Int, id: Int): SectionDto?
    suspend fun addSection(projectId: Int, sectionDto: PostSectionDto): SectionDto?
    suspend fun removeSection(projectId: Int, id: Int): Boolean
    suspend fun updateSection(projectId: Int, id: Int, sectionDto: UpdateSectionDto): SectionDto?
    suspend fun sectionExists(id: Int): Boolean
}