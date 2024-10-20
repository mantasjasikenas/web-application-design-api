package com.github.mantasjasikenas.util

import java.security.MessageDigest

fun String.toSHA256(): String {
    val digest = MessageDigest.getInstance("SHA-256")
    val hash = digest.digest(toByteArray())

    return hash.fold("") { str, it -> str + "%02x".format(it) }
}