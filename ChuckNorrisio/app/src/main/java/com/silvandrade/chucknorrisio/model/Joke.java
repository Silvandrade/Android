package com.silvandrade.chucknorrisio.model;

public class Joke {
    private final String iconUrl;
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
