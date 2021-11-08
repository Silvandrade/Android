package com.silvandrade.recyclerasync;

import android.content.ContentValues;

public class Film implements Model {

    private String id, title, content, id_category;

    public Film(String id, String title, String content, String id_category) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.id_category = id_category;
    }

    public ContentValues toContentValues() {
        ContentValues contentValues = new ContentValues();
            contentValues.put("title", this.title);
            contentValues.put("content", this.content);
            contentValues.put("id_category", this.id_category);
        return contentValues;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId_category() {
        return id_category;
    }

    public void setId_category(String id_category) {
        this.id_category = id_category;
    }
}
