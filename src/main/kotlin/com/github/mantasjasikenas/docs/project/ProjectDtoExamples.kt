package com.github.mantasjasikenas.docs.project

import com.github.mantasjasikenas.data.project.PostProjectDto
import com.github.mantasjasikenas.data.project.ProjectDto
import com.github.mantasjasikenas.data.project.UpdateProjectDto

val projectDtoExample = ProjectDto(
    id = 1,
    name = "Personal Task Manager",
    description = "Plan your day with ease",
    createdAt = "2024-10-01T12:00:00",
    createdBy = "Antonia"
)

val projectDtoExample2 = ProjectDto(
    id = 2,
    name = "Home Budget",
    description = "Manage your finances",
    createdAt = "2024-08-09T12:00:00",
    createdBy = "Anton"
)

val postProjectDtoExample = PostProjectDto(
    name = "Movie Night",
    description = "Plan a movie night with friends"
)

val updateProjectDtoExample = UpdateProjectDto(
    name = "Movie Morning",
)