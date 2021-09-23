package com.raywenderlich.timefighter

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
  // Armazenei o nome da minha classe em uma variável que sertá usada no Log.d para Debugger.
  private val TAG = MainActivity::class.java.simpleName
  // Criei variáveis latenit que serão definidas mais tarde e defini seus tipos como objetos view.
  private lateinit var gameScoreTextView: TextView
  private lateinit var timeLeftTextView: TextView
  private lateinit var tapMeButton: Button
  // Variaveis de pontuação, tempo restante (inicial) e controle de inicialização do jogo.
  private var score = 0
  private var timeLeft = 60
  private var gameStarted = false
  // Definindo variáveis de recursos de uma classe java de contagem decrescente de tempo.
  private lateinit var  countDownTimer: CountDownTimer
  private var initialCountDownTimer: Long = 60000
  private var countDownInterval: Long = 1000

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    // connect views to variables
    // 1 - Criando a referência dos elemento da UI com as variáveis do tipo de objetos view declaradas nas linhas 23,24 e 25
    gameScoreTextView = findViewById(R.id.game_score_text_view)
    timeLeftTextView = findViewById(R.id.time_left_text_view)
    tapMeButton = findViewById(R.id.tap_me_button)
    // 2 - Utilizando o método de esculta de clique da classe View para incrementar o score e executar a animação definida em app/res/anim/bounce.xml
    tapMeButton.setOnClickListener {
      // Rodando a animação
      view -> val bounceAnimation = AnimationUtils.loadAnimation(this, R.anim.bounce)
      view.startAnimation(bounceAnimation)
      // Chamando a função que incrementa o score.
      incrementScore()
    }
    // Chamando a função que reseta a pontuação e o cronômetro do jogo sempre que o aplicativo é inicizializado (onCreate cicle).
    resetGame()
    // Imprimindo no log com o nome da classe referenciado na linha 21 e a pontuação atual.
    Log.d(TAG, "onCreate called. Score is: $score")
    // Verificando se há estado da UI (conteúdo das Views) salvo pelo método onSaveInstanceState() antes do onDestroy.
    if(savedInstanceState != null) {
      // Recuperando os estados.
      score = savedInstanceState.getInt(SCORE_KEY)
      timeLeft = savedInstanceState.getInt(TIME_LEFT_KEY)
      // Restaurando os valores nas Views.
      restoreGame()
    }
  }
  // Definindo a função que Restaura o estado anterior das Views salvo antes da destruição da Activity.
  private fun restoreGame() {
    val restoreScore = getString(R.string.your_score, score)
    gameScoreTextView.text = restoreScore

    val restoreTimeLeft = getString(R.string.time_left, timeLeft)
    timeLeftTextView.text = restoreTimeLeft
    // Recriando o objeto e resumindo o tempo interrompido na linha 98 quando do momento de salvar o estado das Views antes de destruir a Activity
    countDownTimer = object : CountDownTimer((timeLeft * 1000).toLong(), countDownInterval) {
      override fun onTick(millisUntilFinished: Long) {
        timeLeft = millisUntilFinished.toInt() / 1000
        val timeLeftString = getString(R.string.time_left, timeLeft)
        timeLeftTextView.text = timeLeftString
      }

      override fun onFinish() {
        endGame()
      }
    }
    startGame()
  }
  // 1 - Definindo o objeto companheiro com atributos de constantes que servirá para recuperar os dados.
  companion object {
    private const val SCORE_KEY = "SCORE_KEY"
    private const val TIME_LEFT_KEY = "TIME_LEFT_KEY"
  }
  // 2 - Metodo chamado antes de destruir a Activity e usado para salvar o estado das Views (conteúdo).
  override fun onSaveInstanceState(outState: Bundle) {
    super.onSaveInstanceState(outState)
    // Salvando os dados das Views em recurso de Map(key, values) fornecido pelo objeto Bunble passado pela Activity para função.
    outState.putInt(SCORE_KEY, score)
    outState.putInt(TIME_LEFT_KEY, timeLeft)
    // Interrompendo a contagem
    countDownTimer.cancel()
    // Imprimindo log como recurso de Debbuger para testar o funcionamento da função.
    Log.d(TAG, "onSaveInstanceState: Score: $score & Time: $timeLeft")
  }
  // Sobrescrevendo o onDestroy para imprimir o log para testar o Debbuger
  override fun onDestroy() {
    super.onDestroy()
    Log.d(TAG, "onDestroy called.")
  }
  // Criando o Menu através do recurso app/res/menu/menu.xml
  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    super.onCreateOptionsMenu(menu)
    menuInflater.inflate(R.menu.menu, menu)
    return true
  }
  // Verificando o item selecionado no menu e exibindo os dados.
  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    super.onOptionsItemSelected(item)
    if(item.itemId == R.id.about_item) {
      // Chamando a função que exibe os dados.
      showInfo()
    }
    return true
  }
  // Criando e exibindo uma caixa de diálogo com os dados definidos no recurso de string app/res/values/string.xml
  @SuppressLint("StringFormatMatches")
  private fun showInfo(){
    val dialogTitle = getString(R.string.about_title, BuildConfig.VERSION_CODE)
    val dialogMessage = getString(R.string.about_message)
    val builder = AlertDialog.Builder(this)
    builder.setTitle(dialogTitle)
    builder.setMessage(dialogMessage)
    builder.create().show()
  }
  // Função de incremento da pontuação chamado pela esculta de clique do View Button
  private fun incrementScore() {
    // increment score logic
    if(!gameStarted) {
      startGame()
    }
    score++
    val newScore = getString(R.string.your_score, score)
    gameScoreTextView.text = newScore
  }
  // Reiniciando o jogo após inicia a aplicação ou finalizar o tempo.
  private fun resetGame() {
    // reset game logic
    score = 0
    val initialScore = getString(R.string.your_score, score)
    gameScoreTextView.text = initialScore

    val initialTimeLeft = getString(R.string.time_left, 60)
    timeLeftTextView.text = initialTimeLeft
    // Criando o objeto cronometro da classe java que decrementa o valor até 0 a partir de um valor inicial e de um valor de decremento (step).
    countDownTimer = object : CountDownTimer(initialCountDownTimer, countDownInterval) {
      override fun onTick(millisUntilFinished: Long) {
        timeLeft = millisUntilFinished.toInt() / 1000
        val timeLeftString = getString(R.string.time_left, timeLeft)
        timeLeftTextView.text = timeLeftString
      }

      override fun onFinish() {
        endGame()
      }
    }
    gameStarted = false
  }
  // Iniciando o cronômetro
  private fun startGame() {
    // start game logic
    countDownTimer.start()
    gameStarted = true
  }
  // Finalizando o jogo
  private fun endGame() {
    // end game logic
    // Exibindo mensagem com a potuação final.
    Toast.makeText(this, getString(R.string.game_over_message, score), Toast.LENGTH_LONG).show()
    resetGame()
  }
}