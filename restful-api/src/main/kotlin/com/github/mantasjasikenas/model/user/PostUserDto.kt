package com.github.mantasjasikenas.model.user

import io.ktor.server.plugins.requestvalidation.*
import kotlinx.serialization.Serializable

@Serializable
data class PostUserDto(val username: String, val email: String, val password: String)

fun PostUserDto.validate(): ValidationResult {
    val reasons = mutableListOf<String>()

    if (this.username.isBlank()) {
        reasons.add("Username cannot be blank")
    }

    if (this.email.isBlank() || !this.email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\$".toRegex())) {
        reasons.add("Email is not valid")
    }

    if (this.password.isBlank()) {
        reasons.add("Password cannot be blank")
    }

    return if (reasons.isEmpty()) {
        ValidationResult.Valid
    } else {
        ValidationResult.Invalid(reasons)
    }
}
