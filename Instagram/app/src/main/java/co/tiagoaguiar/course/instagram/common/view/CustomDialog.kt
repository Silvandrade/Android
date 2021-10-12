package co.tiagoaguiar.course.instagram.common.view

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import co.tiagoaguiar.course.instagram.R
import co.tiagoaguiar.course.instagram.databinding.DialogCustomBinding

class CustomDialog(context: Context): Dialog(context) {

    private lateinit var binding: DialogCustomBinding
    private lateinit var dialogLinearLayout: LinearLayout
    private lateinit var txtButtons: Array<TextView>

    private lateinit var dialogTitle: TextView
    private var titleId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DialogCustomBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dialogLinearLayout = binding.dialogContainer // Referênciando meu ViewGroup do arquivo dialog_custom.xml
        dialogTitle = binding.dialogTitle
    }

    override fun setTitle(titleId: Int) { // Recebe um arquivo de recurso e armazena o valor em uma variável
        this.titleId = titleId
    }

    fun addButton(vararg texts: Int, listener: View.OnClickListener) {
        // Definindo o tamanho do meu array txtButtons
        txtButtons = Array(texts.size) {
            TextView(context) // Preenchendo meu Array com intâncias de textview.
        }

        texts.forEachIndexed {index, txtId->
            with(txtButtons[index]) {
                id = txtId // Definindo o ID de cada textview.
                setText(txtId) // Definindo o texto de cada textview.
                // Definindo o listener de cada textview.
                setOnClickListener {
                    listener.onClick(it) // Passando a referência do próprio textview como argumento.
                    dismiss() // Esconde a dialog.
                }
            }
        }
    }

    override fun show() { // Sobrescrevendo o metodo show da classe Dialog.
        super.show()

        titleId?.let { dialogTitle.setText(it) } // Se a variável tiver recebido um valor altera o textview usado como título na custom dialog.

        for(textView in txtButtons) {
            // Define os parametros de formatação dos textviews que serão adicionados.
            val layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            layoutParams.setMargins(30, 50, 30, 50)

            dialogLinearLayout.addView(textView, layoutParams) // Adicionando os elementos do meu Array na minha Dialog.
        }
    }
}