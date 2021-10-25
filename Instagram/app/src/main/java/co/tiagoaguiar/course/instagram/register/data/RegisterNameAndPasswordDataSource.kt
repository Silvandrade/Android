package co.tiagoaguiar.course.instagram.register.data

interface RegisterNameAndPasswordDataSource {
    fun registerNameAndPassword(email: String, name: String, password: String,
                                callback: RegisterNameAndPasswordCallback)
}