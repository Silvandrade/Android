package com.silvandrade.netflixremake.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import com.silvandrade.netflixremake.model.Movie;
import com.silvandrade.netflixremake.model.MovieDetail;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.net.ssl.HttpsURLConnection;

public class MovieDetailTask extends AsyncTask<String, Void, MovieDetail> {

    private final WeakReference<Context> context;
    private ProgressDialog dialog;
    private MovieDetailLoader movieDetailLoader;

    // Recebendo o contexto.
    public MovieDetailTask(Context context) {
        this.context = new WeakReference<>(context); // Passando o contexto no atributo da classe.
    }

    public void setMovieDetailLoader(MovieDetailLoader movieDetailLoader) {
        this.movieDetailLoader = movieDetailLoader;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        // Pegando o contexto referenciado no atributo da classe.
        Context context = this.context.get();

        if(context != null) {
            dialog = ProgressDialog.show(context, "Carregando...", "",true);
        }
    }

    @Override
    protected MovieDetail doInBackground(String... params) {
        String url = params[0]; // Pegando o link da página do conteúdo.

        try {
            URL requestUrl = new URL(url);
//            URLConnection urlConnection = requestUrl.openConnection();
            HttpsURLConnection urlConnection = (HttpsURLConnection) requestUrl.openConnection(); // Abrindo a conexão.
            urlConnection.setReadTimeout(2000); // Tempo de espera de leitura.
            urlConnection.setConnectTimeout(2000); // Tempo para mostrar mensagem de erro.

            int responseCode = urlConnection.getResponseCode(); // Pegando o status code da conexão.

            if(responseCode > 400) {
                throw new IOException("Erro na conexão com o servidor.");
            }

            InputStream inputStream = urlConnection.getInputStream(); // Capturando a sequência de dados bytes do arquivo Json.

            BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);

            String jasonAsString = toString(bufferedInputStream); // Convertendo os Bytes para String.
            Log.i("Teste", jasonAsString); // Escrevendo o log o arquivo Json.

            MovieDetail movieDetail = getMovieDetail(new JSONObject(jasonAsString)); // Convertendo as Strings em Objeto Json para recuperar os objetos da minhas classes.

            inputStream.close(); // Fechando a conexão.

            return movieDetail; // Vai delegar para main thread e vai chegar onPosExecute.

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private MovieDetail getMovieDetail(JSONObject jsonObject) throws  JSONException {

        int id = jsonObject.getInt("id");
        String title = jsonObject.getString("title");
        String desc = jsonObject.getString("desc");
        String cast = jsonObject.getString("cast");
        String coverUrl = jsonObject.getString("cover_url");

        List<Movie> movies = new ArrayList<>();
        JSONArray movieArray = jsonObject.getJSONArray("movie");

        for(int i = 0; i < movieArray.length(); i++) {
            JSONObject movie = movieArray.getJSONObject(i); // Iterando por cada filme.
            String cover_url = movie.getString("cover_url"); // Capa de cada filme similar.
            int idSimilar = movie.getInt("id");

            Movie movieSimilar = new Movie();
            movieSimilar.setId(idSimilar); // Atribuindo o id.
            movieSimilar.setCoverUrl(cover_url); // Atribuindo a capa.
            movies.add(movieSimilar); // Adicionando a minha coleção de filmes.
        }

        Movie movie = new Movie();
        movie.setId(id);
        movie.setCoverUrl(coverUrl);
        movie.setTitle(title);
        movie.setDesc(desc);
        movie.setCast(cast);

        return new MovieDetail(movie, movies);
    }

    @Override
    protected void onPostExecute(MovieDetail movieDetail) {
        super.onPostExecute(movieDetail);
        dialog.dismiss();

        // Listener
        if (movieDetailLoader != null) {
            movieDetailLoader.onResult(movieDetail);
        }
    }

    // Transformando os bytes em caracteres no formato de String.
    private String toString(InputStream inputStream) throws  IOException { // Convertendo byte para string.
        byte[] bytes = new byte[1024];
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        int read;
        // Ler uma sequência de bytes e a cada 1024 armazena no objeto byteArrayOutputStream.
        while ((read = inputStream.read(bytes)) > 0) { // Pegando todos os bytes lidos.
            byteArrayOutputStream.write(bytes, 0, read); // Toda vez que ele consegue ler os bytes é escrito na saída.
        }

        return new String(byteArrayOutputStream.toByteArray());
    }

    public interface MovieDetailLoader {
        void onResult(MovieDetail movieDetail);
    }
}
