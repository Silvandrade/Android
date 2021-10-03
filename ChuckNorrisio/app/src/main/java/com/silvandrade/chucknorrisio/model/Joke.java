package com.silvandrade.chucknorrisio.model;

import com.google.gson.annotations.SerializedName;

public class Joke {

    @SerializedName("icon_url")
    private final String iconUrl;

    @SerializedName("value")
    private final String jokeText;

    public Joke(String iconUrl, String jokeText) {
        this.iconUrl = iconUrl;
        this.jokeText = jokeText;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public String getJokeText() {
        return jokeText;
    }
}
