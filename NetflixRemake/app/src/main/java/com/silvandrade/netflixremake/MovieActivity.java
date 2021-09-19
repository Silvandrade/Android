package com.silvandrade.netflixremake;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.silvandrade.netflixremake.model.Movie;

import java.util.ArrayList;
import java.util.List;

public class MovieActivity extends AppCompatActivity {

    private TextView textTitle;
    private TextView textDesc;
    private TextView textCast;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        textTitle = findViewById(R.id.text_view_title);
        textDesc = findViewById(R.id.text_view_desc);
        textCast = findViewById(R.id.text_view_cast);
        recyclerView = findViewById(R.id.recycler_view_similar);

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

        List<Movie> movies = new ArrayList<>();

        for(int i = 0; i < 30; i++) {
            Movie movie = new Movie();
            movies.add(movie);
        }

        recyclerView.setAdapter(new MovieAdapter(movies));
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));

    }

    private class MovieAdapter extends RecyclerView.Adapter<MovieHolder> {

        private List<Movie> movies;

        private MovieAdapter(List<Movie> movies) {
            this.movies = movies;
        }

        @NonNull
        @Override
        public MovieHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new MovieHolder(getLayoutInflater().inflate(R.layout.movie_item_similar, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull MovieActivity.MovieHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return movies.size();
        }
    }

    private class MovieHolder extends RecyclerView.ViewHolder {

        public MovieHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

}