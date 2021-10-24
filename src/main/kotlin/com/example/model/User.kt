package com.example.model

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ResultRow

object UsersTable: IntIdTable(name = "users") {
    val name = varchar("name", 50)
    val age = integer("age")
}

data class DUser(val id: Int, val name: String, val age: Int)

class User(id: EntityID<Int>): IntEntity(id) {
    companion object : IntEntityClass<User>(UsersTable)

    var name by UsersTable.name
    var age by  UsersTable.age
}
