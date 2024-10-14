package com.github.mantasjasikenas.util

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