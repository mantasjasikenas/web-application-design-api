package com.github.mantasjasikenas.docs.auth

import com.github.mantasjasikenas.data.auth.LoginDto
import com.github.mantasjasikenas.data.user.PostUserDto
import com.github.mantasjasikenas.docs.createdResponse
import com.github.mantasjasikenas.docs.okResponse
import com.github.mantasjasikenas.docs.unprocessableEntityResponse
import io.github.smiley4.ktorswaggerui.dsl.routes.OpenApiRoute


fun authRoutesDocs(): OpenApiRoute.() -> Unit = {
    description = "Auth routes"
    tags("Auth")
}

fun registerDocs(): OpenApiRoute.() -> Unit = {
    description = "Register new user"

    request {
        body<PostUserDto> {
            description = "User to register"
            example("default") {
                value = postUserDtoExample
            }
        }
    }

    response {
        createdResponse(
            description = "User created",
            message = "User created successfully",
            data = successfulRegisterDtoExample
        )

        unprocessableEntityResponse(message = "User with username ${postUserDtoExample.username} already exists")
    }
}

fun loginDocs(): OpenApiRoute.() -> Unit = {
    description = "Login user"

    request {
        body<LoginDto> {
            description = "User to login"
            example("default") {
                value = loginDtoExample
            }
        }
    }

    response {
        okResponse(
            description = "Login successful",
            message = "Login successful",
            data = successfulLoginDtoExample
        )

        unprocessableEntityResponse(message = "Username or password is incorrect")
    }
}

fun accessTokenDocs(): OpenApiRoute.() -> Unit = {
    description = "Refresh access token"

    request {
        cookieParameter<String>("RefreshToken") {
            description = "Refresh token"
            required = true
            example("default") {
                value = refreshTokenExample
            }
        }
    }

    response {
        okResponse(
            description = "Access token refreshed successfully",
            message = "Access token refreshed successfully",
            data = successfulLoginDtoExample
        )

        unprocessableEntityResponse(message = "Unprocessable entity")
    }
}

fun logoutDocs(): OpenApiRoute.() -> Unit = {
    description = "Logout user"

    response {
        okResponse<Unit>(
            description = "Logout successful",
            message = "Logout successful"
        )

        unprocessableEntityResponse(message = "Failed to logout")
    }
}
