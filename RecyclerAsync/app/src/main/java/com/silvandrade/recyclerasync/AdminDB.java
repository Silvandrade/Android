package com.silvandrade.recyclerasync;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class AdminDB extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "recycler.db";
    private SQLiteDatabase db = this.getReadableDatabase();

    public AdminDB(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION); // Criando a base de dados.
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE IF NOT EXISTS Category("
                + "id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
                + "name TEXT NOT NULL)");

        db.execSQL("CREATE TABLE IF NOT EXISTS Film("
                + "id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
                + "title TEXT NOT NULL,"
                + "content TEXT NOT NULL,"
                + "id_category INTEGER NOT NULL,"
                + "FOREIGN KEY(id_category)"
                + "REFERENCES Category(id))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public long insetDataTable(Model model, String tableName) {
        return getWritableDatabase()
                .insert(tableName, null, model.toContentValues());
    }

    public Cursor selectAllDataTable() {
        return getReadableDatabase()
                .query("data", null, null, null, null, null, null);
    }

    public SQLiteDatabase getReadDB() {
        return db;
    }
}
