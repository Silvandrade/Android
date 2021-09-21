package com.silvandrade.netflixremake;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import com.silvandrade.netflixremake.model.Category;
import com.silvandrade.netflixremake.model.Movie;
import com.silvandrade.netflixremake.util.CategoryTask;
import com.silvandrade.netflixremake.util.ImageDownloaderTask;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements CategoryTask.CategoryLoader {

    private CategoryAdapter categoryAdapter;
    private MovieAdapter movieAdapter;
    private CategoryTask categoryTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recycler_view_main);

        List<Category> categories = new ArrayList<>();
        List<Movie> movies = new ArrayList<>();

//        for (int i = 0; i < 5; i++) {
//            Category category = new Category();
//            category.setName("Categoria " + i);
//
//            for (int j = 0; j < 30; j++) {
//                Movie movie = new Movie();
////                movie.setCoverUrl(R.drawable.movie);
//                movies.add(movie);
//            }
//
//            category.setMovies(movies);
//            categories.add(category);
//        }

        categoryAdapter = new CategoryAdapter(categories);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        recyclerView.setAdapter(categoryAdapter);

        categoryTask = new CategoryTask(this);
        categoryTask.setCategoryLoader(this); // Passando a classe que implementa a interface.
        categoryTask.execute("https://tiagoaguiar.co/api/netflix/home");
    }

    @Override
    public void onResult(List<Category> categories) {
        categoryAdapter.setCategories(categories);
        categoryAdapter.notifyDataSetChanged(); // Notificando a chegada dos dados.
    }

    private class CategoryAdapter extends RecyclerView.Adapter<CategoryHolder> {

        private List<Category> categories;

        private CategoryAdapter(List<Category> categories) {
            this.categories = categories;
        }

        @NonNull
        @Override
        public CategoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new CategoryHolder(getLayoutInflater().inflate(R.layout.category_item, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull MainActivity.CategoryHolder holder, int position) {
            Category category;
            category = categories.get(position);

            movieAdapter = new MovieAdapter(category.getMovies());
            holder.recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext(), RecyclerView.HORIZONTAL, false));
            holder.recyclerView.setAdapter(movieAdapter);
            holder.textView.setText(category.getName());
        }

        @Override
        public int getItemCount() {
            return categories.size();
        }

        void setCategories(List<Category> categories) {
            this.categories.clear();
            this.categories.addAll(categories);
        }
    }

    private class CategoryHolder extends RecyclerView.ViewHolder {

        TextView textView;
        RecyclerView recyclerView;

        public CategoryHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.text_view_title);
            recyclerView = itemView.findViewById(R.id.recycler_view_movie);
        }
    }

    private class MovieAdapter extends RecyclerView.Adapter<MovieHolder> implements OnItemClickListener {

        private final List<Movie> movies;

        private MovieAdapter (List<Movie> movies) {
            this.movies = movies;
        }

        @NonNull
        @Override
        public MovieHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.movie_item, parent, false);
            return new MovieHolder(view, this);
        }

        @Override
        public void onBindViewHolder(@NonNull MainActivity.MovieHolder holder, int position) {
            Movie movie = movies.get(position);
            new ImageDownloaderTask(holder.imageView).execute(movie.getCoverUrl());
//            holder.imageView.setImageResource(movie.getCoverUrl());
        }

        @Override
        public int getItemCount() {
            return movies.size();
        }

        @Override
        public void onClick(int position) {
            Intent intent = new Intent(MainActivity.this, MovieActivity.class); // Definindo intenção de abrir outra Activity.
            intent.putExtra("id", movies.get(position).getId()); // Passando o Id para MovieActivity de acordo com a posição retornada pela interface de Listener.
            startActivity(intent);
        }
    }

    private class MovieHolder extends RecyclerView.ViewHolder {

        ImageView imageView;

        public MovieHolder(@NonNull View itemView, OnItemClickListener onItemClickListener) { // Construtor recebe a classe que implementa onItemClickListener.
            super(itemView);
            imageView = itemView.findViewById(R.id.image_view_cover);

            itemView.setOnClickListener(v -> {
                onItemClickListener.onClick(getAdapterPosition()); // Passando para minha interface a posição do item clicado.
            });
        }
    }

    interface OnItemClickListener {
        void onClick(int position);
    }

}