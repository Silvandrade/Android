package co.tiagoaguiar.course.instagram.login.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ProgressBar
import co.tiagoaguiar.course.instagram.R

class LoadingButton : FrameLayout {

    private lateinit var button: Button
    private lateinit var progress: ProgressBar
    private lateinit var text: String

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        setup(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    private fun setup(context: Context, attrs: AttributeSet?) {
        // Inflando o layout xml dentro do LoadingButton/Frame Layout.
        val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.button_loading, this)

        // Lista de atributos criados no arquivo attrs.xml
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.LoadingButton, 0, 0)
        text = typedArray.getString(R.styleable.LoadingButton_text).toString() // Recuperando valor definido no atributo text criano no attrs.xml

        button = this.getChildAt(0) as Button // Pegando o botão criado no nosso button_loading.xml
        progress = this.getChildAt(1) as ProgressBar // Pegando a ProgressBar criada no nosso button_loading.xml

        button.text = text
        button.isEnabled = false

        typedArray.recycle() // Para diminuir a lista de array.
    }

    override fun setEnabled(enabled: Boolean) { // Sobrescrevendo o metodo para converter a propriedade do FrameLayout para a propriedade do botão.
        button.isEnabled = enabled
    }

    override fun setOnClickListener(l: OnClickListener?) { // Sobrescrevendo o metodo para converter o clique do FrameLayout no clique do botão.
        button.setOnClickListener(l) // Passando o listener para nosso botão.
    }

    fun showProgress(enabled: Boolean) {
        if(enabled) {
            button.text = ""
            button.isEnabled = false
            progress.visibility = View.VISIBLE
        } else {
            button.text = text
            button.isEnabled = true
            progress.visibility = View.GONE
        }
    }
}