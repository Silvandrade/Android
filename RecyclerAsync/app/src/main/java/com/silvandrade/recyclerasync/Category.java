package com.silvandrade.recyclerasync;

import android.content.ContentValues;
import java.util.ArrayList;
import java.util.List;

public class Category implements Model {
    String id, name;
    List<Film> films = new ArrayList<>();

    public Category(String id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public ContentValues toContentValues() {
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", this.name);
        return contentValues;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFilms(List<Film> films) {
        this.films = films;
    }
}
