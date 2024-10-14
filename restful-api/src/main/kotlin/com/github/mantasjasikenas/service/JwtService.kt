package com.github.mantasjasikenas.service

import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.interfaces.DecodedJWT
import com.github.mantasjasikenas.model.Role
import io.ktor.server.auth.jwt.*

interface JwtService {
    val realm: String
    val jwtVerifier: JWTVerifier

    fun createAccessToken(username: String, userId: String, roles: List<Role>): String
    fun createRefreshToken(userId: String): String
    fun verifyRefreshToken(token: String): DecodedJWT?
    suspend fun customValidator(credential: JWTCredential): JWTPrincipal?
}