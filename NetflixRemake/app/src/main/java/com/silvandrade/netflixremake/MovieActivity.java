package com.silvandrade.netflixremake;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.util.Log;
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
import com.silvandrade.netflixremake.model.MovieDetail;
import com.silvandrade.netflixremake.util.ImageDownloaderTask;
import com.silvandrade.netflixremake.util.MovieDetailTask;

import java.util.ArrayList;
import java.util.List;

public class MovieActivity extends AppCompatActivity implements MovieDetailTask.MovieDetailLoader {

    private TextView textTitle;
    private TextView textDesc;
    private TextView textCast;
    private RecyclerView recyclerView;
    private MovieAdapter movieAdapter;

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

//        textTitle.setText("Batman Begins");
//        textDesc.setText("Marcado pelo assassinato de seus pais quando ainda era criança, " +
//                "o milionário Bruce Wayne (Christian Bale) decide viajar pelo mundo em busca de " +
//                "encontrar meios que lhe permitam combater a injustiça e provocar medo em seus adversários. " +
//                "Após retornar a Gotham City, sua cidade-natal, ele idealiza seu alter-ego: Batman, um justiceiro " +
//                "mascarado que usa força, inteligência e um arsenal tecnológico para combater o crime.");
//        textCast.setText(getString(R.string.cast, "Christian Bale, Katie Holmes, Michael Caine")); // Passando variável para o recurso de string.

        List<Movie> movies = new ArrayList<>();

//        for(int i = 0; i < 30; i++) {
//            Movie movie = new Movie();
//            movies.add(movie);
//        }

        movieAdapter = new MovieAdapter(movies);
        recyclerView.setAdapter(movieAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));

//        int id = getIntent().getExtras().getInt("id"); // Recuperando o id passado pela MainActivity.
        Bundle extras = getIntent().getExtras();

        if(extras != null) {
            int id = extras.getInt("id"); // Recuperando o id passado pela MainActivity.
            MovieDetailTask movieDetailTask = new MovieDetailTask(this);
            movieDetailTask.setMovieDetailLoader(this);
            movieDetailTask.execute("https://tiagoaguiar.co/api/netflix/" + id);
        }

    }

    @Override
    public void onResult(MovieDetail movieDetail) {
        textTitle.setText(movieDetail.getMovie().getTitle());
        textDesc.setText(movieDetail.getMovie().getDesc());
        textCast.setText(movieDetail.getMovie().getCast());
        movieAdapter.setMovies(movieDetail.getMoviesSimilar());
        movieAdapter.notifyDataSetChanged();
    }

    private class MovieAdapter extends RecyclerView.Adapter<MovieHolder> {

        private List<Movie> movies;

        private MovieAdapter(List<Movie> movies) {
            this.movies = movies;
        }

        public void setMovies(List<Movie> movies) {
            this.movies.clear();
            this.movies.addAll(movies);
        }

        @NonNull
        @Override
        public MovieHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new MovieHolder(getLayoutInflater().inflate(R.layout.movie_item_similar, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull MovieActivity.MovieHolder holder, int position) {
            Movie movie = movies.get(position);
            new ImageDownloaderTask(holder.imageView).execute(movies.get(position).getCoverUrl());
        }

        @Override
        public int getItemCount() {
            return movies.size();
        }
    }

    private class MovieHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        public MovieHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_view_cover);
        }
    }

}