package com.silvandrade.netflixremake.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.silvandrade.netflixremake.model.Category;
import com.silvandrade.netflixremake.model.Movie;
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

public class CategoryTask extends AsyncTask<String, Void, List<Category>> { // Extendendo a class AsyncTask para fazer requisições assincronas.

    private final WeakReference<Context> context; // Declarando um context com uma referência fraca.
    private ProgressDialog dialog;
    private CategoryLoader categoryLoader;

    public CategoryTask(Context context) {
        this.context = new WeakReference<>(context);
    }

    public void setCategoryLoader(CategoryLoader categoryLoader) {
        this.categoryLoader = categoryLoader;
    }

    // Main Thread
    @Override
    protected void onPreExecute() { // Implementando progress bar.
        super.onPreExecute();
        Context context = this.context.get();

        if(context != null) {
            dialog = ProgressDialog.show(context, "Carregando...", "", true);
        }
    }

    // Vai ser executado paralelamente em outra thread background.
    @Override
    protected List<Category> doInBackground(String... urlParams) {
        String url = urlParams[0]; // Pegando o link da página do conteúdo.

        try {
            URL requestUrl = new URL(url);
//            URLConnection urlConnection = requestUrl.openConnection(); // Substituido pelo método HttlsURLConection.
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

            List<Category> categories = getCategories(new JSONObject(jasonAsString)); // Convertendo as Strings em Objeto Json para recuperar os objetos da minhas classes.

            inputStream.close(); // Fechando a conexão.

            return categories; // Vai delegar para mai thread e vai chegar onPosExecute.

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Recebe um objeto Json que contém uma String.
    private List<Category> getCategories(JSONObject jsonObject) throws JSONException {

        List<Category> categories = new ArrayList<>();
        JSONArray categoriesArrayJason = jsonObject.getJSONArray("category"); // Como o primeiro objeto do Json é um Array de categorias uso a chave do objeto para captura-lo.

        for(int i = 0; i < categoriesArrayJason.length(); i++) {
            final JSONObject categoryJson = categoriesArrayJason.getJSONObject(i); // Iterando por todos os objetos category do Json.
            String title = categoryJson.getString("title"); // Capturando o nome da categoria.

            List<Movie> movies = new ArrayList<>();
            JSONArray moviesArrayJason = categoryJson.getJSONArray("movie"); // Capturando o Array de Movie dentro da Category do arquivo Jason.

            // Capturando todos os objetos movie de cada categoria.
            for(int j = 0; j < moviesArrayJason.length(); j++) {
                final JSONObject movieJson = moviesArrayJason.getJSONObject(j); // Iterando por cada objeto movie.
                String coverUrl = movieJson.getString("cover_url"); // Capturando o atributo cover_url do movie dentro do arquivo Json.
                int id = movieJson.getInt("id"); // Capturando o id.

                Movie movie = new Movie(); // Instanciando um objeto da minha classe.
                movie.setCoverUrl(coverUrl); // Passando a url capturada do arquivo Json para o objeto movie instanciado.
                movie.setId(id); // Pasando o Id do JSON para o objeto filme.
                movies.add(movie); // Adicionando a filme a lista de filmes.
            }

            Category category = new Category(); // Instanciando o objeto da minha classe Category.
            category.setName(title); // Definindo o nome da categoria capturada no arquivo Json.
            category.setMovies(movies); // Passando para o objeto a sua lista de filmes.
            categories.add(category); // Adicionando a category a lista de categorias.

        }
        
        return categories;
    }

    // Main Thread
    @Override
    protected void onPostExecute(List<Category> categories) {
        super.onPostExecute(categories);
        dialog.dismiss();

        // Listener
        if(categoryLoader != null) {
            categoryLoader.onResult(categories);
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

    public interface CategoryLoader {
        void onResult(List<Category> categories);
    }
}
