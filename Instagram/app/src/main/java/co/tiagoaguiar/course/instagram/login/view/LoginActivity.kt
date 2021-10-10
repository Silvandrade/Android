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
import com.google.android.material.textfield.TextInputLayout

class LoginActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_login)

    val editTextEmail = findViewById<EditText>(R.id.login_edit_email)
    val editTextPassword = findViewById<EditText>(R.id.login_edit_password)
    val buttonEnter = findViewById<LoadingButton>(R.id.login_btn_enter)

    editTextEmail.addTextChangedListener(watcher) // Evento de escuta de alteração de texto.
    editTextPassword.addTextChangedListener(watcher)

    // Objeto anônimo que implementa uma interface, quando o botão é clicado é acionado o metodo Listener do LoadingButton que é um FrameLayout por extensão.
    buttonEnter.setOnClickListener{

      closeKeyboard(this)
      buttonEnter.showProgress(true);

      findViewById<TextInputLayout>(R.id.login_edit_email_input)
        .error = "Esse e-mail é inválido."

      findViewById<TextInputLayout>(R.id.login_edit_password_input)
        .error = "Senha inválida."

      Handler(Looper.getMainLooper()).postDelayed({ // Método para simular um atraso de 2 segundo.
        buttonEnter.showProgress(false)
      }, 2000)
    }
  }

  private val watcher = object : TextWatcher { // Objeto anônimo que implementa uma interface.
    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { // Toda vez que o texto for alterado.
      val btnEnter = findViewById<LoadingButton>(R.id.login_btn_enter)
      btnEnter.isEnabled = s.toString().isNotEmpty() // Retorna um boolean de edição de texto do campo e-mail.
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