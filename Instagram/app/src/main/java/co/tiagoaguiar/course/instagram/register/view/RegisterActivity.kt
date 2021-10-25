package co.tiagoaguiar.course.instagram.register.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import co.tiagoaguiar.course.instagram.R
import co.tiagoaguiar.course.instagram.databinding.ActivityRegisterBinding
import co.tiagoaguiar.course.instagram.register.view.RegisterNameAndPasswordFragment.Companion.KEY_EMAIL
import co.tiagoaguiar.course.instagram.register.view.RegisterWelcomeFragment.Companion.KEY_NAME

class RegisterActivity : AppCompatActivity(), FragmentAttachListener {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Pegando a referência da minha atividade
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root) // Definindo o conteudo de visualização

        // Definindo o fragmento que será exibido na atividade.
        val fragment = RegisterEmailFragment()
        changeFragment(fragment)

    }

    override fun goToNamePasswordScreen(email: String) {
        /*
        val args = Bundle()
        args.putString(KEY_EMAIL, email) // Definindo a estrutura dos dados.

        val fragment = RegisterNamePasswordFragment()
        fragment.arguments = args // Passando dados para o fragemento.
        */

        val fragment = RegisterNameAndPasswordFragment().apply {
            arguments = Bundle().apply {
                putString(KEY_EMAIL, email)
            }
        }

        changeFragment(fragment)
    }

    override fun goToWelcomeScreen(name: String) {
        val fragment = RegisterWelcomeFragment().apply {
            arguments = Bundle().apply {
                putString(KEY_NAME, name)
            }
        }

        changeFragment(fragment)
    }

    // Alterando o fragmento.
    private fun changeFragment(fragment: Fragment) {
        if(supportFragmentManager.findFragmentById(R.id.register_fragment) == null) {
            supportFragmentManager.beginTransaction().apply {
                add(R.id.register_fragment, fragment) // Adicionando o primeiro fragmento.
                commit()
            }
        } else {
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.register_fragment, fragment) // Trocando o fragmento.
                addToBackStack(null) // Adicionando pilha de retorno.
                commit()
            }
        }
    }
}