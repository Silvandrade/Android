package co.tiagoaguiar.course.instagram.register.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import co.tiagoaguiar.course.instagram.R
import co.tiagoaguiar.course.instagram.common.view.CustomDialog
import co.tiagoaguiar.course.instagram.databinding.FragmentRegisterPhotoBinding

class RegisterPhotoFragment: Fragment(R.layout.fragment_register_photo) {

    private var binding: FragmentRegisterPhotoBinding? = null

//    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
//        return inflater.inflate(R.layout.fragment_register_photo, container, false)
//    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) { // Depois que o fragment tiver sido criado.
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentRegisterPhotoBinding.bind(view) // Inflando o fragmento da view.

        val customDialog = CustomDialog(requireContext()) // Instancia uma CustomDialog passando o contexto do Fragment.

        customDialog.setTitle(R.string.app_name)
        customDialog.addButton(R.string.photo, R.string.gallery) {
            // Passando o listener como argumento do método addButtom.
            when(it.id) {
                R.string.photo -> {
                    Log.i("Teste", (it as TextView).text.toString())
                }
                R.string.gallery -> {
                    Log.i("Teste", (it as TextView).text.toString())
                }
            }
        }
//        customDialog.addButton({
//            Log.i("Teste", (it as TextView).text.toString())
//        }, R.string.photo, R.string.gallery)

        customDialog.show()
    }

    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }
}