package com.github.mantasjasikenas.util

import com.github.mantasjasikenas.model.Role
import com.github.mantasjasikenas.plugins.RoleBasedAuthorizationPlugin
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Route.authorized(
    vararg hasAnyRole: Role,
    build: Route.() -> Unit
) {
    install(RoleBasedAuthorizationPlugin) { roles = hasAnyRole.toSet() }
    build()
}
