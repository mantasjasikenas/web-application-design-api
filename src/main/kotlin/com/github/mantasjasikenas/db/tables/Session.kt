package com.github.mantasjasikenas.db.tables

import com.github.mantasjasikenas.data.auth.SessionDto
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.kotlin.datetime.CurrentDateTime
import org.jetbrains.exposed.sql.kotlin.datetime.datetime
import java.util.*


object SessionsTable : UUIDTable() {
    val lastRefreshToken = varchar("last_refresh_token", 255)
    val initiatedAt = datetime("initiated_at").defaultExpression(CurrentDateTime)
    val expiresAt = datetime("expires_at").defaultExpression(CurrentDateTime)
    val isRevoked = bool("is_revoked").default(false)
    val userId = reference("user_id", UsersTable, onDelete = ReferenceOption.CASCADE)
}


class SessionDAO(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<SessionDAO>(SessionsTable)

    var lastRefreshToken by SessionsTable.lastRefreshToken
    var initiatedAt by SessionsTable.initiatedAt
    var expiresAt by SessionsTable.expiresAt
    var isRevoked by SessionsTable.isRevoked
    var userId by SessionsTable.userId

    val user by UserDAO referencedOn SessionsTable.userId
}


fun daoToModel(dao: SessionDAO) = SessionDto(
    id = dao.id.value.toString(),
    lastRefreshToken = dao.lastRefreshToken,
    initiatedAt = dao.initiatedAt,
    expiresAt = dao.expiresAt,
    isRevoked = dao.isRevoked,
    userId = dao.userId.value.toString(),
    user = dao.user.let(::daoToDto)
)