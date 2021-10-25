package co.tiagoaguiar.course.instagram.register.presenter

import co.tiagoaguiar.course.instagram.R
import co.tiagoaguiar.course.instagram.register.RegisterNameAndPassword
import co.tiagoaguiar.course.instagram.register.data.RegisterNameAndPasswordCallback
import co.tiagoaguiar.course.instagram.register.data.RegisterNameAndPasswordRepository

class RegisterNameAndPasswordPresenter(
    private var view: RegisterNameAndPassword.View?,
    private var repository: RegisterNameAndPasswordRepository): RegisterNameAndPassword.Presenter {

    override fun registerNameAndPassword(
        email: String,
        name: String,
        password: String,
        confirmPassword: String
    ) {
        val isNameValid = name.length > 3
        val isPasswordValid = password.length > 8 && password == confirmPassword

        if(!isNameValid) {
            view?.displayNameFailure(R.string.invalid_name)
        } else if(!isPasswordValid) {
            view?.displayPasswordFailure(R.string.invalid_password)
        } else if(password != confirmPassword) {
            view?.displayPasswordFailure(R.string.password_not_equal)
        } else {
            repository.registerNameAndPassword(email, name, password,
                object: RegisterNameAndPasswordCallback {
                override fun onSuccess() {
                    view?.goToWelcomeScreen(name)
                }

                override fun onFailure(message: String) {
                    view?.onRegisterFailure(message)
                }

                override fun onComplete() {
                    view?.showProgress(false)
                }
            })
        }
    }

    override fun onDestroy() {
        view = null
    }


}