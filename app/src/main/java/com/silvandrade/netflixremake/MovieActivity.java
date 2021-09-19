package com.silvandrade.netflixremake;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

public class MovieActivity extends AppCompatActivity {

    private TextView textTitle;
    private TextView textDesc;
    private TextView textCast;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        textTitle = findViewById(R.id.text_view_title);
        textDesc = findViewById(R.id.text_view_desc);
        textCast = findViewById(R.id.text_view_cast);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);
            getSupportActionBar().setTitle(null);
        }

        LayerDrawable layerDrawable = (LayerDrawable) ContextCompat.getDrawable(this, R.drawable.shadows); // Referenciando o arquivo shadows.xml

        if (layerDrawable != null) {
            Drawable movieCover = ContextCompat.getDrawable(this, R.drawable.movie); // Criando um drawable.
            layerDrawable.setDrawableByLayerId(R.id.cover_drawable, movieCover); // Alterando a referência da imagem dentro do arquivo shandow.xml
//            ((ImageView) findViewById(R.id.image_view_cover)).setImageDrawable(layerDrawable); // Acessando o ImageView de movie_item.xml e atualizando a imagem.
        }

        textTitle.setText("Batman Begins");
        textDesc.setText("Marcado pelo assassinato de seus pais quando ainda era criança, " +
                "o milionário Bruce Wayne (Christian Bale) decide viajar pelo mundo em busca de " +
                "encontrar meios que lhe permitam combater a injustiça e provocar medo em seus adversários. " +
                "Após retornar a Gotham City, sua cidade-natal, ele idealiza seu alter-ego: Batman, um justiceiro " +
                "mascarado que usa força, inteligência e um arsenal tecnológico para combater o crime.");
        textCast.setText(getString(R.string.cast, "Christian Bale, Katie Holmes, Michael Caine")); // Passando variável para o recurso de string.

    }
}