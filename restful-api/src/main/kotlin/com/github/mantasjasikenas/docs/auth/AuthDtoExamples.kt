package com.github.mantasjasikenas.docs.auth

import com.github.mantasjasikenas.model.auth.LoginDto
import com.github.mantasjasikenas.model.auth.RefreshAccessTokenDto
import com.github.mantasjasikenas.model.auth.SuccessfulLoginDto
import com.github.mantasjasikenas.model.auth.SuccessfulRegisterDto
import com.github.mantasjasikenas.model.user.PostUserDto

val postUserDtoExample = PostUserDto(
    username = "Sigmund",
    password = "VerySafePassword132",
    email = "sigmund@gmail.com"
)

val successfulRegisterDtoExample = SuccessfulRegisterDto(
    id = "9351f427-fba2-4378-99e9-2b82dc2a9466",
    userName = "Sigmund",
    email = "sigmund@gmail.com",
)

val loginDtoExample = LoginDto(
    username = "Sigmund",
    password = "VerySafePassword132"
)

val successfulLoginDtoExample = SuccessfulLoginDto(
    accessToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOiJUcnVzdGVkQ2xpZW50IiwiaXNzIjoiTWFudGFzIiwianRpIjoiNmYwMzgzYmMtNjY3MC00ZTMxLTg0N2QtMGVlZGYxNWJkZDJkIiwic3ViIjoiYzFjY2FmYTYtM2EwNi00NzA3LThkNDQtNzc3ZWFkZWYzNjM1IiwidXNlcm5hbWUiOiJhZG1pbiIsInJvbGVzIjpbIkFkbWluIl0sImV4cCI6MTcyODkxODU0Mn0.qPQOaKwhjjfMLNriH8D35xP8F81MXGox9wUMVYWwwwo",
)

val refreshAccessTokenDtoExample = RefreshAccessTokenDto(
    refreshToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOiJUcnVzdGVkQ2xpZW50IiwiaXNzIjoiTWFudGFzIiwianRpIjoiNThkMWMzMzQtNmRjMi00OTAyLTg2ZmEtNmUyYjk4ZjE4ODJhIiwic3ViIjoiYzFjY2FmYTYtM2EwNi00NzA3LThkNDQtNzc3ZWFkZWYzNjM1IiwiZXhwIjoxNzI5MDA0MzQyfQ.NwcM5fY4glzUVroLqaGfQEHA7bDkpd0LpVy10DFHJmw"
)
