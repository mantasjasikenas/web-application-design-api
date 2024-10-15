package com.github.mantasjasikenas.model.auth

import kotlinx.serialization.Serializable

@Serializable
data class SuccessfulLoginDto(val accessToken: String)

@Serializable
data class SuccessfulRegisterDto(val id: String, val userName: String, val email: String)

@Serializable
data class LoginDto(val username: String, val password: String)

@Serializable
data class RefreshAccessTokenDto(val refreshToken: String)
