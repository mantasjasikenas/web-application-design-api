package com.github.mantasjasikenas.data.user

import com.github.mantasjasikenas.data.Role
import com.github.mantasjasikenas.data.auth.SuccessfulRegisterDto
import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    val id: String,
    val username: String,
    val email: String,
    val roles: List<Role>,
)

fun UserDto.toSuccessfulRegisterDto(): SuccessfulRegisterDto =
    SuccessfulRegisterDto(
        id = this.id,
        userName = this.username,
        email = this.email,
    )