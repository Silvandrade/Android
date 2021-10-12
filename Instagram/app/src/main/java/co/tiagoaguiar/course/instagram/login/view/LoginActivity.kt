package co.tiagoaguiar.course.instagram.login.view

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import co.tiagoaguiar.course.instagram.R
import co.tiagoaguiar.course.instagram.databinding.ActivityLoginBinding
import com.google.android.material.textfield.TextInputLayout

class LoginActivity : AppCompatActivity() {

  private lateinit var binding: ActivityLoginBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    binding = ActivityLoginBinding.inflate(layoutInflater)
    setContentView(binding.root)

    val buttonEnter = binding.loginBtnEnter

    with(binding) {
      loginEditEmail.addTextChangedListener(watcher) // Evento de escuta de alteração de texto.
      loginEditPassword.addTextChangedListener(watcher)

      // Objeto anônimo que implementa uma interface, quando o botão é clicado é acionado o metodo Listener do LoadingButton que é um FrameLayout por extensão.
      loginBtnEnter.setOnClickListener{
        closeKeyboard(LoginActivity())
        buttonEnter.showProgress(true);

        loginEditEmailInput.error = "Esse e-mail é inválido."
        loginEditPasswordInput.error = "Senha inválida."

        Handler(Looper.getMainLooper()).postDelayed({ // Método para simular um atraso de 2 segundo.
          loginBtnEnter.showProgress(false)
        }, 2000)
      }
    }
  }

  private val watcher = object : TextWatcher { // Objeto anônimo que implementa uma interface.
    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { // Toda vez que o texto for alterado.
      binding.loginBtnEnter.isEnabled = s.toString().isNotEmpty() // Retorna um boolean de edição de texto do campo e-mail.
    }

    override fun afterTextChanged(s: Editable?) {
    }

  }

  private fun closeKeyboard(activity: LoginActivity) {
    val inputMethodManager: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    val view: View? = activity.currentFocus
    inputMethodManager.hideSoftInputFromWindow(view?.windowToken, 0)
  }
}