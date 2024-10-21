package com.github.mantasjasikenas.data

enum class Role(
    val id: Int,
    val value: String
) {
    Admin(0, "Admin"),
    User(1, "User")
}