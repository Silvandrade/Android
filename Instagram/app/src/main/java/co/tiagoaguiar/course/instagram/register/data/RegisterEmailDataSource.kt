package co.tiagoaguiar.course.instagram.register.data

interface RegisterEmailDataSource {
    fun registerEmail(email: String, callback: RegisterEmailCallback)
}