package com.github.mantasjasikenas.data

import com.github.mantasjasikenas.model.section.PostSectionDto
import com.github.mantasjasikenas.model.section.SectionDto
import com.github.mantasjasikenas.model.section.UpdateSectionDto

interface SectionRepository {
    suspend fun allSections(): List<SectionDto>
    suspend fun sectionById(id: Int): SectionDto?
    suspend fun addSection(projectId: Int, sectionDto: PostSectionDto): SectionDto
    suspend fun removeSection(id: Int): Boolean
    suspend fun updateSection(id: Int, sectionDto: UpdateSectionDto): SectionDto?
}