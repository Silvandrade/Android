package co.tiagoaguiar.course.instagram.register.data

interface RegisterNameAndPasswordCallback {
    fun onSuccess()
    fun onFailure(message: String)
    fun onComplete()
}