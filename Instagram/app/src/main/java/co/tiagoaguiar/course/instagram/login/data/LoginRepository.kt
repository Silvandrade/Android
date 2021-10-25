package co.tiagoaguiar.course.instagram.login.data

class LoginRepository(private val dataSource: LoginDataSource) { // Responsável por decidir onde buscar os dados.

    fun login(email: String, password: String, callback: LoginCallback) {
        dataSource.login(email, password, callback)
    }
}