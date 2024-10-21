package com.github.mantasjasikenas.db.tables

import com.github.mantasjasikenas.data.user.User
import com.github.mantasjasikenas.data.user.UserDto
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.kotlin.datetime.CurrentDateTime
import org.jetbrains.exposed.sql.kotlin.datetime.datetime
import java.util.*


object UsersTable : UUIDTable() {
    val userName = varchar("user_name", 255)
    val email = varchar("email", 255)
    val password = varchar("password", 255)
    val createdAt = datetime("created_at").defaultExpression(CurrentDateTime)
}


class UserDAO(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<UserDAO>(UsersTable)

    var userName by UsersTable.userName
    var email by UsersTable.email
    var password by UsersTable.password
    var createdAt by UsersTable.createdAt

    val roles by UserRoleDAO referrersOn UsersRolesTable.userId
}


fun daoToModel(dao: UserDAO) = User(
    id = dao.id.value,
    username = dao.userName,
    email = dao.email,
    roles = dao.roles.map { it.role },
    password = dao.password,
)

fun daoToDto(dao: UserDAO) = UserDto(
    id = dao.id.value.toString(),
    username = dao.userName,
    email = dao.email,
    roles = dao.roles.map { it.role },
)