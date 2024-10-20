package com.github.mantasjasikenas.service

import com.github.mantasjasikenas.model.AuthResponse
import com.github.mantasjasikenas.model.Role
import com.github.mantasjasikenas.model.auth.LoginDto
import com.github.mantasjasikenas.model.user.PostUserDto
import com.github.mantasjasikenas.model.user.UpdateUserDto
import com.github.mantasjasikenas.model.user.User
import com.github.mantasjasikenas.model.user.UserDto

interface UserService {
    suspend fun all(): List<UserDto>
    suspend fun findById(id: String): UserDto?
    suspend fun findByUsername(username: String): UserDto?
    suspend fun new(postUserDto: PostUserDto): UserDto?
    suspend fun assignRole(userId: String, role: Role): Boolean
    suspend fun newAndAssignRole(postUserDto: PostUserDto, role: Role): UserDto?
    suspend fun update(id: String, updateUserDto: UpdateUserDto): User?
    suspend fun authenticate(loginDto: LoginDto): AuthResponse?
    suspend fun logout(refreshToken: String): Boolean
    suspend fun refreshToken(refreshToken: String): AuthResponse?
}