package com.github.mantasjasikenas.data.user

import com.github.mantasjasikenas.data.Role
import java.util.*

data class User(
    val id: UUID,
    val email: String,
    val username: String,
    val password: String,
    val roles: List<Role>,
)

fun modelToDto(user: User) = UserDto(
    id = user.id.toString(),
    username = user.username,
    email = user.email,
    roles = user.roles
)