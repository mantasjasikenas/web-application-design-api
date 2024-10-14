package com.github.mantasjasikenas.model.user

import com.github.mantasjasikenas.model.Role
import com.github.mantasjasikenas.model.auth.SuccessfulRegisterDto
import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    val id: String,
    val username: String,
    val email: String,
    val roles: List<Role>,
    val forceRelogin: Boolean
)

fun UserDto.toSuccessfulRegisterDto(): SuccessfulRegisterDto =
    SuccessfulRegisterDto(
        id = this.id,
        userName = this.username,
        email = this.email,
    )