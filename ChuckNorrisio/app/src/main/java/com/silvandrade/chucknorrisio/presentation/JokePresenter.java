package com.silvandrade.chucknorrisio.presentation;
import com.silvandrade.chucknorrisio.JokeActivity;
import com.silvandrade.chucknorrisio.datasource.JokeRemoteDataSource;
import com.silvandrade.chucknorrisio.model.Joke;


// Presenter especifica O QUE vai ser mostrando e a Activity especifica ONDE vai ser mostrado.
public class JokePresenter implements JokeRemoteDataSource.JokeCallback {

    private JokeActivity jokeActivity;
    private JokeRemoteDataSource dataSource;

    public JokePresenter(JokeActivity jokeActivity, JokeRemoteDataSource dataSource) {
        this.jokeActivity = jokeActivity;
        this.dataSource = dataSource;
    }

    public void findJokeBy(String category) {
        this.jokeActivity.showProgressDialog(); // Mostrar a barra de progresso.
        this.dataSource.findJokeBy(this, category); // Chama a consulta de dados e passa e a categoria.
    }

    @Override // Se comunica com os metodos de JokeActivity.
    public void onSuccess(Joke response) { // Em caso de sucesso.
        this.jokeActivity.showJoke(response); // Passa a resposta para Activity obtida do DataSource.
    }

    @Override // Se comunica com os metodos de JokeActivity.
    public void onError(String message) { // Em caso de erro.
        this.jokeActivity.showFailure(message); // Passa a mensagem de erro.
    }

    @Override // Se comunica com os metodos de JokeActivity.
    public void onComplete() { // Em qualquer caso.
        this.jokeActivity.hideProgressDialog(); // Fecha a barra de progresso.
    }
}
