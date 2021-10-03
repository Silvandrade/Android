package com.silvandrade.chucknorrisio.datasource;


public class EndPoint {
    static final String BASE_URL = "https://api.chucknorris.io/";
    static final String GET_CATEGORY = BASE_URL + "jokes/categories";
    static final String GET_JOKE = BASE_URL + "jokes/random"; // ?category={category}
}
