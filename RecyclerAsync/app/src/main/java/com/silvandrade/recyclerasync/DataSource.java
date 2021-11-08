package com.silvandrade.recyclerasync;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DataSource implements ModelSource {

    private final SQLiteDatabase db;
    private Cursor cursor;
    private String tableName;

    public DataSource(SQLiteDatabase db, String tableName) {
        this.db = db;
        this.tableName = tableName;
        this.cursor = getAllDataTable(tableName);
    }

    private Cursor getAllDataTable(String tableName) {
        if(cursor == null || cursor.isClosed()) {
            return db.query(
                    tableName,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null
            );
        }
        return null;
    }

    @Override
    public int getCount() {
        return cursor.getCount();
    }

    @Override
    public Model getItem(int position) {
        cursor.moveToPosition(position);

        if(tableName.equals("Category")) {
            return new Category(
                    cursor.getString(0),
                    cursor.getString(1)
            );
        } else {
            return new Film(
                    cursor.getString(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3)
            );
        }
    }

    @Override
    public void close() {
        if(cursor != null || !cursor.isClosed()) {
            cursor.close();
            cursor = null;
        }
    }
}
