package com.github.mantasjasikenas.routes

import com.github.mantasjasikenas.model.*
import com.github.mantasjasikenas.model.auth.LoginDto
import com.github.mantasjasikenas.model.auth.RefreshAccessTokenDto
import com.github.mantasjasikenas.model.auth.SuccessfulLoginDto
import com.github.mantasjasikenas.model.user.PostUserDto
import com.github.mantasjasikenas.model.user.toSuccessfulRegisterDto
import com.github.mantasjasikenas.service.UserService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.routing.*


fun Route.authRoutes(userService: UserService) {
    route("/auth") {
        post("/register") {
            val postUserDto = call.receive<PostUserDto>()

            userService.findByUsername(postUserDto.username)?.let {
                return@post call.respondCustom(
                    message = "User with username ${postUserDto.username} already exists",
                    status = HttpStatusCode.UnprocessableEntity
                )
            }

            val createdUser = userService.new(postUserDto) ?: return@post call.respondCustom(
                message = "Failed to create user",
                status = HttpStatusCode.UnprocessableEntity
            )

            userService.assignRole(createdUser.id, Role.User)

            call.respondCreated(
                "User created successfully",
                createdUser.toSuccessfulRegisterDto()
            )
        }

        post("/login") {
            val loginDto = call.receive<LoginDto>()

            val authResponse: AuthResponse? = userService.authenticate(loginDto)

            authResponse?.let {
                call.respondSuccess(
                    message = "Login successful",
                    data = SuccessfulLoginDto(
                        accessToken = it.accessToken,
                        refreshToken = it.refreshToken
                    )
                )
            } ?: call.respondCustom(
                message = "Username or password is incorrect",
                status = HttpStatusCode.UnprocessableEntity
            )
        }

        post("/accessToken") {
            val refreshAccessTokenDto = call.receive<RefreshAccessTokenDto>()

            val authResponse = userService.refreshToken(token = refreshAccessTokenDto.refreshToken)

            authResponse?.let {
                call.respondSuccess(
                    message = "Access token refreshed successfully",
                    data = SuccessfulLoginDto(
                        accessToken = it.accessToken,
                        refreshToken = it.refreshToken
                    )
                )
            } ?: call.respondCustom(
                message = "Unprocessable entity",
                status = HttpStatusCode.UnprocessableEntity
            )
        }
    }
}

