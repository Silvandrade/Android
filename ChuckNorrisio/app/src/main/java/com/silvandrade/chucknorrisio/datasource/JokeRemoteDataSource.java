package com.silvandrade.chucknorrisio.datasource;

import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.JsonToken;
import com.silvandrade.chucknorrisio.model.Joke;
import com.silvandrade.chucknorrisio.presentation.JokePresenter;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JokeRemoteDataSource {

    public interface JokeCallback {
        void onSuccess(Joke response);
        void onError(String message);
        void onComplete();
    }

    public void findJokeBy(JokePresenter jokeCallBack, String category) {

        HttpClient.retrofit().create(ChuckNorrisAPI.class)
                .findJokeBy(category)
                .enqueue(new Callback<Joke>() {
                    @Override
                    public void onResponse(Call<Joke> call, Response<Joke> response) {
                        if(response.isSuccessful()) {
                            jokeCallBack.onSuccess(response.body());
                            jokeCallBack.onComplete();
                        }
                    }

                    @Override
                    public void onFailure(Call<Joke> call, Throwable t) {
                        jokeCallBack.onComplete();
                    }
                });

//        new JokeTask(jokeCallBack, category).execute(); Metodo substituido pelo retrofit.
    }

//    Substituido pelo Retrofit.
    private static class JokeTask extends AsyncTask<Void, Void, Joke> {

        private final JokePresenter jokeCallBack;
        private final String category;
        private String errorMessage;

        public JokeTask(JokePresenter jokeCallBack, String category) {
            this.jokeCallBack = jokeCallBack;
            this.category = category;
        }

        @Override
        protected Joke doInBackground(Void... voids) {
            String iconUrl = null;
            String jokeText = null;
            Joke jokeResponse = null;

            HttpURLConnection urlConnection;

            try {
                String endPoint = String.format("%s?category=%s", EndPoint.GET_JOKE, category);
                URL url = new URL(endPoint);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setConnectTimeout(2000);
                urlConnection.setReadTimeout(2000);

                final int responseCode = urlConnection.getResponseCode();

                if(responseCode > 400) {
                    throw new IOException("Falha de conexão com o servidor.");
                }

                InputStream in = new BufferedInputStream(urlConnection.getInputStream());

                JsonReader jsonReader = new JsonReader(new InputStreamReader(in));

                // Transformar o JSON em Joke.
                jsonReader.beginObject();

                while(jsonReader.hasNext()) {
                    JsonToken token = jsonReader.peek(); // Capturar um sequência de caracteres.

                    if(token == JsonToken.NAME) {
                        String name = jsonReader.nextName();

                        if(name.equals(category)) {
                            jsonReader.skipValue(); // Pular esse valor.
                        } else if(name.equals("icon_url")) {
                            iconUrl = jsonReader.nextString(); // Pegando o valor.
                        } else if(name.equals("value")) {
                            jokeText = jsonReader.nextString();
                        } else {
                            jsonReader.skipValue(); // Qualquer outro valor pular.
                        }
                    }
                }

                jokeResponse = new Joke(iconUrl, jokeText);
                jsonReader.endObject();

            } catch (MalformedURLException e) {
                errorMessage = e.getMessage();
            } catch (IOException e) {
                errorMessage = e.getMessage();
            }

            return jokeResponse;
        }

        @Override // Se comunica com os metodos de JokePresenter.
        protected void onPostExecute(Joke jokeResponse) {
            if(errorMessage != null) {
                jokeCallBack.onError(errorMessage);
            } else {
                jokeCallBack.onSuccess(jokeResponse);
            }
            jokeCallBack.onComplete();
        }
    }
}
