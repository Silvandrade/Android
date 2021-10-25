package co.tiagoaguiar.course.instagram.login.presenter

import android.util.Log
import android.util.Patterns
import co.tiagoaguiar.course.instagram.R
import co.tiagoaguiar.course.instagram.common.model.UserAuth
import co.tiagoaguiar.course.instagram.login.Login
import co.tiagoaguiar.course.instagram.login.data.LoginCallback
import co.tiagoaguiar.course.instagram.login.data.LoginRepository

class LoginPresenter(
    private var view: Login.View?,
    private var repository: LoginRepository) : Login.Presenter {

    override fun login(email: String, password: String) {
        val isEmailValid: Boolean = Patterns.EMAIL_ADDRESS.matcher(email).matches()
        val isPasswordValid: Boolean = password.length >= 8;

        if(!isEmailValid) {
            view?.displayEmailFailure(R.string.invalid_email)
        } else {
            view?.displayEmailFailure(null)
        }

        if(!isPasswordValid) {
            view?.displayPasswordFailure(R.string.invalid_password)
        } else {
            view?.displayPasswordFailure(null)
        }

        if(isEmailValid && isPasswordValid) {
            view?.showProgress(true)

            repository.login(email, password, object: LoginCallback {
                override fun onSuccess(userAuth: UserAuth) { // Se o model devolver sucesso.
                    view?.onUserAuthenticated() // Informa a view que o usuário foi a autenticado
                }

                override fun onFailure(message: String) { // Se o model devolver falha.
                    view?.onUserUnauthorized(message) // Informa a view sobre o erro.
                }

                override fun onComplete() { // Sempre será executado.
                    view?.showProgress(false) // Informa a view para esconder a progress.
                }
            })
        }
    }

    override fun onDestroy() {
        view = null
    }
}