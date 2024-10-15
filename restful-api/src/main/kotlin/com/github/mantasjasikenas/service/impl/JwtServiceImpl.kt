package com.github.mantasjasikenas.service.impl

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTCreator
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.DecodedJWT
import com.github.mantasjasikenas.model.Role
import com.github.mantasjasikenas.model.user.User
import com.github.mantasjasikenas.repository.UserRepository
import com.github.mantasjasikenas.service.JwtService
import io.ktor.server.application.*
import io.ktor.server.auth.jwt.*
import java.util.*
import kotlin.time.Duration.Companion.days
import kotlin.time.Duration.Companion.minutes

class JwtServiceImpl(
    private val application: Application,
    private val userRepository: UserRepository,
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

    override fun createAccessToken(username: String, userId: String, roles: List<Role>): String {
        return createJwtCreatorBaseBuilder(userId)
            .withClaim("username", username)
            .withArrayClaim("roles", roles.map { it.name }.toTypedArray())
            .withExpiresAt(Date(System.currentTimeMillis() + 5.minutes.inWholeMilliseconds))
            .sign(Algorithm.HMAC256(secret))
    }

    override fun createRefreshToken(userId: String): String {
        return createJwtCreatorBaseBuilder(userId)
            .withExpiresAt(Date(System.currentTimeMillis() + 30.days.inWholeMilliseconds))
            .sign(Algorithm.HMAC256(secret))
    }

    private fun createJwtCreatorBaseBuilder(userId: String): JWTCreator.Builder {
        return JWT.create()
            .withAudience(audience)
            .withIssuer(issuer)
            .withJWTId(UUID.randomUUID().toString())
            .withSubject(userId)
    }

    override suspend fun customValidator(credential: JWTCredential): JWTPrincipal? {
        val username: String = extractUsername(credential) ?: return null
        val foundUser: User = userRepository.findByUsername(username) ?: return null

        return if (audienceMatches(credential) && foundUser.forceRelogin.not()) {
            JWTPrincipal(credential.payload)
        } else {
            null
        }
    }

    private fun audienceMatches(credential: JWTCredential): Boolean {
        return credential.payload.audience.contains(audience)
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

    private fun extractUsername(credential: JWTCredential): String? =
        credential.payload.getClaim("username").asString()
}