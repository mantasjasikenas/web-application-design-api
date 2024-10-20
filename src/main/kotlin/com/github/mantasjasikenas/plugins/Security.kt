package com.github.mantasjasikenas.plugins

import com.github.mantasjasikenas.service.JwtService
import com.github.mantasjasikenas.util.getRefreshTokenFromCookies
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*

fun Application.configureSecurity(
    jwtService: JwtService
) {
    authentication {
        jwt {
            realm = jwtService.realm
            verifier(jwtService.jwtVerifier)

            validate { credential ->
                val refreshToken = this.getRefreshTokenFromCookies() ?: return@validate null

                jwtService.customValidator(credential, refreshToken)
            }
        }
    }
}

