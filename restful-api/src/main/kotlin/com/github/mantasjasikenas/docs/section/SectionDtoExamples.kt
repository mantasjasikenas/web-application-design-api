package com.github.mantasjasikenas.docs.section

import com.github.mantasjasikenas.model.section.PostSectionDto
import com.github.mantasjasikenas.model.section.SectionDto
import com.github.mantasjasikenas.model.section.UpdateSectionDto

val sectionDtoExample = SectionDto(
    id = 1, name = "Backlog", projectId = 1, createdBy = "Virginia", createdAt = "2024-09-01T12:00:00"
)

val sectionDtoExample2 = SectionDto(
    id = 2, name = "In Progress", projectId = 1, createdBy = "Natalie", createdAt = "2024-09-02T12:00:00"
)

val postSectionDtoExample = PostSectionDto(
    name = "Done", createdBy = "Virginia"
)

val updateSectionDtoExample = UpdateSectionDto(
    name = "Done", projectId = 1
)