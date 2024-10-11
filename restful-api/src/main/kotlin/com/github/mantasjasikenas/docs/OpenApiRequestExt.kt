package com.github.mantasjasikenas.docs

import io.github.smiley4.ktorswaggerui.dsl.routes.OpenApiRequest

fun OpenApiRequest.projectIdPathParameter() {
    pathParameter<Int>("projectId") {
        description = "Project id"
        required = true
        example("default") {
            value = 1
        }
    }
}

fun OpenApiRequest.sectionIdPathParameter() {
    pathParameter<Int>("sectionId") {
        description = "Section id"
        required = true
        example("default") {
            value = 1
        }
    }
}

fun OpenApiRequest.taskIdPathParameter() {
    pathParameter<Int>("taskId") {
        description = "Task id"
        required = true
        example("default") {
            value = 1
        }
    }
}