package co.tiagoaguiar.course.instagram.common.model

import java.util.*

object Database { // Banco de dados falso.

    val usersAuth = hashSetOf<UserAuth>() // Trabalhar com identificador único
    var sessionAuth: UserAuth? = null

    init { // Gerando contas com identificadores aleatórios e únicos.
        usersAuth.add(UserAuth(UUID.randomUUID().toString(), "UserA", "userA@gmail.com", "12345678"))
        usersAuth.add(UserAuth(UUID.randomUUID().toString(), "UserB","userB@gmail.com", "87654321"))
    }
}