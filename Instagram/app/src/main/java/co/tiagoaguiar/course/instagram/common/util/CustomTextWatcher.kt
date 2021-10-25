package co.tiagoaguiar.course.instagram.common.util

import android.text.Editable
import android.text.TextWatcher

class CustomTextWatcher(val onTextChanged: (String) -> Unit): TextWatcher {
    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    // Executa sempre que o texto é modificado.
    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        onTextChanged(s.toString())
    }

    override fun afterTextChanged(s: Editable?) {
    }
}