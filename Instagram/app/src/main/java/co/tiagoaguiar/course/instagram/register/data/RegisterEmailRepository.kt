package co.tiagoaguiar.course.instagram.register.data

class RegisterEmailRepository(private val dataSource: RegisterEmailDataSource) {

    fun registerEmail(email: String, callback: RegisterEmailCallback) {
        dataSource.registerEmail(email, callback)
    }
}