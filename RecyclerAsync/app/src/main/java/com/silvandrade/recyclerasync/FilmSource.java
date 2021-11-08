package com.silvandrade.recyclerasync;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class FilmSource implements ModelSource {

    private final SQLiteDatabase db;
    private Cursor cursor;

    public FilmSource(SQLiteDatabase db) {
        this.db = db;
        this.cursor = getAllData();
    }

    public Cursor getAllData() {
        Log.d("Recycler Async", this.getClass().getName() + " - getAllData() called.");
        if(cursor == null || cursor.isClosed()) {
            cursor = db.query("data", new String[]{"title", "content"}, null, null, null, null, null);
        }
        return cursor;
    }

    @Override
    public int getCount() {
        Log.d("Recycler Async", this.getClass().getName() + " - getCount() called.");
        return cursor.getCount();
    }

    @Override
    public Model getItem(int position) {
        Log.d("Recycler Async", this.getClass().getName() + " - getItem() called.");
        cursor.moveToPosition(position);
        return new Film(
                cursor.getString(0),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3)
        );
    }

    @Override
    public void close() {
        Log.d("Recycler Async", this.getClass().getName() + " - close() called.");
        if(cursor != null || !cursor.isClosed()) {
            cursor.close();
            cursor = null;
        }
    }
}
