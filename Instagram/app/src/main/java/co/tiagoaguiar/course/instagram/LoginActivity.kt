package co.tiagoaguiar.course.instagram

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import com.google.android.material.textfield.TextInputLayout

class LoginActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_login)

    val editTextEmail = findViewById<EditText>(R.id.login_edit_email)
    val editTextPassword = findViewById<EditText>(R.id.login_edit_password)
    val buttonEnter = findViewById<Button>(R.id.login_btn_enter)

    editTextEmail.addTextChangedListener(watcher) // Evento de escuta de alteração de texto.
    editTextPassword.addTextChangedListener(watcher)

    // Objeto anônimo que implementa uma interface.
    buttonEnter.setOnClickListener{
      findViewById<TextInputLayout>(R.id.login_edit_email_input)
        .error = "Esse e-mail é inválido."

      findViewById<TextInputLayout>(R.id.login_edit_password_input)
        .error = "Senha inválida."
    }
  }

  private val watcher = object : TextWatcher { // Objeto anônimo que implementa uma interface.
    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { // Toda vez que o texto for alterado.
      val btnEnter = findViewById<Button>(R.id.login_btn_enter)
      btnEnter.isEnabled = s.toString().isNotEmpty() // Retorna um boolean de edição de texto do campo e-mail.
    }

    override fun afterTextChanged(s: Editable?) {
    }

  }
}