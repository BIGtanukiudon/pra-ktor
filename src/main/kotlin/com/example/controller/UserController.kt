package com.example.controller

import com.example.model.DUser
import com.example.model.User
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import org.jetbrains.exposed.sql.transactions.transaction

fun Route.userController() {
    route("/user") {
        get {
            var res: DUser = DUser(id = 0, name = "", age = 0);
            transaction {
                val user = User.findById(1)

                if (user != null) {
                    res = DUser(
                        id = user.id.value,
                        name = user.name,
                        age = user.age
                    )
                }
            }
            call.respond(HttpStatusCode.OK, res)

            // call.respondText { "user routing ok" }
        }
        post {
            transaction {
                User.new {
                    name = "テスト花子"
                    age = 25
                }
            }

            call.respond(HttpStatusCode.OK)
        }
    }
}
