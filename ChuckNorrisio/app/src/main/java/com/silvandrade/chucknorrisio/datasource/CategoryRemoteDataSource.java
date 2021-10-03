package com.silvandrade.chucknorrisio.datasource;

import android.os.AsyncTask;
import android.util.JsonReader;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.net.ssl.HttpsURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryRemoteDataSource {

    public interface ListCategoriesCallback { // Definindo interface para manipular resposta.
        void onSuccess(List<String> response);
        void onError(String message);
        void onComplete();
    }

    public void findAll(ListCategoriesCallback callback) {

        HttpClient.retrofit().create(ChuckNorrisAPI.class)
                .findAll()
                .enqueue(new Callback<List<String>>() { // Para fazer de forma assincrona.
                    @Override
                    public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                        if(response.isSuccessful()) {
                            callback.onSuccess(response.body()); // Passando minha lista de categorias.
                            callback.onComplete();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<String>> call, Throwable t) {
                        callback.onError(t.getMessage());
                        callback.onComplete();
                    }
                });

//        new CategoryTask(callback).execute(); Metodo substituido pelo retrofit.
    }

//    Substituido pelo Retrofit.
    private static class CategoryTask extends AsyncTask<Void, Void, List<String>> {

        private final ListCategoriesCallback callback;
        private String errorMessage; // Caso ocorra erro na requisição.

        private CategoryTask(ListCategoriesCallback callback) {
            this.callback = callback;
        }

        @Override
        protected List<String> doInBackground(Void... params) {
            List<String> response = new ArrayList<>();
            HttpsURLConnection urlConnection; // Definindo objeto Https porque é o protocolo usado pela API.

            try {
                URL url = new URL(EndPoint.GET_CATEGORY); // Objeto de conexão do Java.
                urlConnection = (HttpsURLConnection) url.openConnection();// Abrindo conexão com API Https.
                urlConnection.setConnectTimeout(2000); // Tempo de espera de 2 segundos para a página.
                urlConnection.setReadTimeout(2000); // Tempo de espera de 2 segundos para o Json.

                final int responseCode = urlConnection.getResponseCode(); // Capturando a resposta da requisição web.

                if (responseCode > 400) {
                    throw new IOException("Falha de comunicação com o servidor.");
                }

                InputStream in = new BufferedInputStream(urlConnection.getInputStream()); // Capturando os dados da Url.

                JsonReader jsonReader = new JsonReader(new InputStreamReader(in)); // Converter de InputStream para Json.

                jsonReader.beginArray(); // Indo para o início dos dados.

                while (jsonReader.hasNext()) { // Enquanto não chegar ao fim dos dados.
                    response.add(jsonReader.nextString()); // Passando os dados para meu Array.
                }

                jsonReader.endArray(); // Fechar o meu Array.

            } catch (MalformedURLException e) {
                errorMessage = e.getMessage(); // Capturando mensagem de erro.
            } catch (IOException e) {
                errorMessage = e.getMessage(); // Capturando mensagem de erro.
            }

            return response;
        }

        @Override
        protected void onPostExecute(List<String> response) {
            if (errorMessage != null) {
                callback.onError(errorMessage); // Se der erro na requisição.
            } else {
                callback.onSuccess(response); // Se tudo ocorrer bem.
            }
            callback.onComplete(); // Independente do resultado.
        }
    }
}