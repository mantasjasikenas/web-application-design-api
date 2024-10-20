package com.github.mantasjasikenas.service

import kotlinx.datetime.LocalDateTime
import java.util.*

interface SessionService {
    suspend fun createSession(sessionId: UUID, userId: UUID, refreshToken: String, expiresAt: LocalDateTime)
    suspend fun extendSession(sessionId: UUID, refreshToken: String, expiresAt: LocalDateTime)
    suspend fun invalidateSession(sessionId: UUID)
    suspend fun isSessionValid(sessionId: UUID, refreshToken: String): Boolean
}

