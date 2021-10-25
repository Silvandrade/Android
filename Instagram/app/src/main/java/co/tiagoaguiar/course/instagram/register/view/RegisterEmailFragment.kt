package co.tiagoaguiar.course.instagram.register.view

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import co.tiagoaguiar.course.instagram.R
import co.tiagoaguiar.course.instagram.common.base.DependencyInjector
import co.tiagoaguiar.course.instagram.common.util.CustomTextWatcher
import co.tiagoaguiar.course.instagram.databinding.FragmentRegisterEmailBinding
import co.tiagoaguiar.course.instagram.register.RegisterEmail
import co.tiagoaguiar.course.instagram.register.presenter.RegisterEmailPresenter

// Extendendo o Fragment e passando o layout que será inflado no container. Implementando a interface RegiterEmail.View
class RegisterEmailFragment: Fragment(R.layout.fragment_register_email), RegisterEmail.View {

    private var binding: FragmentRegisterEmailBinding? = null
    private var fragmentAttachListener: FragmentAttachListener? = null
    override lateinit var presenter: RegisterEmail.Presenter // Atributo definido pela interface RegisterEmail.View

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) { // Depois de criar a visualização.
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentRegisterEmailBinding.bind(view) // Capturando a referência do meu fragmento.

        // Se o frgmento não for nulo defino um ouvinte de clique para o componente de texto fazer login.
        binding?.let {
            with(it) {
                // Ao clicar no texto fazer login.
                registerTxtLogin.setOnClickListener {
                    activity?.finish() // Encerro a activity para voltar para tela anterior.
                }

                // Adiciona um objeto de escuta de alteração de texto.
                registerEditEmail.addTextChangedListener(CustomTextWatcher {
                    // Limpar mensagem de erro.
                    displayEmailFailure(null)
                    // Ativa ou desativa o botão.
                    registerBtnNext.isEnabled = editEmailIsNotEmpty()
                })

                // Ao clicar no botão avançar.
                registerBtnNext.setOnClickListener {
                    val repository = DependencyInjector.registerEmailRepository()
                    presenter = RegisterEmailPresenter(this@RegisterEmailFragment, repository)
                    presenter.registerEmail(registerEditEmail.text.toString())
                }
            }
        }
    }

    private fun editEmailIsNotEmpty(): Boolean {
        return binding?.registerEditEmail?.text.toString().isNotEmpty()
    }

    override fun showProgress(enabled: Boolean) {
        binding?.registerBtnNext?.showProgress(enabled)
    }

    // Função definida pela interface RegisterEmail.View
    override fun displayEmailFailure(emailError: Int?) {
        // Exibe o argumento no campo de texto.
        binding?.registerEditEmailInput?.error = emailError?.let { getString(it) }
    }

    // Exibindo mensagem de erro.
    override fun onEmailFailure(message: String) {
        binding?.registerEditEmailInput?.error = message
    }

    // Abrindo o próximo fragmento.
    override fun goToNameAndPasswordScreen(email: String) {
        fragmentAttachListener?.goToNamePasswordScreen(email)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        // Se a atividade implementar minha interface FragmentAttachListener.
        if(context is FragmentAttachListener) {
            fragmentAttachListener = context // Capturo a referência da atividade.
        }
    }

    // Sobrescrevendo o onDestroy do fragmento.
    override fun onDestroy() {
        binding = null // Destuindo a referencia do layout.
        presenter.onDestroy() // Destruindo a referência da view passada para o presenter.
        fragmentAttachListener = null // Destruindo a referência da minha atividade.
        super.onDestroy()
    }
}