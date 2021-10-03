package com.silvandrade.chucknorrisio.datasource;

import com.silvandrade.chucknorrisio.model.Joke;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


// Utilizado pelo Retrofit.
public interface ChuckNorrisAPI {
    String BASE_URL = "https://api.chucknorris.io/";

    @GET("jokes/categories")
    Call<List<String>> findAll();

    @GET("jokes/random")
    Call<Joke> findJokeBy(@Query("category") String category);
}
