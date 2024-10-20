package com.github.mantasjasikenas.service.impl

import com.github.mantasjasikenas.model.AuthResponse
import com.github.mantasjasikenas.model.Role
import com.github.mantasjasikenas.model.auth.LoginDto
import com.github.mantasjasikenas.model.user.*
import com.github.mantasjasikenas.repository.UserRepository
import com.github.mantasjasikenas.service.JwtService
import com.github.mantasjasikenas.service.SessionService
import com.github.mantasjasikenas.service.UserService
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import java.util.*
import kotlin.time.Duration.Companion.days

class UserServiceImpl(
    private val userRepository: UserRepository,
    private val jwtService: JwtService,
    private val sessionService: SessionService
) : UserService {

    override suspend fun all(): List<UserDto> {
        return userRepository.all().map(::modelToDto)
    }

    override suspend fun findById(id: String): UserDto? {
        return userRepository.findById(id)?.let(::modelToDto)
    }

    override suspend fun findByUsername(username: String): UserDto? {
        return userRepository.findByUsername(username)?.let(::modelToDto)
    }

    override suspend fun new(postUserDto: PostUserDto): UserDto? {
        return userRepository.new(postUserDto)?.let(::modelToDto)
    }

    override suspend fun assignRole(userId: String, role: Role): Boolean {
        return userRepository.assignRole(userId, role)
    }

    override suspend fun newAndAssignRole(postUserDto: PostUserDto, role: Role): UserDto? {
        return userRepository.newAndAssignRole(postUserDto, role)?.let(::modelToDto)
    }

    override suspend fun update(id: String, updateUserDto: UpdateUserDto): User? {
        return userRepository.update(id, updateUserDto)
    }

    override suspend fun authenticate(loginDto: LoginDto): AuthResponse? {
        val username = loginDto.username
        val foundUser: User? = userRepository.findByUsername(username)

        if (foundUser == null || loginDto.password != foundUser.password) {
            return null
        }

        val userId = foundUser.id

        val sessionId = UUID.randomUUID()
        val expiresAt = Clock.System.now().plus(3.days).toLocalDateTime(TimeZone.UTC)

        val accessToken = jwtService.createAccessToken(username, userId, foundUser.roles)
        val refreshToken = jwtService.createRefreshToken(sessionId, userId, expiresAt)

        sessionService.createSession(sessionId, userId, refreshToken, expiresAt)

        return AuthResponse(
            accessToken = accessToken,
            refreshToken = refreshToken,
        )
    }

    override suspend fun logout(refreshToken: String): Boolean {
        val decodedRefreshToken = jwtService.verifyRefreshToken(refreshToken) ?: return false

        val sessionId = decodedRefreshToken.getClaim("sessionId").asString() ?: return false
        val sessionIdAsUUID = UUID.fromString(sessionId)

        if (!sessionService.isSessionValid(sessionIdAsUUID, refreshToken)) {
            return false
        }

        sessionService.invalidateSession(sessionIdAsUUID)

        return true
    }

    override suspend fun refreshToken(refreshToken: String): AuthResponse? {
        val decodedRefreshToken = jwtService.verifyRefreshToken(refreshToken) ?: return null

        val sessionId = decodedRefreshToken.getClaim("sessionId").asString() ?: return null
        val sessionIdAsUUID = UUID.fromString(sessionId)

        if (!sessionService.isSessionValid(sessionIdAsUUID, refreshToken)) {
            return null
        }

        val userIdFromRefreshToken: String = decodedRefreshToken.subject
        val foundUser: User = userRepository.findById(userIdFromRefreshToken) ?: return null

        val userId = foundUser.id

        val expiresAt = Clock.System.now().plus(3.days).toLocalDateTime(TimeZone.UTC)

        val newAccessToken = jwtService.createAccessToken(foundUser.username, userId, foundUser.roles)
        val newRefreshToken = jwtService.createRefreshToken(sessionIdAsUUID, userId, expiresAt)

        sessionService.extendSession(sessionIdAsUUID, newRefreshToken, expiresAt)

        return AuthResponse(
            accessToken = newAccessToken,
            refreshToken = newRefreshToken,
        )
    }
}