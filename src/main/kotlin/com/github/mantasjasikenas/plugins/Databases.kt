package com.github.mantasjasikenas.plugins

import com.github.mantasjasikenas.db.tables.*
import io.ktor.server.application.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

fun Application.configureDatabases() {
    val url = System.getenv("DB_URL")

    Database.connect(
        url,
        driver = "org.postgresql.Driver",
    )

    transaction {
        SchemaUtils.createMissingTablesAndColumns(
            TasksTable,
            ProjectsTable,
            SectionsTable,
            UsersRolesTable,
            UsersTable,
            SessionsTable
        )
    }
}
