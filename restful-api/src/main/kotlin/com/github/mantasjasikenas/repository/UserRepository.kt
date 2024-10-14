package com.github.mantasjasikenas.repository

import com.github.mantasjasikenas.model.Role
import com.github.mantasjasikenas.model.user.PostUserDto
import com.github.mantasjasikenas.model.user.UpdateUserDto
import com.github.mantasjasikenas.model.user.User

interface UserRepository {
    suspend fun all(): List<User>
    suspend fun findById(id: String): User?
    suspend fun findByUsername(username: String): User?
    suspend fun new(postUserDto: PostUserDto): User?
    suspend fun assignRole(userId: String, role: Role): Boolean
    suspend fun delete(id: String): Boolean
    suspend fun update(id: String, userDto: UpdateUserDto): User?
}