package com.silvandrade.sqlite_crud.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.silvandrade.sqlite_crud.controller.AlumnosCursorAdapter;
import com.silvandrade.sqlite_crud.database.AlumnosContract.AlumnoEntry;
import com.silvandrade.sqlite_crud.model.Alumno;

import androidx.annotation.Nullable;

public class  AdminDB extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "escola_primaria.db";

    // Construtor do Sqlite
    public AdminDB(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION); // Criando a base de dados.
    }


    // OnCreate do Sqlite
    @Override
    public void onCreate(SQLiteDatabase db) {

        /*
        // Criando uma tabela.
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_ALUMNOS + "(" +
                "ID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                "NOMBRE TEXT NOT NULL, " +
                "APELLIDO TEXT NOT NULL, " +
                "GRADO INTEGER NOT NULL, " +
                "GRUPO TEXT NOT NULL, " +
                "TURNO TEXT)");
        */

        db.execSQL("CREATE TABLE IF NOT EXISTS " + AlumnoEntry.TABLE_NAME + " ("
                + AlumnoEntry._ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
                + AlumnoEntry.ID + " TEXT NOT NULL,"
                + AlumnoEntry.NOMBRE + " TEXT NOT NULL,"
                + AlumnoEntry.APELLIDO + " TEXT NOT NULL,"
                + AlumnoEntry.GRADO + " TEXT NOT NULL,"
                + AlumnoEntry.GRUPO + " TEXT NOT NULL,"
                + AlumnoEntry.TURNO + " TEXT NOT NULL,"
                + "UNIQUE(" + AlumnoEntry.ID + "))");


        db.execSQL("INSERT INTO " + AlumnoEntry.TABLE_NAME + "(ID, NOMBRE, APELLIDO, GRADO, GRUPO, TURNO) " +
                " VALUES(1, 'Diego', 'Andrade', 3, 'A', 'VES')");

        insertAlumno(db, new Alumno("2", "Hugo", "Alves", "2", "B", "VES"));
    }

    // OnUpgrade Sqlite
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    // db.insert(AlumnoEntry.TABLE_NAME, null, values);
    public long insertAlumno(SQLiteDatabase db, Alumno alumno) {
        return db.insert(
                AlumnoEntry.TABLE_NAME,
                null,
                alumno.toContentValues()
        );
    }

    // Atualizar um aluno.
    public long updateAlumno(String id, String name, String lastName, String degree, String group, String turn) {
        ContentValues values = new ContentValues();
        values.put(AlumnoEntry.NOMBRE, name);
        values.put(AlumnoEntry.APELLIDO, lastName);
        values.put(AlumnoEntry.GRADO, degree);
        values.put(AlumnoEntry.GRUPO, group);
        values.put(AlumnoEntry.TURNO, turn);

        return getReadableDatabase()
                .update(AlumnoEntry.TABLE_NAME,
                        values,
                        AlumnoEntry._ID + " = ?",
                        new String[]{id});
    }

    // Excluir um aluno.
    public long deleteAlumno(String id) {
        return getReadableDatabase()
                .delete(AlumnoEntry.TABLE_NAME,
                        AlumnoEntry._ID + " = ?",
                        new String[]{id});
    }

    // Buscar todos os alunos.
    public Cursor getAllAlumnos() {
        return getReadableDatabase()
                .query(
                        AlumnoEntry.TABLE_NAME,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null
                );
    }

    // Buscar um aluno.
    public Cursor getAlumnoById(String id) {
        return getReadableDatabase()
                .query(
                        AlumnoEntry.TABLE_NAME,
                        null,
                        AlumnoEntry._ID + " LIKE ?",
                        new String[]{id},
                        null,
                        null,
                        null
                );
    }
}
