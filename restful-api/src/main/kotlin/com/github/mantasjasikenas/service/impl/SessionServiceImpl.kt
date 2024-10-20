package com.github.mantasjasikenas.service.impl

import com.github.mantasjasikenas.db.suspendTransaction
import com.github.mantasjasikenas.db.tables.SessionDAO
import com.github.mantasjasikenas.db.tables.SessionsTable
import com.github.mantasjasikenas.db.tables.daoToModel
import com.github.mantasjasikenas.service.SessionService
import com.github.mantasjasikenas.util.toSHA256
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.update
import java.util.*

class SessionServiceImpl : SessionService {
    override suspend fun createSession(sessionId: UUID, userId: UUID, refreshToken: String, expiresAt: LocalDateTime) {
        suspendTransaction {
            SessionsTable.insert {
                it[id] = sessionId
                it[this.userId] = userId
                it[lastRefreshToken] = refreshToken.toSHA256()
                it[this.expiresAt] = expiresAt
                it[isRevoked] = false
            }
        }
    }

    override suspend fun extendSession(sessionId: UUID, refreshToken: String, expiresAt: LocalDateTime) {
        suspendTransaction {
            SessionsTable.update({ SessionsTable.id eq sessionId }) {
                it[lastRefreshToken] = refreshToken.toSHA256()
                it[this.expiresAt] = expiresAt
            }
        }
    }

    override suspend fun invalidateSession(sessionId: UUID) {
        suspendTransaction {
            SessionsTable.update({ SessionsTable.id eq sessionId }) {
                it[isRevoked] = true
            }
        }
    }

    override suspend fun isSessionValid(sessionId: UUID, refreshToken: String): Boolean {
        val session = suspendTransaction {
            SessionDAO.findById(sessionId)?.let(::daoToModel)
        }

        return session != null &&
                session.expiresAt > Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()) &&
                session.lastRefreshToken == refreshToken.toSHA256() &&
                !session.isRevoked
    }
}