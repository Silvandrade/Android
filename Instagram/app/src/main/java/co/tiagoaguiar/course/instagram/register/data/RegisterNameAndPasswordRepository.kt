package co.tiagoaguiar.course.instagram.register.data

import javax.security.auth.callback.Callback

class RegisterNameAndPasswordRepository(private val dataSource: RegisterNameAndPasswordDataSource) {

    fun registerNameAndPassword(email: String, name: String, password: String,
                                callback: RegisterNameAndPasswordCallback) {
        dataSource.registerNameAndPassword(email, name, password, callback)
    }
}