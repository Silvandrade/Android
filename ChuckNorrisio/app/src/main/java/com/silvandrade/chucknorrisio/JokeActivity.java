package com.silvandrade.chucknorrisio;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.silvandrade.chucknorrisio.databinding.ActivityJokeBinding;
import com.silvandrade.chucknorrisio.datasource.JokeRemoteDataSource;
import com.silvandrade.chucknorrisio.model.Joke;
import com.silvandrade.chucknorrisio.presentation.JokePresenter;
import com.squareup.picasso.Picasso;

public class JokeActivity extends AppCompatActivity {

    private ProgressDialog progress;
    private ActivityJokeBinding binding;
    public static final String CATEGORY_KEY = "category_key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityJokeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);

        if(getIntent().getExtras() != null) { // Evitando NullPointerException.
            final String category = getIntent().getExtras().getString(CATEGORY_KEY); // Capturando a Intent passada pela MainActivity.

            if(getSupportActionBar() != null) { // Evitando NullPointerException.
                getSupportActionBar().setTitle(category); // Alternado o título da ActionBar.
                getSupportActionBar().setDisplayHomeAsUpEnabled(true); // Definindo o botão de voltar.
            }

            JokeRemoteDataSource dataSource = new JokeRemoteDataSource();
            final JokePresenter presenter = new JokePresenter(this, dataSource); // O presenter precisa de uma referência a Activity e ao Objeto de requisições.

            presenter.findJokeBy(category); // Quando entrar na tela.

            binding.fab.setOnClickListener(view -> {
                presenter.findJokeBy(category); // Quando eu clicar em atualizer piada.
            });
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) { // Pegar eventos de clique dos elementos da ActionBar.
        switch (item.getItemId()) { // Pegando o Id do objeto clicado na ActionBar.
            case android.R.id.home: // Se for o botão voltar.
                this.finish(); // Feche a Activity.
                break;
            default:
        }
        return true;
    }

    public void showProgressDialog() {
        if(progress == null){
            progress = new ProgressDialog(this);
            progress.setMessage(this.getString(R.string.loading));
            progress.setIndeterminate(true); // Tempo de exibição indeterminado.
            progress.setCancelable(false); // Para que ninguém cancele.
            progress.show();
        }
    }

    public void hideProgressDialog() {
        if(progress != null) {
            progress.hide();
            progress = null;
        }
    }

    public void showJoke(Joke joke) {
        final TextView jokeText = findViewById(R.id.txt_joke);
        jokeText.setText(joke.getJokeText());

        ImageView imgJoke = findViewById(R.id.img_icon);

        Picasso.get().load(joke.getIconUrl()).into(imgJoke); // Utilizando a biblioteca Picasso.
    }

    public void showFailure(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

}