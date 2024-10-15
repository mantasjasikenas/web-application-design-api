package com.github.mantasjasikenas.db.tables

import com.github.mantasjasikenas.model.Role
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ReferenceOption

object UsersRolesTable : IntIdTable("users_roles") {
    val userId = reference("user_id", UsersTable, onDelete = ReferenceOption.CASCADE)
    val roleId = customEnumeration(
        name = "role_id",
        sql = null,
        fromDb = { value -> Role.entries.first { it.id == value } },
        toDb = { it.id }
    )
}

class UserRoleDAO(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<UserRoleDAO>(UsersRolesTable)

    var userId by UsersRolesTable.userId
    var role by UsersRolesTable.roleId
}