package co.tiagoaguiar.course.instagram.register

import androidx.annotation.StringRes
import co.tiagoaguiar.course.instagram.common.base.BasePresenter
import co.tiagoaguiar.course.instagram.common.base.BaseView

interface RegisterNameAndPassword {

    interface  Presenter: BasePresenter {
        fun registerNameAndPassword(email: String, name: String, password: String, confirmPassword: String)
    }

    interface View: BaseView<Presenter> {
        fun showProgress(enabled: Boolean)
        fun displayNameFailure(@StringRes nameError: Int?)
        fun displayPasswordFailure(@StringRes passwordError: Int?)
        fun onRegisterFailure(message: String)
        fun goToWelcomeScreen(name: String)
    }
}