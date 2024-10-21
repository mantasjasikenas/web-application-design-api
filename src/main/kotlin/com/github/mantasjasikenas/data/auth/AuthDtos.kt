package com.github.mantasjasikenas.data.auth

import com.github.mantasjasikenas.data.user.UserDto
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class SuccessfulLoginDto(val accessToken: String)

@Serializable
data class SuccessfulRegisterDto(val id: String, val userName: String, val email: String)

@Serializable
data class LoginDto(val username: String, val password: String)

@Serializable
data class RefreshAccessTokenDto(val refreshToken: String)

@Serializable
data class SessionDto(
    val id: String,
    val lastRefreshToken: String,
    val initiatedAt: LocalDateTime,
    val expiresAt: LocalDateTime,
    val isRevoked: Boolean,
    val userId: String,
    val user: UserDto
)
