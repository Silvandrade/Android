package co.tiagoaguiar.course.instagram.register.data

import android.os.Handler
import android.os.Looper
import co.tiagoaguiar.course.instagram.common.model.Database

class FakeRegisterEmailDataSource: RegisterEmailDataSource {

    override fun registerEmail(email: String, callback: RegisterEmailCallback) {
        Handler(Looper.getMainLooper()).postDelayed({

            // Select * From User_Auth Where Email = ? Limit 1
            val userAuth = Database.usersAuth.firstOrNull {
                email == it.email
            }

            if (userAuth == null) {
                callback.onSuccess()
            } else {
                callback.onFailure("Email já cadastrado.")
            }

            callback.onComplete()
        }, 2000)
    }
}