package com.github.mantasjasikenas.docs.task

import com.github.mantasjasikenas.model.task.PostTaskDto
import com.github.mantasjasikenas.model.task.Priority
import com.github.mantasjasikenas.model.task.TaskDto
import com.github.mantasjasikenas.model.task.UpdateTaskDto

val taskDtoExample = TaskDto(
    id = 1,
    name = "Finish REST API",
    description = "Create documentation and tests",
    createdAt = "2024-10-01T12:12:32",
    sectionId = 1,
    priority = Priority.Low,
    completed = false,
    dueDate = "2023-10-09T12:00:00",
    createdBy = "Sigmund"
)

val taskDtoExample2 = TaskDto(
    id = 2,
    name = "Upload to GitHub",
    description = "Push code to GitHub",
    createdAt = "2024-10-01T12:12:32",
    sectionId = 1,
    priority = Priority.Vital,
    completed = true,
    dueDate = "2023-10-09T12:00:00",
    createdBy = "Viktor"
)

val postTaskDtoExample = PostTaskDto(
    name = "Finish REST API",
    description = "Create documentation and tests",
    priority = Priority.Low,
    completed = false,
    dueDate = "2023-10-09T12:00:00",
    createdBy = "Sigmund"
)

val updateTaskDtoExample = UpdateTaskDto(
    priority = Priority.Medium,
    completed = true,
)