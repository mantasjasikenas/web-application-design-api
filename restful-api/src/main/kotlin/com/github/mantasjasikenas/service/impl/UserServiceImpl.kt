package com.github.mantasjasikenas.service.impl

import com.github.mantasjasikenas.model.AuthResponse
import com.github.mantasjasikenas.model.Role
import com.github.mantasjasikenas.model.auth.LoginDto
import com.github.mantasjasikenas.model.user.*
import com.github.mantasjasikenas.repository.UserRepository
import com.github.mantasjasikenas.service.JwtService
import com.github.mantasjasikenas.service.UserService

class UserServiceImpl(
    private val userRepository: UserRepository,
    private val jwtService: JwtService,
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

    override suspend fun update(id: String, updateUserDto: UpdateUserDto): User? {
        return userRepository.update(id, updateUserDto)
    }

    override suspend fun authenticate(loginDto: LoginDto): AuthResponse? {
        val username = loginDto.username
        val foundUser: User? = userRepository.findByUsername(username)

        if (foundUser == null || loginDto.password != foundUser.password) {
            return null
        }

        update(id = foundUser.id.toString(), updateUserDto = UpdateUserDto(forceRelogin = false)) ?: return null

        val accessToken = jwtService.createAccessToken(username, foundUser.id.toString(), foundUser.roles)
        val refreshToken = jwtService.createRefreshToken(foundUser.id.toString())

        return AuthResponse(
            accessToken = accessToken,
            refreshToken = refreshToken,
        )
    }

    override suspend fun refreshToken(token: String): AuthResponse? {
        val decodedRefreshToken = jwtService.verifyRefreshToken(token) ?: return null

        val userId: String = decodedRefreshToken.subject

        val foundUser: User? = userRepository.findById(userId)
        val userIdFromRefreshToken: String? = decodedRefreshToken.subject

        if (foundUser == null || userIdFromRefreshToken != foundUser.id.toString() || foundUser.forceRelogin) {
            return null
        }
        
        val accessToken = jwtService.createAccessToken(foundUser.username, userId, foundUser.roles)
        val refreshToken = jwtService.createRefreshToken(userId)

        return AuthResponse(
            accessToken = accessToken,
            refreshToken = refreshToken,
        )
    }
}