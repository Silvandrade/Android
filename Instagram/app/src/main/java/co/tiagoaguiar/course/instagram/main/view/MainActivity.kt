package co.tiagoaguiar.course.instagram.main.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowInsetsController
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import co.tiagoaguiar.course.instagram.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Alterando as cores da statusbar nessa activity
        window.statusBarColor = ContextCompat.getColor(this, R.color.gray)
        window.insetsController?.setSystemBarsAppearance(
            WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
            WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS)

        val toolbar = findViewById<Toolbar>(R.id.main_toolbar)
        setSupportActionBar(toolbar) // Informando ao sistema que essa activity possui uma toolbar e essa toolbar será responsável por escutar eventos de ação.

        // Atribuindo icon na toolbar.
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_insta_camera) // icon
        supportActionBar?.setDisplayHomeAsUpEnabled(true) // clicável
        supportActionBar?.title = ""
    }
}