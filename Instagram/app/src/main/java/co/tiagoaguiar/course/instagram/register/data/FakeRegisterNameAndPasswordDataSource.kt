package co.tiagoaguiar.course.instagram.register.data

import android.os.Handler
import android.os.Looper
import co.tiagoaguiar.course.instagram.common.model.Database
import co.tiagoaguiar.course.instagram.common.model.UserAuth
import java.util.*

class FakeRegisterNameAndPasswordDataSource: RegisterNameAndPasswordDataSource {
    override fun registerNameAndPassword(
        email: String,
        name: String,
        password: String,
        callback: RegisterNameAndPasswordCallback
    ) {
        Handler(Looper.getMainLooper()).postDelayed({

            // Select * From User_Auth Where Email = ? Limit 1
            val userAuth = Database.usersAuth.firstOrNull {
                email == it.email
            }

            if (userAuth != null) {
                callback.onFailure("Usuário já cadastrado")
            } else {
                val created = Database.usersAuth.add(
                    UserAuth(UUID.randomUUID().toString(), name, email, password)
                )

                if(created) {
                    callback.onSuccess()
                } else {
                    callback.onFailure("Erro ao tentar salvar o registro no banco de dados.")
                }
            }

            callback.onComplete()
        }, 2000)
    }
}