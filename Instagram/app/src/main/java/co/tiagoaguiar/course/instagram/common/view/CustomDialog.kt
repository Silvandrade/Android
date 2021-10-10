package co.tiagoaguiar.course.instagram.common.view

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import co.tiagoaguiar.course.instagram.R

class CustomDialog(context: Context): Dialog(context) {

    private lateinit var dialogLinearLayout: LinearLayout
    private lateinit var txtButtons: Array<TextView>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_custom)

        dialogLinearLayout = findViewById(R.id.dialog_container)

    }

    fun addButton(listener: View.OnClickListener, vararg texts: Int) {
        // Definindo o tamanho do meu array txtButtons
        txtButtons = Array(texts.size) {
            TextView(context) // Preenchendo meu Array com intâncias de textview.
        }

        texts.forEachIndexed {index, txtValue->
            txtButtons[index].setText(txtValue) // Definindo o texto de cada textview.
            // Definindo o listener de cada textview.
            txtButtons[index].setOnClickListener {
                listener.onClick(it) // Passando a referência do próprio textview.
                dismiss() // Esconde a dialog.
            }
        }
    }

    override fun show() {
        super.show()

        for(textView in txtButtons) {
            dialogLinearLayout.addView(textView) // Adicionando os elementos do meu Array na minha Dialog.
        }
    }
}