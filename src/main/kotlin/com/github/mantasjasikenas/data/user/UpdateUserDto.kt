package com.github.mantasjasikenas.data.user

import kotlinx.serialization.Serializable

@Serializable
data class UpdateUserDto(
    val username: String? = null,
    val email: String? = null,
    val password: String? = null,
)