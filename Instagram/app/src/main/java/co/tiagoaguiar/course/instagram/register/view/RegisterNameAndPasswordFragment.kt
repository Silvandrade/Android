package co.tiagoaguiar.course.instagram.register.view

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import co.tiagoaguiar.course.instagram.R
import co.tiagoaguiar.course.instagram.common.base.DependencyInjector
import co.tiagoaguiar.course.instagram.common.util.CustomTextWatcher
import co.tiagoaguiar.course.instagram.databinding.FragmentRegisterNamePasswordBinding
import co.tiagoaguiar.course.instagram.register.RegisterNameAndPassword
import co.tiagoaguiar.course.instagram.register.presenter.RegisterNameAndPasswordPresenter

class RegisterNameAndPasswordFragment: Fragment(R.layout.fragment_register_name_password),
    RegisterNameAndPassword.View {

    private var binding: FragmentRegisterNamePasswordBinding? = null
    private var fragmentAttachListener: FragmentAttachListener? = null
    override lateinit var presenter: RegisterNameAndPassword.Presenter

    companion object {
        const val KEY_EMAIL = "key_email"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentRegisterNamePasswordBinding.bind(view)
        // Recuperando o emal repassado pelo fragmento anterior.
        val email = arguments?.getString(KEY_EMAIL) ?: throw IllegalArgumentException("Email not found")

        binding?.let {
            with(it) {
                registerEditName.addTextChangedListener(CustomTextWatcher {
                    registerNameBtnNext.isEnabled = formIsFilled()
                    displayNameFailure(null)
                })

                registerEditPassword.addTextChangedListener(CustomTextWatcher {
                    registerNameBtnNext.isEnabled = formIsFilled()
                    displayPasswordFailure(null)
                })

                registerEditConfirmPassword.addTextChangedListener(CustomTextWatcher {
                    registerNameBtnNext.isEnabled = formIsFilled()
                    displayPasswordFailure(null)
                })

                registerNameBtnNext.setOnClickListener {
                    val repository = DependencyInjector.registerNameAndPassword()
                    presenter = RegisterNameAndPasswordPresenter(this@RegisterNameAndPasswordFragment, repository)
                    presenter.registerNameAndPassword(
                        email,
                        registerEditName.text.toString(),
                        registerEditPassword.text.toString(),
                        registerEditConfirmPassword.text.toString()
                    )
                }

                registerTxtLogin.setOnClickListener {
                    activity?.finish()
                }
            }
        }
    }

    private fun formIsFilled(): Boolean {
        var formIsValidated = false

        binding?.let {
            with(it) {
                formIsValidated = (registerEditName.text.toString().isNotEmpty()
                        && registerEditPassword.text.toString().isNotEmpty()
                        && registerEditConfirmPassword.text.toString().isNotEmpty())
            }
        }

        return formIsValidated
    }

    override fun showProgress(enabled: Boolean) {
        binding?.registerNameBtnNext?.showProgress(enabled)
    }

    override fun displayNameFailure(nameError: Int?) {
        binding?.registerEditNameInput?.error = nameError?.let { getString(it) }
    }

    override fun displayPasswordFailure(passwordError: Int?) {
        binding?.registerEditPasswordInput?.error = passwordError?.let { getString(it) }
    }

    override fun onRegisterFailure(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    override fun goToWelcomeScreen(name: String) {
        fragmentAttachListener?.goToWelcomeScreen(name)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is FragmentAttachListener) {
            fragmentAttachListener = context
        }
    }

    override fun onDestroy() {
        binding = null
        fragmentAttachListener = null
        presenter.onDestroy()
        super.onDestroy()
    }
}