package com.github.mantasjasikenas.data

import kotlinx.serialization.Serializable

@Serializable
data class AuthResponse(
  val accessToken: String,
  val refreshToken: String,
)
