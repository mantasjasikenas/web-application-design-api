package com.github.mantasjasikenas.util

import com.github.mantasjasikenas.data.Role
import com.github.mantasjasikenas.data.respondBadRequest
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*

fun ApplicationCall.extractClaim(claim: String): String? =
    this.principal<JWTPrincipal>()
        ?.payload
        ?.getClaim(claim)
        ?.asString()

fun ApplicationCall.extractSubject(): String? =
    this.principal<JWTPrincipal>()
        ?.payload
        ?.subject

fun getRoleFromToken(call: ApplicationCall): List<Role>? =
    call.principal<JWTPrincipal>()
        ?.payload
        ?.getClaim("roles")
        ?.asList(String::class.java)
        ?.map(Role::valueOf)

fun ApplicationCall.getRefreshTokenFromCookies(): String? =
    this.request.cookies["RefreshToken"]

suspend fun ApplicationCall.extractSubjectOrRespond(): String? {
    val subject = this.extractSubject()

    if (subject == null) {
        this.respondBadRequest("User id is required")
        return null
    }

    return subject
}

suspend fun ApplicationCall.extractSubjectWithRolesOrRespond(): Pair<String, List<Role>>? {
    val subject = this.extractSubject()

    if (subject == null) {
        this.respondBadRequest("User id is required")
        return null
    }

    val roles = getRoleFromToken(this)

    if (roles == null) {
        this.respondBadRequest("Roles are required")
        return null
    }

    return subject to roles
}

suspend fun ApplicationCall.extractRolesOrRespond(): List<Role>? {
    val roles = this.principal<JWTPrincipal>()
        ?.payload
        ?.getClaim("roles")
        ?.asList(String::class.java)
        ?.map(Role::valueOf)

    if (roles == null) {
        this.respondBadRequest("Roles are required")
        return null
    }

    return roles
}