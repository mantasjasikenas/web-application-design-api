﻿package com.github.mantasjasikenas.repository

import com.github.mantasjasikenas.data.Role
import com.github.mantasjasikenas.data.user.PostUserDto
import com.github.mantasjasikenas.data.user.UpdateUserDto
import com.github.mantasjasikenas.data.user.User

interface UserRepository {
    suspend fun all(): List<User>
    suspend fun findById(id: String): User?
    suspend fun findByUsername(username: String): User?
    suspend fun new(postUserDto: PostUserDto): User?
    suspend fun newAndAssignRole(postUserDto: PostUserDto, role: Role): User?
    suspend fun assignRole(userId: String, role: Role): Boolean
    suspend fun delete(id: String): Boolean
    suspend fun update(id: String, userDto: UpdateUserDto): User?
}