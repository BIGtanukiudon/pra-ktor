package com.example

import com.example.controller.userController
import com.example.model.User
import com.example.model.UsersTable
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.jackson.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.exists
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

fun main() {
    Database.connect("jdbc:postgresql://localhost:5432/praktor", driver = "org.postgresql.Driver", user = "praktor", password = "praktor")
    transaction {
        SchemaUtils.create(UsersTable)
        if (!UsersTable.exists()) {
            User.new {
                name = "テスト太郎"
                age = 29
            }
        }
    }

    embeddedServer(Netty, port = 8080) {
        install(ContentNegotiation) {
            jackson()
        }

        routing {
            userController()
        }
    }.start(wait = true)
}
