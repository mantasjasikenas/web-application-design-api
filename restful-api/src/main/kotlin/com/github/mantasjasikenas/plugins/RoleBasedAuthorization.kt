package com.github.mantasjasikenas.plugins

import com.github.mantasjasikenas.model.Role
import com.github.mantasjasikenas.model.respondCustom
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*

class PluginConfiguration {
    var roles: Set<Role> = emptySet()
}

val RoleBasedAuthorizationPlugin = createRouteScopedPlugin(
    name = "RbacPlugin",
    createConfiguration = ::PluginConfiguration
) {
    val authorizedRoles = pluginConfig.roles

    pluginConfig.apply {
        on(AuthenticationChecked) { call ->
            val tokenRoles = getRoleFromToken(call)

            val authorized = tokenRoles?.any { it in authorizedRoles } ?: false

            if (!authorized) {
                call.respondCustom(
                    HttpStatusCode.Forbidden,
                    "User does not have any of the following roles: ${authorizedRoles.joinToString()}"
                )
            }
        }
    }
}

private fun getRoleFromToken(call: ApplicationCall): List<Role>? =
    call.principal<JWTPrincipal>()
        ?.payload
        ?.getClaim("roles")
        ?.asList(String::class.java)
        ?.map(Role::valueOf)