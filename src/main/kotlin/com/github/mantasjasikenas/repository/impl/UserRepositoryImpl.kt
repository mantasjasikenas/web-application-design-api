package com.github.mantasjasikenas.repository.impl

import com.github.mantasjasikenas.db.suspendTransaction
import com.github.mantasjasikenas.db.tables.UserDAO
import com.github.mantasjasikenas.db.tables.UserRoleDAO
import com.github.mantasjasikenas.db.tables.UsersTable
import com.github.mantasjasikenas.db.tables.daoToModel
import com.github.mantasjasikenas.data.Role
import com.github.mantasjasikenas.data.user.PostUserDto
import com.github.mantasjasikenas.data.user.UpdateUserDto
import com.github.mantasjasikenas.data.user.User
import com.github.mantasjasikenas.repository.UserRepository
import org.jetbrains.exposed.dao.id.EntityID
import java.util.*

class UserRepositoryImpl : UserRepository {
    override suspend fun all(): List<User> = suspendTransaction {
        UserDAO.all().map(::daoToModel)
    }

    override suspend fun findById(id: String): User? = suspendTransaction {
        UserDAO.findById(UUID.fromString(id))?.let(::daoToModel)
    }

    override suspend fun findByUsername(username: String): User? = suspendTransaction {
        UserDAO.find { UsersTable.userName eq username }.firstOrNull()?.let(::daoToModel)
    }

    override suspend fun new(postUserDto: PostUserDto): User = suspendTransaction {
        UserDAO.new {
            userName = postUserDto.username
            email = postUserDto.email
            password = postUserDto.password
        }.let(::daoToModel)
    }

    override suspend fun assignRole(userId: String, role: Role): Boolean = suspendTransaction {
        UserRoleDAO.new {
            this.userId = EntityID(UUID.fromString(userId), UsersTable)
            this.role = role
        }

        return@suspendTransaction true
    }

    override suspend fun newAndAssignRole(postUserDto: PostUserDto, role: Role): User = suspendTransaction {
        val userDao = UserDAO.new {
            userName = postUserDto.username
            email = postUserDto.email
            password = postUserDto.password
        }

        UserRoleDAO.new {
            this.userId = EntityID(userDao.id.value, UsersTable)
            this.role = role
        }

        return@suspendTransaction daoToModel(userDao)
    }

    override suspend fun delete(id: String): Boolean = suspendTransaction {
        UserDAO.findById(UUID.fromString(id))?.delete() ?: false

        return@suspendTransaction true
    }

    override suspend fun update(id: String, userDto: UpdateUserDto): User? = suspendTransaction {
        UserDAO.findByIdAndUpdate(UUID.fromString(id)) {
            it.userName = userDto.username ?: it.userName
            it.email = userDto.email ?: it.email
            it.password = userDto.password ?: it.password
        }?.let(::daoToModel)
    }
}