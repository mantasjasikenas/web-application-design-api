package com.github.mantasjasikenas.service.impl

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTCreator
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.DecodedJWT
import com.github.mantasjasikenas.data.Role
import com.github.mantasjasikenas.service.JwtService
import com.github.mantasjasikenas.service.SessionService
import io.ktor.server.application.*
import io.ktor.server.auth.jwt.*
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import java.util.*
import kotlin.time.Duration.Companion.days
import kotlin.time.Duration.Companion.minutes

val JWT_ACCESS_TOKEN_EXPIRATION = 5.minutes
val JWT_REFRESH_TOKEN_EXPIRATION = 3.days

class JwtServiceImpl(
    private val application: Application,
    private val sessionService: SessionService
) : JwtService {
    private val secret = getConfigProperty("JWT_SECRET")
    private val issuer = getConfigProperty("JWT_ISSUER")
    private val audience = getConfigProperty("JWT_AUDIENCE")

    override val realm = getConfigProperty("JWT_REALM")

    override val jwtVerifier: JWTVerifier =
        JWT
            .require(Algorithm.HMAC256(secret))
            .withAudience(audience)
            .withIssuer(issuer)
            .build()

    override fun createAccessToken(username: String, userId: UUID, roles: List<Role>): String {
        return createJwtCreatorBaseBuilder(userId)
            .withClaim("username", username)
            .withArrayClaim("roles", roles.map { it.name }.toTypedArray())
            .withExpiresAt(Date(System.currentTimeMillis() + JWT_ACCESS_TOKEN_EXPIRATION.inWholeMilliseconds))
            .sign(Algorithm.HMAC256(secret))
    }

    override fun createRefreshToken(sessionId: UUID, userId: UUID, expiresAt: LocalDateTime): String {
        return createJwtCreatorBaseBuilder(userId)
            .withClaim("sessionId", sessionId.toString())
            .withExpiresAt(Date(expiresAt.toInstant(TimeZone.UTC).toEpochMilliseconds()))
            .sign(Algorithm.HMAC256(secret))
    }

    private fun createJwtCreatorBaseBuilder(userId: UUID): JWTCreator.Builder {
        return JWT.create()
            .withAudience(audience)
            .withIssuer(issuer)
            .withJWTId(UUID.randomUUID().toString())
            .withSubject(userId.toString())
    }

    override suspend fun customValidator(credential: JWTCredential, refreshToken: String): JWTPrincipal? {
        val decodedRefreshToken = getVerifiedDecodedJwt(refreshToken)
        val sessionIdString = decodedRefreshToken?.getClaim("sessionId")?.asString() ?: return null

        val sessionId = UUID.fromString(sessionIdString)

        if (sessionService.isSessionValid(sessionId, refreshToken)) {
            return JWTPrincipal(credential.payload)
        }

        return null
    }

    override fun verifyRefreshToken(token: String): DecodedJWT? {
        return getVerifiedDecodedJwt(token)
    }

    private fun getVerifiedDecodedJwt(token: String): DecodedJWT? {
        return try {
            jwtVerifier.verify(token)
        } catch (ex: Exception) {
            null
        }
    }

    private fun getConfigProperty(path: String) =
        System.getenv(path) ?: application.environment.config.property(path).getString()
}