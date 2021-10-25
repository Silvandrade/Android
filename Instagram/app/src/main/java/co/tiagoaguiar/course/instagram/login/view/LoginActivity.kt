package co.tiagoaguiar.course.instagram.login.view

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import co.tiagoaguiar.course.instagram.common.base.DependencyInjector
import co.tiagoaguiar.course.instagram.common.util.CustomTextWatcher
import co.tiagoaguiar.course.instagram.databinding.ActivityLoginBinding
import co.tiagoaguiar.course.instagram.login.Login
import co.tiagoaguiar.course.instagram.login.presenter.LoginPresenter
import co.tiagoaguiar.course.instagram.main.view.MainActivity
import co.tiagoaguiar.course.instagram.register.view.RegisterActivity

class LoginActivity : AppCompatActivity(), Login.View {

  private lateinit var binding: ActivityLoginBinding
  override lateinit var presenter: Login.Presenter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    binding = ActivityLoginBinding.inflate(layoutInflater)
    setContentView(binding.root)

    presenter = LoginPresenter(this, DependencyInjector.loginRepository())

    with(binding) {
      // Evento de escuta de alteração de texto.
      loginEditEmail.addTextChangedListener(watcher)
      loginEditEmail.addTextChangedListener(CustomTextWatcher {
        displayEmailFailure(null)
      })

      loginEditPassword.addTextChangedListener(watcher)
      loginEditPassword.addTextChangedListener(CustomTextWatcher {
        displayPasswordFailure(null)
      })

      loginBtnEnter.setOnClickListener{
        closeKeyboard(this@LoginActivity)
        presenter.login(loginEditEmail.text.toString(), loginEditPassword.text.toString())
      }

      loginTxtRegister.setOnClickListener{
        goToRegisterScreen()
      }
    }
  }

  private val watcher = CustomTextWatcher {
    binding.loginBtnEnter.isEnabled = fieldsIsFilled()
  }

  override fun showProgress(enabled: Boolean) {
    binding.loginBtnEnter.showProgress(enabled)
  }

  override fun displayEmailFailure(emailError: Int?) {
    binding.loginEditEmailInput.error = emailError?.let { getString(it) }
  }

  override fun displayPasswordFailure(passwordError: Int?) {
    binding.loginEditPasswordInput.error = passwordError?.let { getString(it) }
  }

  override fun onUserAuthenticated() {
    val intent = Intent(this, MainActivity::class.java)
    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
    startActivity(intent)
  }

  override fun onUserUnauthorized(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
  }

  override fun onDestroy() {
    presenter.onDestroy()
    super.onDestroy()
  }

  private fun closeKeyboard(activity: Activity) {
    val inputMethodManager: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    val view: View? = activity.currentFocus
    inputMethodManager.hideSoftInputFromWindow(view?.windowToken, 0)
  }

  private fun fieldsIsFilled() : Boolean {
    return binding.loginEditEmail.text.toString().isNotEmpty()
            && binding.loginEditPassword.text.toString().isNotEmpty()
  }

  private fun goToRegisterScreen() {
    startActivity(Intent(this, RegisterActivity::class.java))
  }
}