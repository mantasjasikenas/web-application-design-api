package com.github.mantasjasikenas.model.user

import com.github.mantasjasikenas.model.Role
import java.util.*

data class User(
    val id: UUID,
    val email: String,
    val username: String,
    val password: String,
    val roles: List<Role>,
    var forceRelogin: Boolean
)

fun modelToDto(user: User) = UserDto(
    id = user.id.toString(),
    username = user.username,
    email = user.email,
    roles = user.roles,
    forceRelogin = user.forceRelogin
)