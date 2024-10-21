package com.github.mantasjasikenas.service

import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.interfaces.DecodedJWT
import com.github.mantasjasikenas.data.Role
import io.ktor.server.auth.jwt.*
import kotlinx.datetime.LocalDateTime
import java.util.*

interface JwtService {
    val realm: String
    val jwtVerifier: JWTVerifier

    fun createAccessToken(username: String, userId: UUID, roles: List<Role>): String
    fun createRefreshToken(sessionId: UUID, userId: UUID, expiresAt: LocalDateTime): String
    fun verifyRefreshToken(token: String): DecodedJWT?
    suspend fun customValidator(credential: JWTCredential, refreshToken: String): JWTPrincipal?
}