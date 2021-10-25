package co.tiagoaguiar.course.instagram.register.presenter

import android.util.Patterns
import co.tiagoaguiar.course.instagram.R
import co.tiagoaguiar.course.instagram.register.RegisterEmail
import co.tiagoaguiar.course.instagram.register.data.RegisterEmailCallback
import co.tiagoaguiar.course.instagram.register.data.RegisterEmailRepository

class RegisterEmailPresenter(
    // A referência dos objetos view e repository são passados pelo view.
    private var view: RegisterEmail.View?,
    private var repository: RegisterEmailRepository): RegisterEmail.Presenter {

    override fun registerEmail(email: String) { // A view passa o valor do campo email.
        // Verificando se o email é válido
        val isEmailValid: Boolean = Patterns.EMAIL_ADDRESS.matcher(email).matches()

        if(!isEmailValid) { // Se for inválido.
            view?.displayEmailFailure(R.string.invalid_email) // Exibir menssagem de erro.
        } else { // Se o email for válido.
            view?.showProgress(true) // Exibir a progressbar do botão.

            // Envia os dados para o Model junto com uma referência para receber um retorno.
            repository.registerEmail(email, object: RegisterEmailCallback {
                override fun onSuccess() { // Caso retorne sucesso.
                    view?.goToNameAndPasswordScreen(email) // Vou para próxima tela.
                }

                override fun onFailure(message: String) { // Caso retorne falha.
                    view?.onEmailFailure(message) // repassada a mensagem de erro vinda do servidor.
                }

                override fun onComplete() { // Em qualquer caso.
                    view?.showProgress(false) // Feche o progressbar.
                }
            })
        }
    }

    override fun onDestroy() {
        view = null
    }

}