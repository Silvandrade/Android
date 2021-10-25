package co.tiagoaguiar.course.instagram.register.view

// Criada para se comunicar com a atividade a partir do fragmento.
interface FragmentAttachListener {
    fun goToNamePasswordScreen(email: String)
    fun goToWelcomeScreen(name: String)
}